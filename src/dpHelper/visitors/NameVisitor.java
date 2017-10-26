package dpHelper.visitors;

import javax.lang.model.element.*;


public class NameVisitor extends AbstractVisitor<Boolean, String> {

    @Override
    public Boolean visitExecutable(ExecutableElement executableElement, String name) {
        Boolean result = executableElement.getSimpleName().contentEquals(name);
        if (result) {
            this.foundElements.add(executableElement);
            return true;
        }
        return super.visitExecutable(executableElement,name);
    }

    @Override
    public Boolean visitPackage(PackageElement packageElement, String name) {
        Boolean result = packageElement.getSimpleName().contentEquals(name);
        if (result) {
            this.foundElements.add(packageElement);
            return true;
        }
        return super.visitPackage(packageElement,name);
    }

    @Override
    public Boolean visitType(TypeElement typeElement, String name) {
        Boolean result = typeElement.getSimpleName().contentEquals(name);
        if (result) {
            this.foundElements.add(typeElement);
            return true;
        }
        return super.visitType(typeElement,name);
    }

    @Override
    public Boolean visitTypeParameter(TypeParameterElement typeParameterElement, String name) {
        Boolean result = typeParameterElement.getSimpleName().contentEquals(name);
        if (result) {
            this.foundElements.add(typeParameterElement);
            return true;
        }
        return super.visitTypeParameter(typeParameterElement,name);
    }

    @Override
    public Boolean visitVariable(VariableElement variableElement, String name) {
        Boolean result = variableElement.getSimpleName().contentEquals(name);
        if (result) {
            this.foundElements.add(variableElement);
            return true;
        }
        return super.visitVariable(variableElement,name);
    }
}
