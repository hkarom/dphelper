package dpHelper.tools;

import dpHelper.verifiers.fluentapi.AbstractCheck;

import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.type.TypeMirror;
import java.util.Collection;
import java.util.Set;

import static dpHelper.tools.ProcessorTools.arrayToString;
import static dpHelper.tools.ProcessorTools.reportError;
import static dpHelper.tools.ProcessorTools.reportWarning;

public final class Errors {

   private Errors() {
   }


   public static String kindNotFound(ElementKind kind, Collection<Element> e) {
      return String.format("-> Not any %s found in %s", kind, e);
   }

   public static String kindDoesNotMatch(ElementKind kind, Collection<Element> e) {
      return String.format("-> %s should be of kind %s", e,kind);
   }

   public static String constraintRuleNotRespected(AbstractCheck exist, Object
         subject, String descr, Object found, int nbFound) {

      return String.format("-> Expected %s%d %s from %s matching condition '%s %s' but" +
                  " found %d", exist.getConstraint(), exist.getNbExpectedElements(),
            exist.getKind(), subject, descr, found, nbFound);
   }

   public static String doNotHaveWhatExpected(Object kind, String descr, Object
         element) {

      return String.format("-> Elements %s do not have %s %s", kind, descr, element);
   }


   public static String doNotHaveWhatExpected(Object kind, String descr, Object...
         elements) {

      return String.format("-> Elements %s do not have %s %s", kind, descr,
            arrayToString(elements));
   }

   public static String isNotWhatExpected(Object kind, String descr, Object...
         elements) {

      return String.format("-> Elements %s is not %s %s", kind, descr,
            arrayToString(elements));
   }


   public static String doNotImplementOne(Element className, int nbImplements, Object
         interfaces) {
      return String.format("-> Class %s implements %d of %s while should be " +
            "implementing one", className, nbImplements, interfaces);
   }

   public static String isNotImplementedBy(Element interfaces, String function, Object
         elements) {
      return String.format("Interface %s is not %s %s", interfaces, function, elements);
   }

   public static String isNotTypeOf(Element type, String function, Object
         elements) {
      return String.format("Type %s is not %s %s", type, function, elements);
   }

   public static String doNotMatchDescription(Object kind, String descr, Object o) {

      return String.format("-> Not any %s with %s %s found", kind, descr, o);
   }

   public static String eachElementMustFoundMatch(Object kind, Object element, String descr,
                                                  Object o) {

      return String.format("-> All %s %s must be %s at least one of element(s) %s ",
            kind, element, descr, o);
   }

   public static String cannotBeDescendantOfItself(Element element) {

      return String.format("-> Element %s cannot be descendant of itself ", element);
   }

   public static String typeNotFound(Collection<TypeMirror> elements) {

      return String.format("-> No Type found corresponding to %s", elements);
   }




   /**
    * SINGLETON
    */

   public static class Singleton {


      public static void privateConstructorsError(String className, String msg) {
         reportWarning(
               "[%s.java]:Need all constructors to be private : \n%s",
               className, msg);
      }

      public static void getInstanceError(String className, String methodName, String msg) {
         reportError(
               "[%s.java]:Need public static method(s) '%s' returning %s : \n%s",
               className, methodName, className, msg);
      }

      public static void synchronizedGetInstanceError(String className, String methodName,
                                                      String msg) {
         reportError(
               "[%s.java]:Need public static synchronized method(s) '%s' returning %s :" +
                     " \n%s", className, methodName, className, msg);
      }

      public static void uniqueInstanceError(String className, String msg) {
         reportError(
               "[%s.java]:Need unique private static field with type %s :" +
                     " \n%s", className, className, msg);
      }

      public static void volatileUniqueInstanceError(String className, String msg) {
         reportError(
               "[%s.java]:Need unique private static volatile field with type %s :" +
                     " \n%s", className, className, msg);
      }
   }


   /**
    * ABSTRACT FACTORY
    */

   public static class AbstractFactory {


      public static void absFactoryErrorMethodFamilyProduct(Collection<Element>
                                                        absFactories, String message) {

         reportError("Need all abstract factories %s to be interfaces, annotated with " +
               "@AbstractFactory, with existing family products as their methods " +
               "return type : \n%s",absFactories, message);
      }

      public static void factoryIsNotFromAbsFactory(Element factory, TypeMirror
            absFactory, String message) {

         reportError(
               "[%s.java]:Need factory %s to be a class descendant of ABSTRACT FACTORY " +
                     "%s : \n%s", factory.getSimpleName(), factory, absFactory, message);
      }

      public static void productIsNotFromOnyOneFamily(Element product, Set<Element> family,
                                                      String message) {
         reportError(
               "[%s.java]:Need product %s to be a class descending from only one these " +
                     "family product %s : \n%s", product, product, family, message);
      }
   }

   /**
    * VISITOR
    */

   public static class Visitor {



   }

   /**
    * OBSERVER
    */

   public static class Observer {



   }

   /**
    * DECORATOR
    */

   public static class Decorator {



   }

}

