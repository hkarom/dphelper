package dpHelper.verifiers.fluentapi;

import dpHelper.exceptions.NumberOfElementException;
import dpHelper.tools.Errors;
import dpHelper.tools.ProcessorTools;
import dpHelper.verifiers.fluentapi.Checker.Constraint;
import dpHelper.visitors.KindVisitor;

import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import java.util.Collection;
import java.util.NoSuchElementException;
import java.util.Set;

public class ExistCheck extends AbstractCheck implements IExistCheck {

   ExistCheck(ElementKind kind) {
      super(Constraint.AT_LEAST, 1, kind);
   }

   ExistCheck(Constraint constraint, ElementKind kind) {
      super(constraint, kind);
   }

   ExistCheck(Constraint constraint, int nbExpected, ElementKind kind) {
      super(constraint, nbExpected, kind);
   }

   @Override
   public ISpecification in(Element element)
         throws NoSuchElementException, NumberOfElementException {

      Set<Element> elementSet = ProcessorTools.put(element);
      inAll(elementSet);
      return new Specification(this, selectedElements);
   }

   @Override
   public ISpecification inAll(Collection<Element> elements)
         throws NoSuchElementException, NumberOfElementException {

      KindVisitor visitor = new KindVisitor();
      visitor.scan(elements, kind);
      selectedElements = visitor.getFoundElements();

      if (selectedElements.isEmpty()) {
         String error = Errors.kindNotFound(kind, elements);
         throw new NoSuchElementException(error);
      }
      initNbExpectedElements(elements);

      String message = Errors.constraintRuleNotRespected(this, elements, "in",
            elements, selectedElements.size());

      if (selectedElements.size() < nbExpectedElements)
         throw new NumberOfElementException(message);

      return new Specification(this, selectedElements);
   }

   private void initNbExpectedElements(Collection<Element> elements) {
      if (constraint.equals(Constraint.ALL))
         nbExpectedElements = selectedElements.size() * elements.size();
      else nbExpectedElements = nbExpectedElements * elements.size();
   }

}
