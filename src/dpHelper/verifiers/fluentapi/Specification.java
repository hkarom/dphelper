package dpHelper.verifiers.fluentapi;

import dpHelper.exceptions.ModifierException;
import dpHelper.exceptions.NumberOfElementException;
import dpHelper.exceptions.TypeException;
import dpHelper.tools.Errors;

import javax.lang.model.element.Element;
import javax.lang.model.element.Modifier;
import java.lang.annotation.Annotation;
import java.util.Collection;
import java.util.List;
import java.util.NoSuchElementException;

public class Specification extends AbstractSpecification {

   Specification(AbstractCheck previous, List<Element> elements) {
      super(previous, elements);
   }

   @Override
   public ISpecification withName(String name)
         throws NoSuchElementException, NumberOfElementException {

      String msg = Errors.doNotMatchDescription(kind, NAME, name);
      super.name(name, msg, new CheckIfNotEmpty());
      return this;
   }

   @Override
   public ISpecification withNameContaining(String name)
         throws NoSuchElementException, NumberOfElementException {
      String msg = Errors.doNotMatchDescription(kind, NAME_CONTAIN, name);
      super.nameContain(name, msg, new CheckIfNotEmpty());
      return this;
   }

   @Override
   public ISpecification withModifiers(Modifier... modifiers)
         throws ModifierException, NumberOfElementException {

      String msg = Errors.doNotMatchDescription(kind, MODIFIERS, modifiers);
      super.modifiers(modifiers, msg, new CheckIfNotEmpty());
      return this;
   }

   @Override
   public ISpecification withType(Element type)
         throws TypeException, NumberOfElementException {

      String msg = Errors.doNotMatchDescription(kind, TYPE, type);
      super.type(type, msg, new CheckIfNotEmpty());
      return this;
   }

   @Override
   public ISpecification withOneOfTheseTypes(Collection<Element> types)
         throws TypeException, NumberOfElementException {

      String msg = Errors.doNotMatchDescription(kind, ONE_OF_TYPES, types);
      super.oneOfTheseTypes(types, msg, new CheckIfNotEmpty());
      return this;
   }


   @Override
   public ISpecification withSuperClass(Element superClass)
         throws TypeException, NumberOfElementException {

      String msg = Errors.doNotMatchDescription(kind, SUPER_CLASS, superClass);
      super.superClass(superClass, msg, new CheckIfNotEmpty());
      return this;
   }

   @Override
   public ISpecification withInterface(Element interfaceE)
         throws TypeException, NumberOfElementException {

      String msg = Errors.doNotMatchDescription(kind, INTERFACE, interfaceE);
      super.interfaceE(interfaceE, msg, new CheckIfNotEmpty());
      return this;
   }

   @Override
   public ISpecification withInterfaces(Collection<Element> interfaces)
         throws TypeException, NumberOfElementException {

      String msg = Errors.doNotMatchDescription(kind, INTERFACES, interfaces);
      super.interfaces(interfaces, msg, new CheckIfNotEmpty());
      return this;
   }

   @Override
   public ISpecification withOneOfTheseInterfaces(Collection<Element> interfaces)
         throws TypeException, NumberOfElementException {

      String msg = Errors.doNotMatchDescription(
            kind, ONE_OF_INTERFACES, interfaces);
      super.oneOfTheseInterfaces(interfaces, msg, new CheckIfNotEmpty());
      return this;
   }


   @Override
   public ISpecification haveName(String name)
         throws NoSuchElementException, NumberOfElementException {

      String msg = Errors.doNotHaveWhatExpected(kind, NAME, name);
      super.name(name, msg, new CheckIfSizeMatch());
      return this;
   }

   @Override
   public ISpecification haveModifiers(Modifier... modifiers)
         throws ModifierException, NumberOfElementException {

      String msg = Errors.doNotHaveWhatExpected(kind, MODIFIERS, modifiers);
      super.modifiers(modifiers, msg, new CheckIfSizeMatch());
      return this;
   }

   @Override
   public ISpecification haveType(Element type)
         throws TypeException, NumberOfElementException {

      String msg = Errors.doNotHaveWhatExpected(kind, TYPE, type);
      super.type(type, msg, new CheckIfSizeMatch());
      return this;
   }

   @Override
   public ISpecification haveOneOfTheseTypes(Collection<Element> types)
         throws TypeException, NumberOfElementException {

      String msg = Errors.doNotHaveWhatExpected(kind, ONE_OF_TYPES, types);
      super.oneOfTheseTypes(types, msg, new CheckIfSizeMatch());
      return this;
   }

   @Override
   public ISpecification haveDistinctTypes(Collection<Element> types)
         throws TypeException, NumberOfElementException {

      super.distinctTypes(types, new CheckIfSizeMatch());
      return this;
   }

   @Override
   public ISpecification haveParameter(Element parameter)
         throws TypeException, NumberOfElementException {
      String msg = Errors.doNotHaveWhatExpected(kind, PARAMETER, parameter);
      super.parameter(parameter, msg, new CheckIfSizeMatch());
      return this;
   }

   @Override
   public ISpecification extendsClass(Element superClass)
         throws TypeException, NumberOfElementException {

      String msg = Errors.doNotHaveWhatExpected(kind, SUPER_CLASS, superClass);
      super.superClass(superClass, msg, new CheckIfSizeMatch());
      return this;
   }

   @Override
   public ISpecification implementsInterface(Element interfaceE)
         throws TypeException, NumberOfElementException {

      String msg = Errors.doNotHaveWhatExpected(kind, INTERFACE, interfaceE);
      super.interfaceE(interfaceE, msg, new CheckIfSizeMatch());
      return this;
   }

   @Override
   public ISpecification implementsInterfaces(Collection<Element> interfaces)
         throws TypeException, NumberOfElementException {

      String msg = Errors.doNotHaveWhatExpected(kind, INTERFACES, interfaces);
      super.interfaces(interfaces, msg, new CheckIfSizeMatch());
      return this;
   }

   @Override
   public ISpecification implementsOneOfInterfaces(Collection<Element> interfaces)
         throws TypeException, NumberOfElementException {

      String msg = Errors.doNotHaveWhatExpected(
            kind, ONE_OF_INTERFACES, interfaces);
      super.oneOfTheseInterfaces(interfaces, msg, new CheckIfSizeMatch());
      return this;
   }

   @Override
   public ISpecification implementsDistinctInterfaces(Collection<Element> interfaces)
         throws TypeException, NumberOfElementException {

      super.distinctInterfaces(interfaces, new CheckIfSizeMatch());
      return this;
   }

   @Override
   public ISpecification annotatedWith(Class<? extends Annotation> annotation)
         throws NoSuchElementException, NumberOfElementException {

      String msg = Errors.doNotHaveWhatExpected(
            kind, ANNOTATION, annotation);
      super.annotatedWith(annotation, msg, new CheckIfSizeMatch());
      return this;
   }

   @Override
   public ISpecification descendantOf(Element element)
         throws NoSuchElementException, TypeException, NumberOfElementException {
      String msg = Errors.isNotWhatExpected(
            kind, DESCENDANT, element);
      super.descendantOf(element, msg, new CheckIfSizeMatch());
      return this;
   }

   @Override
   public ISpecification descendantOfOneOf(Collection<Element> elements)
         throws NoSuchElementException, TypeException, NumberOfElementException {
      String msg = Errors.isNotWhatExpected(
            kind, DESCENDANT_ONE_OF, elements);
      super.descendantOfOneOf(elements, msg, new CheckIfSizeMatch());
      return this;
   }

   @Override
   public ISpecification descendantOfOnlyOneOf(Collection<Element> elements)
         throws NumberOfElementException, TypeException, NoSuchElementException {
      String msg = Errors.isNotWhatExpected(
            kind, DESCENDANT_ONLY_ONE_OF, elements);
      super.descendantOfOnlyOneOf(elements, msg, new CheckIfSizeMatch());
      return this;
   }

}
