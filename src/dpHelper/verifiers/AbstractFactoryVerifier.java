package dpHelper.verifiers;

import dpHelper.annotations.AbstractFactory;
import dpHelper.exceptions.VerifierException;
import dpHelper.tools.Errors;
import dpHelper.verifiers.fluentapi.ISpecification;

import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.type.TypeMirror;
import java.util.*;

import static dpHelper.tools.ProcessorTools.*;
import static dpHelper.verifiers.fluentapi.Checker.CHECK;


public class AbstractFactoryVerifier extends SingleVerifier<AbstractFactory> {

   private static HashMap<String, Set<Element>> absProducts = new HashMap<>();
   private String annotation;

   public AbstractFactoryVerifier(String annotationName) {
      annotation = annotationName;
   }

   @Override
   public void verifyAll(Set<? extends Element> annotatedElements) {
      Collection<Element> elements = new HashSet<>(annotatedElements);

      if (annotation.equals("AbstractFactory"))
         verifyAbstractFactory(elements);
      else
         super.verifyAll(annotatedElements);
   }

   @Override
   public void verify(Element element) {

      switch (annotation) {
         case "Factory":
            verifyFactory(element);
            break;
         case "Product":
            verifyProduct(element);
            break;
         default:
            break;
      }
   }

   private boolean verifyAbstractFactory(Collection<Element> absFactories) {
      try {
         CHECK.thatAll(absFactories)
               .are(ElementKind.INTERFACE)
               .annotatedWith(AbstractFactory.class);

         Collection<Element> potentialFamilies = new LinkedList<>(allCompilingClasses.values());
         potentialFamilies.removeAll(absFactories);

         for (Element absFactory : absFactories) {

            ISpecification checking =
                  CHECK.thatAll(ElementKind.METHOD)
                        .in(absFactory)
                        .haveOneOfTheseTypes(potentialFamilies);

            List<Element> methods = checking.getSelectedElements();
            String name = absFactory.getSimpleName().toString();
            absProducts.put(name, getFromIndex(methods));
         }
      } catch (VerifierException | NoSuchElementException e) {
         absProducts = null;
         Errors.AbstractFactory
               .absFactoryErrorMethodFamilyProduct(absFactories, e.getMessage());
         return false;
      }
      return true;
   }

   private void verifyFactory(Element factory) {

      List<TypeMirror> absFactoryType = annotationClassValues(factory);

      try {
         Collection<Element> absFactories = getAbsFactories(absFactoryType);
         if (absFactories != null) {
            TypeMirror absFactory = absFactoryType.get(0);
            Element classAbsFactory = allCompilingClasses.get(absFactory.toString());

            CHECK.that(factory)
                  .is(ElementKind.CLASS)
                  .descendantOf(classAbsFactory);
         }
      } catch (VerifierException | NoSuchElementException ex) {
         Errors.AbstractFactory
               .factoryIsNotFromAbsFactory(factory, absFactoryType.get(0), ex.getMessage());

      }
   }


   private void verifyProduct(Element product) {

      List<TypeMirror> absFactoriesTypes = annotationClassValues(product);
      try {
         Collection<Element> absFactories = getAbsFactories(absFactoriesTypes);
         if (absFactories != null) {
            for (Element absFactory : absFactories) {
               verifyProductFamily(absFactory, product);
            }
         }
      } catch (VerifierException | NoSuchElementException ex) {
         reportError("[%s.java]:Error with product %s : \n%s", product, product,
               ex.getMessage());
      }
   }


   private Collection<Element> getAbsFactories(List<TypeMirror> absFactoriesTypes)
         throws NoSuchElementException, VerifierException {

      Set<Element> absFactories = getFromIndex(absFactoriesTypes);
      boolean res = absProducts != null && (!absProducts.isEmpty() ||
            verifyAbstractFactory(absFactories));
      return res ? absFactories : null;
   }


   private void verifyProductFamily(Element absFactory, Element
         product) {

      Set<Element> families = absProducts.get(absFactory.getSimpleName().toString());

      try {
            CHECK.that(product)
                  .is(ElementKind.CLASS)
                  .descendantOfOnlyOneOf(families);

      } catch (VerifierException | NoSuchElementException ex) {
         Errors.AbstractFactory
               .productIsNotFromOnyOneFamily(product, families, ex.getMessage());
      }
   }
}
