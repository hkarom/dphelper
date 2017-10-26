package dpHelper.visitors;


import javax.lang.model.element.Element;
import javax.lang.model.element.ExecutableElement;

import java.util.Arrays;

import static dpHelper.tools.ProcessorTools.getParametersOfMethods;

public class FindParameterVisitor extends AbstractVisitor<Boolean,Element> {

   @Override
   public Boolean visitExecutable(ExecutableElement executableElement, Element element) {

      Boolean result = getParametersOfMethods(Arrays.asList(executableElement))
                        .contains(element);
      if(result) {
         foundElements.add(executableElement);
         return true;
      }
      return super.visitExecutable(executableElement,element);
   }
}
