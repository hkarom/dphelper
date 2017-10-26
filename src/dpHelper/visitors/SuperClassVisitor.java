package dpHelper.visitors;

import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;


public class SuperClassVisitor extends AbstractVisitor<Boolean, Element> {

    @Override
    public Boolean visitType(TypeElement element, Element superClass) {
        Boolean result = element.getSuperclass().equals(superClass.asType());
        if (result) {
            this.foundElements.add(element);
            return true;
        }
        return super.visitType(element,superClass);
    }
}
