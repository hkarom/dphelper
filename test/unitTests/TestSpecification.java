package unitTests;

import dpHelper.exceptions.VerifierException;
import org.junit.Before;
import org.junit.Test;
import dpHelper.exceptions.ModifierException;
import dpHelper.exceptions.NumberOfElementException;
import dpHelper.exceptions.TypeException;

import javax.lang.model.element.*;
import java.io.File;
import java.util.*;

import static dpHelper.tools.ProcessorTools.put;
import static dpHelper.verifiers.fluentapi.Checker.CHECK;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class TestSpecification {

   final String FILES_PATH = "resources/experiments/filesForUnitTests/";

   private List<Element> foundElements;
   private Element constructor;
   private Element method;
   private Element variable;

   private PatternsProcessorTest processorTest;
   private Element classElement1;
   private Element classElement2;
   private Element superClass;
   private Element superClass2;
   private Element interface1;
   private Element interface2;

   @Before
   public void setup() {
      processorTest = new PatternsProcessorTest();
      File[] sources = new File[6];
      sources[0] = new File(FILES_PATH + "Classe1.java");
      sources[1] = new File(FILES_PATH + "Classe2.java");
      sources[2] = new File(FILES_PATH + "SuperClass.java");
      sources[3] = new File(FILES_PATH + "SuperClass2.java");
      sources[4] = new File(FILES_PATH + "Interface1.java");
      sources[5] = new File(FILES_PATH + "Interface2.java");

      processorTest = CompilerProcessorTest.compile(sources);
      List<? extends Element> annotatedE = processorTest.getAnnotatedElements();

      classElement1 = annotatedE.get(0);
      classElement2 = annotatedE.get(1);
      superClass = annotatedE.get(2);
      superClass2 = annotatedE.get(3);
      interface1 = annotatedE.get(4);
      interface2 = annotatedE.get(5);

      variable = classElement1.getEnclosedElements().get(0);
      constructor = classElement1.getEnclosedElements().get(1);
      method = classElement1.getEnclosedElements().get(2);

      foundElements = new LinkedList<>();
   }


   @Test
   public void TestWithNamePositive() {
      String nameVariable = "instance";
      launchWithName(classElement1, ElementKind.FIELD, nameVariable);
      assertThat(foundElements, hasSize(1));
      assertThat(foundElements, contains(variable));
   }

   @Test
   public void TestWithNameNegative() {
      String nameMethod = "value";
      launchWithName(classElement1, ElementKind.METHOD, nameMethod);
      assertThat(foundElements, hasSize(0));
      assertThat(foundElements, not(contains(method)));
   }

   @Test
   public void testWithModifiersPositive() {

      launchWithModifiers(classElement1, ElementKind.CONSTRUCTOR, Modifier.PRIVATE);
      assertThat(foundElements, hasSize(1));
      assertThat(foundElements, contains(constructor));
   }

   @Test
   public void testWithModifiersNegative() {

      launchWithModifiers(classElement1, ElementKind.METHOD, Modifier.PRIVATE);
      assertThat(foundElements, hasSize(0));
   }

   @Test
   public void testWithTypePositive() {
      launchWithType(classElement1, ElementKind.METHOD, classElement1);
      assertThat(foundElements, hasSize(1));
      assertThat(foundElements, contains(method));
   }

   @Test
   public void testWithTypeNegative() {
      launchWithType(classElement1, ElementKind.METHOD, classElement2);
      assertThat(foundElements, hasSize(0));
   }

   @Test
   public void testWithOneOfTheseTypesPositive() {
      Set<Element> elements = new HashSet<>();
      elements.add(classElement1);
      elements.add(classElement2);
      launchWithOneOfTheseTypes(classElement1, ElementKind.METHOD, elements);
      assertThat(foundElements, hasSize(1));
      assertThat(foundElements, contains(method));
   }

   @Test
   public void testWithOneOfTheseTypesNegative() {
      Set<Element> elements = new HashSet<>();
      elements.add(classElement2);
      launchWithOneOfTheseTypes(classElement1, ElementKind.METHOD, elements);
      assertThat(foundElements, hasSize(0));
   }

   @Test
   public void testWithDistinctTypesPositive() {
      Set<Element> elements = new HashSet<>();
      elements.add(classElement1);
      elements.add(classElement2);

      launchWithDistinctTypes(superClass2, ElementKind.METHOD, elements);
      assertThat(foundElements, hasSize(2));
   }

   @Test
   public void testWithDistinctTypesNegative() {
      Set<Element> elements = new HashSet<>();
      elements.add(classElement1);
      elements.add(classElement2);

      launchWithDistinctTypes(classElement1, ElementKind.METHOD, elements);
      assertThat(foundElements, hasSize(0));
   }

   @Test
   public void testWithSuperClassPositive() {
      launchWithSuperClass(classElement2, ElementKind.CLASS, superClass);
      assertThat(foundElements, hasSize(1));
      assertThat(foundElements, contains(classElement2));
   }

   @Test
   public void testWithSuperClassNegative() {
      launchWithSuperClass(classElement1, ElementKind.CLASS, superClass);
      assertThat(foundElements, hasSize(0));
   }

   @Test
   public void testWithInterfacePositive() {
      launchWithInterface(superClass, ElementKind.CLASS, interface1);
      assertThat(foundElements, hasSize(1));
      assertThat(foundElements, contains(superClass));
   }

   @Test
   public void testWithInterfaceNegative() {
      launchWithInterface(classElement2, ElementKind.CLASS, interface1);
      assertThat(foundElements, hasSize(0));
   }

   @Test
   public void testWithInterfacesPositive() {
      Set<Element> interfaces = put(interface1, interface2);
      launchWithInterfaces(classElement1, ElementKind.CLASS, interfaces);
      assertThat(foundElements, hasSize(1));
      assertThat(foundElements, contains(classElement1));
   }

   @Test
   public void testWithInterfacesNegative() {
      Set<Element> interfaces = put(interface1, interface2);
      launchWithInterfaces(superClass, ElementKind.CLASS, interfaces);
      assertThat(foundElements, hasSize(0));
   }

   @Test
   public void testWithOneOfTheseInterfacesPositive() {
      Set<Element> interfaces = put(interface1, interface2);
      launchWithOneOfTheseInterfaces(superClass, ElementKind.CLASS, interfaces);
      assertThat(foundElements, hasSize(1));
      assertThat(foundElements, contains(superClass));
   }

   @Test
   public void testWithOneOfTheseInterfacesNegative() {
      Set<Element> interfaces = put(interface1, interface2);
      launchWithOneOfTheseInterfaces(superClass2, ElementKind.CLASS, interfaces);
      assertThat(foundElements, hasSize(0));
   }

   @Test
   public void testWithDistinctInterfacesPositive() {
      Set<Element> interfaces = put(interface1, interface2);
      Set<Element> classes = put(classElement2, superClass);
      boolean res = launchWithDistinctInterfaces(classes, ElementKind.CLASS,
            interfaces);
      assertThat(foundElements, hasSize(2));
      assertThat(foundElements, hasItems(superClass, classElement2));
      assertThat(res, is(true));
   }

   @Test
   public void testWithDistinctInterfacesNegative() {
      Set<Element> interfaces = put(interface1, interface2);
      Set<Element> classes = put(classElement1, classElement2);
      boolean res = launchWithDistinctInterfaces(classes, ElementKind
            .CLASS, interfaces);
      assertThat(foundElements, hasSize(0));
      assertThat(res, is(false));
   }


   private boolean launchWithDistinctInterfaces(Set<Element> elements, ElementKind
                                                kind, Set<Element> interfs) {
      try {
         foundElements = CHECK.thatAll(elements).are(kind)
               .implementsDistinctInterfaces(interfs)
               .getSelectedElements();
      } catch (VerifierException e) {
         return false;
      }
      return true;
   }

   private boolean launchWithOneOfTheseInterfaces(Element element, ElementKind kind,
                                                  Set<Element> interfs) {
      try {
         foundElements = CHECK.that(kind).in(element)
               .implementsOneOfInterfaces(interfs)
               .getSelectedElements();
      } catch (TypeException | NumberOfElementException e) {
         return false;
      }
      return true;
   }

   private boolean launchWithInterfaces(Element element, ElementKind kind,
                                        Set<Element> interfs) {
      try {
         foundElements = CHECK.that(kind).in(element).implementsInterfaces
               (interfs)
               .getSelectedElements();
      } catch (TypeException | NumberOfElementException e) {
         return false;
      }
      return true;
   }

   private boolean launchWithInterface(Element element, ElementKind kind, Element interf) {
      try {
         foundElements = CHECK.that(kind).in(element).implementsInterface(interf)
               .getSelectedElements();
      } catch (TypeException | NumberOfElementException e) {
         return false;
      }

      return true;
   }

   private boolean launchWithSuperClass(Element element, ElementKind kind,
                                        Element superClass) {
      try {
         foundElements = CHECK.that(kind).in(element).extendsClass(superClass)
               .getSelectedElements();
      } catch (TypeException | NumberOfElementException e) {
         return false;
      }
      return true;
   }

   private boolean launchWithDistinctTypes(Element element, ElementKind kind,
                                           Set<Element> elements) {
      try {
         foundElements = CHECK.that(kind).in(element).haveDistinctTypes(elements)
               .getSelectedElements();
      } catch (TypeException | NumberOfElementException e) {
         return false;
      }
      return true;
   }

   private boolean launchWithOneOfTheseTypes(Element element, ElementKind kind,
                                             Set<Element> elements) {
      try {
         foundElements = CHECK.that(kind).in(element).haveOneOfTheseTypes(elements)
               .getSelectedElements();
      } catch (TypeException | NumberOfElementException e) {
         return false;
      }
      return true;
   }

   private boolean launchWithType(Element element, ElementKind kind, Element type) {
      try {
         foundElements = CHECK.that(kind).in(element).haveType(type)
               .getSelectedElements();
      } catch (TypeException | NumberOfElementException e) {
         return false;
      }
      return true;
   }

   private boolean launchWithModifiers(Element element, ElementKind kind,
                                       Modifier... searchedModifiers) {
      try {
         foundElements = CHECK.that(kind).in(element).haveModifiers
               (searchedModifiers).getSelectedElements();
      } catch (ModifierException | NumberOfElementException e) {
         System.out.print(e.getMessage());
         return false;
      }
      return true;
   }

   private boolean launchWithName(Element element, ElementKind kind, String name) {
      try {
         foundElements = CHECK.that(kind).in(element).withName(name)
               .getSelectedElements();
      } catch (NoSuchElementException | NumberOfElementException e) {
         return false;
      }
      return true;
   }
}
