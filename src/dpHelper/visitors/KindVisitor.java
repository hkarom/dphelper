package dpHelper.visitors;

import javax.lang.model.element.*;

public class KindVisitor extends AbstractVisitor<Boolean, ElementKind> {

    @Override
    public Boolean visitExecutable(ExecutableElement element, ElementKind kind) {
        Boolean result = element.getKind().equals(kind);
        if(result) {
            foundElements.add(element);
            return true;
        }
        return super.visitExecutable(element,kind);
    }

    @Override
    public Boolean visitPackage(PackageElement element, ElementKind kind) {
        Boolean result = element.getKind().equals(kind);
        if(result) {
            foundElements.add(element);
            return true;
        }
        return super.visitPackage(element,kind);
    }

    @Override
    public Boolean visitType(TypeElement element, ElementKind kind) {
        Boolean result = element.getKind().equals(kind);
        if(result) {
            foundElements.add(element);
            return true;
        }
        return super.visitType(element,kind);
    }

    @Override
    public Boolean visitTypeParameter(TypeParameterElement element, ElementKind kind) {
        Boolean result = element.getKind().equals(kind);
        if(result) {
            foundElements.add(element);
            return true;
        }
        return super.visitTypeParameter(element,kind);
    }

    @Override
    public Boolean visitVariable(VariableElement element, ElementKind kind) {
        Boolean result = element.getKind().equals(kind);
        if(result) {
            foundElements.add(element);
            return true;
        }
        return super.visitVariable(element,kind);
    }
}
