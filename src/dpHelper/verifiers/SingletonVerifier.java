package dpHelper.verifiers;

import dpHelper.annotations.Singleton;
import dpHelper.exceptions.VerifierException;
import dpHelper.generators.SingletonGenerator;
import dpHelper.tools.Errors;

import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import java.util.NoSuchElementException;

import static dpHelper.tools.ProcessorTools.reportNote;
import static dpHelper.verifiers.fluentapi.Checker.CHECK;
import static javax.lang.model.element.Modifier.*;


public class SingletonVerifier extends SingleVerifier<Singleton> {

   private SingletonGenerator generator;
   private Element annotatedClass;
   private String annotation;
   private String className;

   public SingletonVerifier(String annotationName) {
      annotation = annotationName;
   }

   @Override
   public void verify(Element element) {

      annotatedClass = element;
      className = element.getSimpleName().toString();
      generator = new SingletonGenerator(annotatedClass);
      verifySingleton();
      generator.launchGeneration();
   }


   private void verifySingleton() {
      switch (annotation) {
         case "Synchronized":
            new ThreadedSingletonVerifier().verifySingleton();
            break;
         default:
            new SimpleSingletonVerifier().verifySingleton();
            break;
      }
   }

   private abstract class SingletonElementsVerifier {

      void verifySingleton() {
         verifyConstructor();
         verifyMethod();
         verifyField();
      }

      private void verifyConstructor() {

         try {
            CHECK.thatAll(ElementKind.CONSTRUCTOR)
                  .in(annotatedClass)
                  .withType(annotatedClass)
                  .haveModifiers(PRIVATE);

         } catch (VerifierException | NoSuchElementException e) {
            String code = generator.prepareConstructorGeneration();
            Errors.Singleton.privateConstructorsError(className,e.getMessage());
            reportNote("%s",code);
         }
      }

      abstract void verifyField();

      abstract void verifyMethod();
   }

   private class SimpleSingletonVerifier extends SingletonElementsVerifier {

      void verifyMethod() {

         String methodName =
               annotatedClass.getAnnotation(Singleton.class).value();
         try {
            CHECK.that(ElementKind.METHOD)
                  .in(annotatedClass)
                  .withName(methodName)
                  .withType(annotatedClass)
                  .haveModifiers(PUBLIC, STATIC);

         } catch (VerifierException | NoSuchElementException e) {
            String code = generator.prepareMethodGeneration(methodName);
            Errors.Singleton.getInstanceError(className,methodName,e.getMessage());
            reportNote("%s",code);
         }
      }

      void verifyField() {
         try {
            CHECK.that(1, ElementKind.FIELD)
                  .in(annotatedClass)
                  .haveType(annotatedClass)
                  .haveModifiers(PRIVATE, STATIC);

         } catch (VerifierException | NoSuchElementException e) {
            String code = generator.prepareInstanceGeneration();
            Errors.Singleton.uniqueInstanceError(className,e.getMessage());
            reportNote("%s",code);
         }
      }
   }

   private class ThreadedSingletonVerifier extends SingletonElementsVerifier {

      void verifyMethod() {
         String methodName =
               annotatedClass.getAnnotation(Singleton.Synchronized.class).value();
         try {
            CHECK.that(ElementKind.METHOD)
                  .in(annotatedClass)
                  .withName(methodName)
                  .withType(annotatedClass)
                  .haveModifiers(PUBLIC, STATIC, SYNCHRONIZED);

         } catch (VerifierException | NoSuchElementException e) {
            String code = generator.prepareMethodGeneration(methodName);
            Errors.Singleton.synchronizedGetInstanceError(className, methodName,
                  e.getMessage());
            reportNote("%s",code);
         }
      }


      void verifyField() {

         try {
            CHECK.that(1, ElementKind.FIELD)
                  .in(annotatedClass)
                  .haveType(annotatedClass)
                  .haveModifiers(PRIVATE, STATIC, VOLATILE);

         } catch (VerifierException | NoSuchElementException e) {
            String code = generator.prepareInstanceGeneration();
            Errors.Singleton.volatileUniqueInstanceError(className, e.getMessage());
            reportNote("%s",code);
         }
      }
   }
}