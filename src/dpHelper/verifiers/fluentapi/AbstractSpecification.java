package dpHelper.verifiers.fluentapi;

import dpHelper.exceptions.ModifierException;
import dpHelper.exceptions.NumberOfElementException;
import dpHelper.exceptions.TypeException;
import dpHelper.tools.Errors;
import dpHelper.visitors.*;

import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.Modifier;
import java.lang.annotation.Annotation;
import java.util.*;

import static dpHelper.tools.ProcessorTools.*;


abstract class AbstractSpecification implements ISpecification {

   final String NAME = "name";
   final String NAME_CONTAIN = "name containing";
   final String MODIFIERS = "modifier(s)";
   final String TYPE = "type";
   final String ONE_OF_TYPES = "one of these types";
   final String DISTINCT_TYPES = "distinct types from";
   final String PARAMETER = "parameter";
   final String SUPER_CLASS = "super class";
   final String INTERFACE = "interface";
   final String INTERFACES = "interfaces";
   final String ONE_OF_INTERFACES = "implements one of";
   final String DISTINCT_INTERFACES = "implements distinct from";
   final String ANNOTATION = "annotation";
   final String DESCENDANT = "descendant of";
   final String DESCENDANT_ONE_OF = "descendant of one of";
   final String DESCENDANT_ONLY_ONE_OF = "descendant of only one of";


   protected List<Element> selectedElements;
   protected AbstractCheck previousCheck;
   protected ElementKind kind;

   AbstractSpecification(AbstractCheck previous, List<Element> elements) {
      previousCheck = previous;
      selectedElements = new ArrayList<>(elements);
      kind = previous.getKind();
   }

   @Override
   public List<Element> getSelectedElements() {
      return selectedElements;
   }


   ISpecification name(String name, String message, VisitChecker check)
         throws NoSuchElementException, NumberOfElementException {

      NameVisitor visitor = new NameVisitor();
      visitor.scan(selectedElements, name);
      selectedElements = visitor.getFoundElements();
      if (!check.checkVisit(name, NAME))
         throw new NoSuchElementException(message);

      return this;
   }

   ISpecification nameContain(String subName, String message, VisitChecker check)
         throws NoSuchElementException, NumberOfElementException {

      List<Element> tmpElements = new ArrayList<>(selectedElements);
      for (Element element : tmpElements) {
         if (!element.getSimpleName().toString().contains(subName))
            selectedElements.remove(element);
      }
      if (!check.checkVisit(subName, NAME_CONTAIN))
         throw new NoSuchElementException(message);

      return this;
   }


   ISpecification modifiers(Modifier[] modifiers, String message, VisitChecker
         check)
         throws ModifierException, NumberOfElementException {

      ModifierVisitor visitor = new ModifierVisitor();
      visitor.scan(selectedElements, put(modifiers));
      selectedElements = visitor.getFoundElements();
      if (!check.checkVisit(arrayToString(modifiers), MODIFIERS))
         throw new ModifierException(message);

      return this;
   }


   ISpecification type(Element type, String message, VisitChecker check)
         throws TypeException, NumberOfElementException {

      TypeVisitor visitor = new TypeVisitor();
      visitor.scan(selectedElements, type);
      selectedElements = visitor.getFoundElements();
      if (!check.checkVisit(type, TYPE))
         throw new TypeException(message);

      return this;
   }


   ISpecification oneOfTheseTypes(Collection<Element> types, String message,
                                  VisitChecker check)
         throws TypeException, NumberOfElementException {

      TypeVisitor visitor = new TypeVisitor();
      HashSet<Element> tmpElements = new HashSet<>(selectedElements);
      selectedElements.clear();

      for (Element element : types) {
         visitor.scan(tmpElements, element);
         tmpElements.removeAll(visitor.getFoundElements());
         selectedElements.addAll(visitor.getFoundElements());
         if (tmpElements.isEmpty()) break;
         visitor.reset();
      }

      if (!check.checkVisit(types, ONE_OF_TYPES)) {
         throw new TypeException(message);
      }
      return this;
   }


   ISpecification distinctTypes(Collection<Element> types, VisitChecker check)
         throws TypeException, NumberOfElementException {

      haveOneOfTheseTypes(types);
      HashSet<Element> tmpElements = new HashSet<>(selectedElements);
      TypeVisitor visitor = new TypeVisitor();
      selectedElements.clear();
      String errors = "";

      for (Element type : types) {
         visitor.scan(tmpElements, type);
         if (visitor.getFoundElements().size() != 1)
            errors += Errors.isNotTypeOf(type, ONE_OF_TYPES, tmpElements);
         tmpElements.removeAll(visitor.getFoundElements());
         selectedElements.addAll(visitor.getFoundElements());
         visitor.reset();
      }

      if (errors.length() != 0) {
         selectedElements.clear();
         throw new TypeException(errors);
      }

      check.checkVisit(types, DISTINCT_TYPES);
      return this;
   }

   ISpecification parameter(Element parameter, String message, VisitChecker check)
         throws NumberOfElementException, TypeException {
      FindParameterVisitor visitor = new FindParameterVisitor();
      visitor.scan(selectedElements, parameter);
      selectedElements = visitor.getFoundElements();
      if (!check.checkVisit(parameter, PARAMETER)) {
         throw new TypeException(message);
      }
      return this;
   }


   ISpecification superClass(Element superClass, String message, VisitChecker check)
         throws TypeException, NumberOfElementException {

      SuperClassVisitor visitor = new SuperClassVisitor();
      visitor.scan(selectedElements, superClass);
      selectedElements = visitor.getFoundElements();
      if (!check.checkVisit(superClass, SUPER_CLASS)) {
         throw new TypeException(message);
      }
      return this;
   }


   ISpecification interfaceE(Element interfaceE, String message,
                             VisitChecker check)
         throws TypeException, NumberOfElementException {

      InterfaceVisitor visitor = new InterfaceVisitor();
      visitor.scan(selectedElements, put(interfaceE));
      selectedElements = visitor.getFoundElements();
      if (!check.checkVisit(interfaceE, INTERFACE)) {
         throw new TypeException(message);
      }
      return this;
   }


   ISpecification interfaces(Collection<Element> interfaces, String message,
                             VisitChecker check)
         throws TypeException, NumberOfElementException {

      InterfaceVisitor visitor = new InterfaceVisitor();
      visitor.scan(selectedElements, interfaces);
      selectedElements = visitor.getFoundElements();
      if (!check.checkVisit(interfaces, INTERFACES)) {
         throw new TypeException(message);
      }
      return this;
   }


   ISpecification oneOfTheseInterfaces(Collection<Element> interfaces, String message,
                                       VisitChecker check)
         throws TypeException, NumberOfElementException {

      InterfaceVisitor visitor = new InterfaceVisitor();
      HashSet<Element> tmpElements = new HashSet<>(selectedElements);
      selectedElements.clear();

      for (Element element : interfaces) {
         visitor.scan(tmpElements, put(element));
         tmpElements.removeAll(visitor.getFoundElements());
         selectedElements.addAll(visitor.getFoundElements());
         if (tmpElements.isEmpty()) break;
         visitor.reset();
      }

      if (!check.checkVisit(interfaces, ONE_OF_INTERFACES)) {
         throw new TypeException(message);
      }
      return this;
   }


   ISpecification distinctInterfaces(Collection<Element> interfaces, VisitChecker check)
         throws TypeException, NumberOfElementException {

      implementsOneOfInterfaces(interfaces);
      HashSet<Element> tmpElements = new HashSet<>(selectedElements);
      InterfaceVisitor visitor = new InterfaceVisitor();
      selectedElements.clear();
      String errors = "";

      for (Element interface1 : interfaces) {
         visitor.scan(tmpElements, put(interface1));
         if (visitor.getFoundElements().size() != 1)
            errors += Errors.isNotImplementedBy(interface1, ONE_OF_INTERFACES, tmpElements);
         selectedElements.addAll(visitor.getFoundElements());
         visitor.reset();
      }

      if (errors.length() != 0) {
         selectedElements.clear();
         throw new TypeException(errors);
      }
      check.checkVisit(interfaces, DISTINCT_INTERFACES);
      return this;
   }

   ISpecification annotatedWith(Class<? extends Annotation> annotation, String message,
                                VisitChecker check)
         throws NoSuchElementException, NumberOfElementException {

      AnnotationVisitor visitor = new AnnotationVisitor();
      visitor.scan(selectedElements, annotation);
      selectedElements = visitor.getFoundElements();
      if (!check.checkVisit(annotation, ANNOTATION))
         throw new NoSuchElementException(message);

      return this;
   }

   ISpecification descendantOf(Element parent, String message, VisitChecker check)
         throws NoSuchElementException, TypeException, NumberOfElementException {

      Collection<Element> descendants = getDescendants(parent);

      if (selectedElements.contains(parent))
         throw new TypeException(Errors.cannotBeDescendantOfItself(parent));

      List<Element> tmpElements = new ArrayList<>();
      for (Element element : selectedElements) {
         if (descendants.contains(element))
            tmpElements.add(element);
      }
      selectedElements = tmpElements;
      if (!check.checkVisit(parent, DESCENDANT))
         throw new NoSuchElementException(message);
      return this;
   }

   ISpecification descendantOfOneOf(Collection<Element> parents, String message,
                                    VisitChecker check)
         throws NoSuchElementException, TypeException, NumberOfElementException {

      List<Element> initialSelection = new ArrayList<>(selectedElements);
      Set<Element> tmpElements = new HashSet<>();
      for (Element parent : parents) {
         try {
            descendantOf(parent);
            tmpElements.addAll(selectedElements);
         } catch (NoSuchElementException | NumberOfElementException e) {
            selectedElements = initialSelection;
            continue;
         }
      }
      selectedElements = new ArrayList<>(tmpElements);
      if (!check.checkVisit(parents, DESCENDANT_ONE_OF))
         throw new NoSuchElementException(message);
      return this;
   }

   ISpecification descendantOfOnlyOneOf(Collection<Element> parents, String message,
                                        VisitChecker check)
         throws NoSuchElementException, TypeException, NumberOfElementException {


      List<Element> initialSelection = new ArrayList<>(selectedElements);
      Set<Element> tmpElements = new HashSet<>();
      for (Element parent : parents) {
         try {
            descendantOf(parent);
            tmpElements.addAll(selectedElements);
         } catch (NoSuchElementException | NumberOfElementException e) {
            selectedElements = initialSelection;
            continue;
         }
         if (selectedElements.size() > 1)
            throw new TypeException(message);
      }
      selectedElements = new ArrayList<>(tmpElements);
      ;
      if (!check.checkVisit(parents, DESCENDANT_ONLY_ONE_OF))
         throw new NoSuchElementException(message);
      return this;
   }


   abstract class VisitChecker {

      abstract boolean checkVisit(Object element, String function)
            throws NumberOfElementException;
   }

   class CheckIfNotEmpty extends VisitChecker {

      boolean checkVisit(Object element, String condition)
            throws NumberOfElementException {

         if (!selectedElements.isEmpty()) {
            previousCheck.setNbExpectedElements(selectedElements.size());
            return true;
         }
         selectedElements.clear();
         return false;
      }
   }

   class CheckIfSizeMatch extends VisitChecker {

      boolean checkVisit(Object element, String condition)
            throws NumberOfElementException {

         previousCheck.verifyConstraint(condition, element, selectedElements);
         return true;
      }

   }
}
