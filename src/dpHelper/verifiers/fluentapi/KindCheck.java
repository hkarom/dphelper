package dpHelper.verifiers.fluentapi;

import dpHelper.exceptions.ElementKindException;
import dpHelper.exceptions.NumberOfElementException;
import dpHelper.tools.Errors;

import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import java.util.ArrayList;
import java.util.List;

import static dpHelper.verifiers.fluentapi.Checker.Constraint.FIXED;

public class KindCheck extends AbstractCheck implements IKindCheck {

   private List<Element> rootElements;

   KindCheck(Element element) {
      super(FIXED, 1);
      rootElements = new ArrayList<>();
      rootElements.add(element);
   }

   KindCheck(List<Element> elements) {
      super(FIXED, elements.size());
      rootElements = elements;
   }

   @Override
   public ISpecification is(ElementKind kind)
         throws ElementKindException, NumberOfElementException {

      selectedElements = new ArrayList<>();
      for (Element e : rootElements) {
         if (e.getKind().equals(kind))
            selectedElements.add(e);
      }

      if (selectedElements.isEmpty()) {
         String message = Errors.kindDoesNotMatch(kind, rootElements);
         throw new ElementKindException(message);
      }
      verifyConstraint("is", rootElements, selectedElements);
      this.kind = kind;
      return new Specification(this, selectedElements);
   }

   @Override
   public ISpecification are(ElementKind kind)
         throws ElementKindException, NumberOfElementException {
      return is(kind);
   }
}
