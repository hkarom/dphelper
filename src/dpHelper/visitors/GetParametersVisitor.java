package dpHelper.visitors;

import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.VariableElement;

import static dpHelper.tools.ProcessorTools.TYPES;

public class GetParametersVisitor extends AbstractVisitor<Boolean, Void> {

   @Override
   public Boolean visitExecutable(ExecutableElement element, Void aVoid) {
      for(VariableElement var : element.getParameters()) {
         foundElements.add(TYPES.asElement(var.asType()));
      }
      return super.visitExecutable(element, aVoid);
   }

}
