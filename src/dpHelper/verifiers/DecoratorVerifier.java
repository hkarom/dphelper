package dpHelper.verifiers;

import dpHelper.annotations.Decorator;
import dpHelper.exceptions.ElementKindException;
import dpHelper.exceptions.ModifierException;
import dpHelper.exceptions.NumberOfElementException;
import dpHelper.exceptions.TypeException;
import dpHelper.verifiers.fluentapi.Checker;

import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.Modifier;
import java.util.HashMap;
import java.util.HashSet;
import java.util.NoSuchElementException;
import java.util.Set;

import static dpHelper.tools.ProcessorTools.reportError;


public class DecoratorVerifier implements PatternVerifier<Decorator> {
   private HashMap<Decorator.Role, Set<Element>> actors;
   private Element component;

   public void verifyAll(Set<? extends Element> annotatedElements) {
      actors = new HashMap<>();
      initActorsElements(annotatedElements);
      this.verifyComponent(actors.get(Decorator.Role.COMPONENT));
      this.verifyConcreteComponent(actors.get(Decorator.Role.COMPONENT_CONCRETE));
      this.verifyDecorator(actors.get(Decorator.Role.DECORATOR));
      this.verifyDecoratorAgregation(actors.get(Decorator.Role.DECORATOR));
      this.verifyConcreteDecorators(actors.get(Decorator.Role.DECORATOR_CONCRETE));
   }

   private void initActorsElements(Set<? extends Element> annotatedElements) {
      for (Element annotatedElement : annotatedElements) {
         Decorator.Role role = annotatedElement
               .getAnnotation(Decorator.class).value();
         if (!actors.containsKey(role)) {
            HashSet<Element> elements = new HashSet<>();
            elements.add(annotatedElement);
            actors.put(role, elements);
         }

         actors.get(role).add(annotatedElement);
      }
   }

   private void verifyComponent(Set<Element> componentElement) {
      component = componentElement.iterator().next();
      try {
         Checker.CHECK.that(component)
               .is(ElementKind.INTERFACE);
      } catch (NoSuchElementException e) {
         reportError("Need Element Component"
                     + " to be an interface \n-> %s",
               e.getMessage());
      } catch (ElementKindException e) {
         reportError("Need component element", e.getMessage());
      } catch (NumberOfElementException e) {
         reportError("Need component element", e.getMessage());
      }
   }

   private void verifyConcreteComponent(Set<Element> concreteComponents) {
      component = actors
            .get(Decorator.Role.COMPONENT)
            .iterator().next();
      try {
         Checker.CHECK.thatAll(concreteComponents)
               .are(ElementKind.CLASS)
               .withInterface(component);
      } catch (TypeException e) {
         //e.printStackTrace();
      } catch (NumberOfElementException e) {
         reportError("Need all element be class kind ", e.getMessage());
         //e.printStackTrace();
      } catch (ElementKindException e) {
         //e.printStackTrace();
      }
   }

   private void verifyDecorator(Set<Element> decorators) {
      Element decorator = decorators.iterator().next();
      Set<Modifier> modifiers = new HashSet<>();
      modifiers.add(Modifier.ABSTRACT);
      component = actors.get(Decorator.Role.COMPONENT).iterator().next();
      try {
         Checker.CHECK.that(decorator)
               .are(ElementKind.CLASS)
               .withModifiers(Modifier.ABSTRACT)
               .withInterface(component);
      } catch (TypeException e) {
         e.printStackTrace();
      } catch (NumberOfElementException e) {
         e.printStackTrace();
      } catch (ModifierException e) {
         e.printStackTrace();
      } catch (ElementKindException e) {
         e.printStackTrace();
      }
   }

   private void verifyDecoratorAgregation(Set<Element> decorators) {
      Element decorator = decorators.iterator().next();
      component = actors.get(Decorator.Role.COMPONENT).iterator().next();
      try {
         Checker.CHECK.that(ElementKind.FIELD)
               .in(decorator)
               .withType(component);
      } catch (TypeException e) {
         e.printStackTrace();
      } catch (NumberOfElementException | NoSuchElementException e) {
         e.printStackTrace();
      }
   }

   private void verifyConcreteDecorators(Set<Element> concreteDecorators) {
      Element decorator = actors.get(Decorator.Role.DECORATOR).iterator().next();

      try {
         Checker.CHECK.thatAll(concreteDecorators)
               .are(ElementKind.CLASS)
               .withSuperClass(decorator);
      } catch (TypeException e) {
         e.printStackTrace();
      } catch (NumberOfElementException e) {
         e.printStackTrace();
      } catch (ElementKindException e) {
         e.printStackTrace();
      }
   }
}