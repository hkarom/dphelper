package dpHelper.verifiers.fluentapi;


import dpHelper.exceptions.NumberOfElementException;
import dpHelper.tools.Errors;

import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import java.util.List;


public class AbstractCheck {

   protected List<Element> selectedElements;
   protected Checker.Constraint constraint;
   protected Integer nbExpectedElements;
   protected ElementKind kind;

   AbstractCheck(Checker.Constraint constraint, int nbExpected) {
      this.constraint = constraint;
      this.nbExpectedElements = nbExpected;
   }

   AbstractCheck(Checker.Constraint constraint, ElementKind kind) {
      this.constraint = constraint;
      this.kind = kind;
   }

   AbstractCheck(Checker.Constraint constraint, int nbExpected, ElementKind kind) {
      this.constraint = constraint;
      nbExpectedElements = nbExpected;
      this.kind = kind;
   }

   public ElementKind getKind() {
      return kind;
   }

   public Checker.Constraint getConstraint() {
      return constraint;
   }

   public int getNbExpectedElements() {
      return nbExpectedElements;
   }

   public void setNbExpectedElements(int newNumber) {
      nbExpectedElements =
            newNumber;
   }


   void verifyConstraint(String condition, Object element, List<Element> currents)
         throws NumberOfElementException {

      String message = Errors.constraintRuleNotRespected(this, selectedElements,
            condition, element, currents.size());
      switch (constraint) {
         case ALL:
         case FIXED:
            if (currents.size() != nbExpectedElements)
               throw new NumberOfElementException(message);
            break;

         case AT_LEAST:
            if (currents.size() < nbExpectedElements)
               throw new NumberOfElementException(message);
            break;

         default:
            break;
      }
   }

}
