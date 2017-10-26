package dpHelper.verifiers;

import dpHelper.annotations.Visitor;
import dpHelper.exceptions.NumberOfElementException;
import dpHelper.exceptions.VerifierException;
import dpHelper.verifiers.fluentapi.ISpecification;

import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.type.TypeMirror;
import java.util.*;

import static dpHelper.tools.ProcessorTools.*;
import static dpHelper.verifiers.fluentapi.Checker.CHECK;


public class VisitorVerifier extends SingleVerifier<Visitor> {

   private static HashMap<String, Set<Element>> visitables = new HashMap<>();
   private String annotation;


   public VisitorVerifier(String annotationName) {
      annotation = annotationName;
   }

   @Override
   public void verifyAll(Set<? extends Element> annotatedElements) {
      Collection<Element> elements = new HashSet<>(annotatedElements);

      if (annotation.equals("Visitor"))
         verifyInterfaceVisitor(elements);
      else
         super.verifyAll(annotatedElements);
   }

   @Override
   public void verify(Element element) {

      switch (annotation) {
         case "Concrete":
            verifyConcreteVisitor(element);
            break;
         case "Visitable":
            verifyVisitable(element);
            break;
         default:
            break;
      }
   }

   private boolean verifyInterfaceVisitor(Collection<Element> iVisitor) {
      try {

         CHECK.thatAll(iVisitor)
               .are(ElementKind.INTERFACE);

      } catch (VerifierException | NoSuchElementException e) {
         visitables = null;
         reportError("Need interfaces Visitor to be public : \n%s",e.getMessage());
         return false;
      }

      try {
         for (Element absVisitor : iVisitor) {
            ISpecification checking =
                  CHECK.that(ElementKind.METHOD)
                        .in(absVisitor)
                        .withNameContaining("visit");

            List<Element> methods = checking.getSelectedElements();
            List<Element> visitableParameters = getParametersOfMethods(methods);
            String name = absVisitor.getSimpleName().toString();
            visitables.put(name, getFromIndex(visitableParameters));
         }

      } catch (NumberOfElementException | NoSuchElementException e) {
         visitables = null;
         reportError("All interfaces %s should have method(s) visit with Visitable " +
               "class as parameter : \n%s",iVisitor,e.getMessage());
         return false;
      }
      return true;
   }

   private void verifyConcreteVisitor(Element visitor) {
      List<TypeMirror> iVisitorType = annotationClassValues(visitor);

      try {
         Collection<Element> iVisitors = checkIfIVisitorValid(iVisitorType);
         if (iVisitors != null) {
            TypeMirror iVisitor = iVisitorType.get(0);
            Element classIVisitor = allCompilingClasses.get(iVisitor.toString());

            CHECK.that(visitor)
                  .is(ElementKind.CLASS)
                  .descendantOf(classIVisitor);
         }
      } catch (VerifierException e) {
         reportError("[%s.java]:Need concrete visitor %s to be descendant of " +
               "%s : \n%s", visitor,visitor,iVisitorType,e.getMessage());
      }
   }


   private void verifyVisitable(Element visitable) {

      List<TypeMirror> iVisitorTypes = annotationClassValues(visitable);
      try {
         Collection<Element> iVisitors = checkIfIVisitorValid(iVisitorTypes);
         if(iVisitors != null) {
            for(Element iVisitor : iVisitors) {
               List<Element> list = new ArrayList<>(getDescendants(visitable));
               list.add(visitable);

               CHECK.that(ElementKind.METHOD)
                     .inAll(list)
                     .haveName("accept")
                     .haveParameter(iVisitor);
            }
         }
      } catch (VerifierException e) {
         reportError("[%s.java]:Need visitable %s and all its descendant %s to have " +
               "method accept : \n%s", visitable,visitable,getDescendants(visitable),
               e.getMessage());
      }

   }

   private Collection<Element> checkIfIVisitorValid(List<TypeMirror> iVisitorTypes)
         throws NoSuchElementException, VerifierException {

      Set<Element> iVisitor = getFromIndex(iVisitorTypes);

      boolean res = visitables != null && (!visitables.isEmpty() ||
            verifyInterfaceVisitor(iVisitor));
      return res ? iVisitor : null;
   }

}
