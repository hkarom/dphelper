package dpHelper.verifiers;


import dpHelper.annotations.Observer;
import dpHelper.exceptions.NumberOfElementException;
import dpHelper.exceptions.VerifierException;

import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.Modifier;
import java.util.HashMap;
import java.util.HashSet;
import java.util.NoSuchElementException;
import java.util.Set;

import static dpHelper.tools.ProcessorTools.reportError;
import static dpHelper.verifiers.fluentapi.Checker.CHECK;

public class ObserverVerifier implements PatternVerifier<Observer> {


   private Element observer_element;
   private Element observable_element;

   private HashMap<Observer.Role, Set<Element>> actor;

   @Override
   public void verifyAll(Set<? extends Element> annotatedElements) {
      actor = new HashMap();
      initActorsElements(annotatedElements);
      this.verifyInterfaceObserver(actor.get(Observer.Role.INTERFACE_OBSERVER));
      this.verifyAbstractObservable(actor.get(Observer.Role.ABSTRACT_OBSERVABLE));
      this.verifyObserver(actor.get(Observer.Role.OBSERVER));
      this.verifyObservable(actor.get(Observer.Role.OBSERVABLE));
   }


   private void initActorsElements(Set<? extends Element> annotatedElements) {
      for (Element enclosingElement : annotatedElements) {
         Observer.Role role = enclosingElement.getAnnotation(Observer.class).value();
         if (!actor.containsKey(role)) {
            HashSet<Element> elements = new HashSet<>();
            elements.add(enclosingElement);
            actor.put(role, elements);
            continue;
         }
         actor.get(role).add(enclosingElement);
      }
   }


   private void verifyInterfaceObserver(Set<Element> interface_observer) {
      if (interface_observer != null && interface_observer.size() == 1) {
         observer_element = interface_observer.iterator().next();
         try {

            CHECK.thatAll(interface_observer)
                  .is(ElementKind.INTERFACE)
                  .haveModifiers(Modifier.PUBLIC);


         } catch (NoSuchElementException | VerifierException e) {

            reportError(" Need Interface_Observer to be an public interface ");

         }

         try {

            CHECK.that(ElementKind.METHOD)
                  .in(observer_element)
                  .withName("update");


         } catch (NumberOfElementException | NoSuchElementException e) {
            reportError("Interface annotated with Interface_observer must have method update()");
         }
      } else {

         reportError("You must have one and only on class or interface annotatad as interfacevisitor");
      }
   }

   private void verifyAbstractObservable(Set<Element> abstract_observable) {

      if (abstract_observable != null && abstract_observable.size() == 1) {
         observable_element = abstract_observable.iterator().next();
         try {

            CHECK.thatAll(abstract_observable)
                  .is(ElementKind.CLASS)
                  .haveModifiers(Modifier.PUBLIC);

         } catch (VerifierException | NoSuchElementException e) {

            reportError(" Need Abstract_Observable to be an public class ");

         }


         try {

            CHECK.that(ElementKind.METHOD)
                  .in(observable_element)
                  .withName("notifyObserver");

         } catch (NumberOfElementException | NoSuchElementException e) {
            reportError("Interface annotated with ABSTRACT_OBSERVABLE must have method notifyObserver()");
         }

         try {

            CHECK.that(ElementKind.METHOD)
                  .in(observable_element)
                  .withName("addObserver");


         } catch (NumberOfElementException | NoSuchElementException e) {
            reportError("Interface annotated with ABSTRACT_OBSERVABLE must have method addObserver()");
         }


         try {

            CHECK.that(ElementKind.METHOD)
                  .in(observable_element)
                  .withName("removeObserver");

         } catch (NumberOfElementException | NoSuchElementException e) {
            reportError("Interface annotated with ABSTRACT_OBSERVABLE must have method removeObserver()");
         }
      } else {

         reportError("You must have one and only on class or interface annotated as ABSTRACT_OBSERVABLE");
      }


   }


   private void verifyObserver(Set<Element> observer) {

      if (observer != null) {
         //observer_element = getUniqueElement(observer);
         try {

            CHECK.thatAll(observer)
                  .is(ElementKind.CLASS)
                  .haveModifiers(Modifier.PUBLIC);


         } catch (VerifierException | NoSuchElementException e) {

            reportError(" Need OBSERVER to be an public class ");

         }

         try {
            CHECK.thatAll(observer)
                  .is(ElementKind.CLASS)
                  .haveModifiers(Modifier.PUBLIC)
                  .implementsInterface(observer_element);
         } catch (VerifierException | NoSuchElementException e) {

            reportError("Need all OBSERVER %s to be class public implementing " +
                        "INTERFACE_OBSERVER(%s) : \n-> %s", observer,
                  observer_element.asType(), e.getMessage());
         }
      } else {

         reportError("You must have one and only on class or interface annotated as OBSERVER");
      }
   }


   private void verifyObservable(Set<Element> observable) {
      if (observable != null) {
         observable_element = observable.iterator().next();
         try {

            CHECK.thatAll(observable)
                  .is(ElementKind.CLASS)
                  .haveModifiers(Modifier.PUBLIC);


         } catch (VerifierException | NoSuchElementException e) {

            reportError(" Need OBSERVABLE to be an public class ");

         }
      } else {

         reportError("You must have one and only on class or interface annotated as OBSERVABLE");
      }
   }
}