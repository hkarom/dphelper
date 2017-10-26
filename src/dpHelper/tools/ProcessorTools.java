package dpHelper.tools;

import com.sun.source.util.DocTrees;
import com.sun.source.util.Trees;
import dpHelper.visitors.*;

import javax.annotation.processing.Filer;
import javax.annotation.processing.Messager;
import javax.lang.model.element.*;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.util.Elements;
import javax.lang.model.util.SimpleAnnotationValueVisitor7;
import javax.lang.model.util.Types;
import javax.tools.Diagnostic;
import javax.tools.JavaFileObject;
import java.io.IOException;
import java.util.*;


public final class ProcessorTools {

   public static Filer FILER;
   public static Types TYPES;
   public static Elements ELEMENTS;
   public static Messager MESSAGER;
   public static Trees TREES;
   public static DocTrees DOCTREES;

   public static Map<String, Element> allCompilingClasses = new HashMap<>();

   /**
    * PROCESSOR MESSAGES
    */

   public static void reportError(String message, Object... parameters) {
      String errorMessage = String.format(message, parameters);
      MESSAGER.printMessage(Diagnostic.Kind.ERROR, errorMessage);
   }

   public static void reportWarning(String message, Object... parameters) {
      String errorMessage = String.format(message, parameters);
      MESSAGER.printMessage(Diagnostic.Kind.WARNING, errorMessage);
   }

   public static void reportNote(String message, Object... parameters) {
      String errorMessage = String.format(message, parameters);
      MESSAGER.printMessage(Diagnostic.Kind.NOTE, errorMessage);
   }

   /**
    * ANNOTATION VALUE: parameters of type Class cannot be directly accessed during
    * compilation, we need to get them to be in the form of TypeMirror by an Annotation
    * Visitor
    */

   public static List<TypeMirror> annotationClassValues(Element element) {
      List<? extends AnnotationMirror> annotationMirrors = element.getAnnotationMirrors();

      SimpleAnnotationValueVisitor7<List<TypeMirror>, Void> visitor = new
            SimpleAnnotationValueVisitor7<List<TypeMirror>, Void>() {

               private List<TypeMirror> classesValues = new ArrayList<>();

               @Override
               public List<TypeMirror> visitType(TypeMirror typeMirror, Void aVoid) {
                  classesValues.add(typeMirror);
                  return super.visitType(typeMirror, aVoid);
               }

               @Override
               public List<TypeMirror> visitArray(List<? extends AnnotationValue> list, Void aVoid) {
                  for (AnnotationValue val : list) {
                     val.accept(this, aVoid);
                  }
                  return classesValues;
               }
            };

      List<TypeMirror> annotationClassValues = new ArrayList<>();

      for (AnnotationMirror annotationMirror : annotationMirrors) {
         Map<? extends ExecutableElement, ? extends AnnotationValue> elementValues
               = annotationMirror.getElementValues();
         annotationClassValues.addAll(visitor.visitArray(new ArrayList<>(elementValues
               .values()), null));
      }
      return annotationClassValues;
   }


   /**
    * CODE GENERATION TOOLS
    */

   public static JavaFileObject createSourceFile(CharSequence charSequence, Element...
         elements) throws IOException {
      return FILER.createSourceFile(charSequence, elements);
   }


   /**
    * GENERAL
    */


   public static Set<Modifier> put(Modifier... params) {
      return new HashSet<>(Arrays.asList(params));
   }

   public static Set<Element> put(Element... params) {
      return new HashSet<>(Arrays.asList(params));
   }

   public static Set<TypeMirror> put(TypeMirror... params) {
      return new HashSet<>(Arrays.asList(params));

   }

   public static Set<TypeMirror> putTypeMirror(Element... params) {
      Set<TypeMirror> typeMirrors = new HashSet<>();
      for (Element e : params) {
         typeMirrors.add(e.asType());
      }
      return typeMirrors;
   }


   public static String arrayToString(Object... obj) {
      StringBuilder string = new StringBuilder("[");
      int i;
      for (i = 0; i < obj.length - 1; i++) {
         string.append(obj[i]);
         string.append(",");
      }
      string.append(obj[i]);
      string.append("]");
      return string.toString();
   }

   public static Element[] toArray(Collection<Element> elements) {
      Element[] array = elements.toArray(new Element[0]);
      return array;
   }


   public static String repeatChar(char charToRepeat, int number) {
      StringBuilder r = new StringBuilder();
      for (int i = 0; i < number; i++) {
         r.append(charToRepeat);
      }
      return r.toString();
   }

   public static List<Element> getParametersOfMethods(List<Element> methods) {
      GetParametersVisitor visitor = new GetParametersVisitor();
      visitor.scan(methods, null);
      return visitor.getFoundElements();
   }


   /**
    * INDEX TABLE
    */

   public static Set<Element> getFromIndex(Collection<?
         extends Element> elements) throws NoSuchElementException {

      TypeVisitor visitor = new TypeVisitor();
      List<Element> tmpElements = new ArrayList<>(elements);
      Set<Element> results = new HashSet<>();

      for (Element file : allCompilingClasses.values()) {
         visitor.scan(tmpElements, file);
         if (!visitor.getFoundElements().isEmpty()) {
            results.add(file);
            tmpElements.removeAll(visitor.getFoundElements());
         }
         visitor.reset();
      }
      if (!tmpElements.isEmpty())
         throw new NoSuchElementException( Errors.typeNotFound
               (putTypeMirror(toArray(tmpElements))));

      return results;
   }

   public static Set<Element> getFromIndex(List<TypeMirror> values)
         throws NoSuchElementException {

      Set<Element> files = new HashSet<>();
      for (TypeMirror file : values) {
         Element element = allCompilingClasses.get(file.toString());
         if (element == null)
            throw new NoSuchElementException( Errors.typeNotFound(put(file)));
         files.add(element);
      }
      return files;
   }

   public static Collection<Element> getDescendants(Element parent) {
      Set<Element> foundDescendants = new HashSet<>();
      getAllClassesDescendants(parent, allCompilingClasses.values(), foundDescendants);
      return foundDescendants;
   }


   private static void getAllClassesDescendants(Element parent, Collection<Element>
         currentClasses, Collection<Element> descendants) {
      List<Element> children = new ArrayList<>(currentClasses);
      List<Element> tmpDescendants = new ArrayList<>();

      for (Element child : children) {
         if (isSubClass(child, parent)) {
            tmpDescendants.add(child);
            descendants.add(child);
         }
      }
      children.removeAll(tmpDescendants);
      for (Element descendant : tmpDescendants) {
         getAllClassesDescendants(descendant, children, descendants);
      }
   }

   private static boolean isSubClass(Element child, Element parent) {

      AbstractVisitor visitor = new SuperClassVisitor();
      visitor.scan(child, parent);

      if (visitor.getFoundElements().isEmpty()) {
         visitor = new InterfaceVisitor();
         visitor.scan(child, put(parent));

         if (visitor.getFoundElements().isEmpty())
            return false;
      }
      return true;
   }

}
