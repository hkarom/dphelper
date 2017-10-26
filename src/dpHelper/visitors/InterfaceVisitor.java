package dpHelper.visitors;


import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.TypeMirror;
import java.util.*;

import static dpHelper.tools.ProcessorTools.putTypeMirror;


public class InterfaceVisitor extends AbstractVisitor<Boolean,
      Collection<Element>> {

   @Override
   public Boolean visitType(TypeElement typeElement, Collection<Element> elements) {
      Element[] arrayElement = elements.toArray(new Element[elements.size()]);
      Set<TypeMirror> interfaces = putTypeMirror(arrayElement);

      Boolean result = typeElement.getInterfaces().containsAll(interfaces);
      if (result) {
         this.foundElements.add(typeElement);
         return true;
      }
      return super.visitType(typeElement, elements);
   }
}
