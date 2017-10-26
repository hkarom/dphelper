package dpHelper.visitors;

import java.util.Set;
import javax.lang.model.element.*;

public class ModifierVisitor extends AbstractVisitor<Boolean, Set<Modifier>> {

    @Override
    public Boolean visitExecutable(ExecutableElement element, Set<Modifier> modifiers) {
        Boolean result = element.getModifiers().containsAll(modifiers);
        if (result) {
            this.foundElements.add(element);
            return true;
        }
        return super.visitExecutable(element,modifiers);
    }

    @Override
    public Boolean visitPackage(PackageElement element, Set<Modifier> modifiers) {
        Boolean result = element.getModifiers().containsAll(modifiers);
        if (result) {
            this.foundElements.add(element);
            return true;
        }
        return super.visitPackage(element,modifiers);
    }

    @Override
    public Boolean visitType(TypeElement element, Set<Modifier> modifiers) {
        Boolean result = element.getModifiers().containsAll(modifiers);
        if (result) {
            this.foundElements.add(element);
            return true;
        }
        return super.visitType(element,modifiers);
    }

    @Override
    public Boolean visitTypeParameter(TypeParameterElement element, Set<Modifier> modifiers) {
        Boolean result = element.getModifiers().containsAll(modifiers);
        if (result) {
            this.foundElements.add(element);
            return true;
        }
        return super.visitTypeParameter(element,modifiers);
    }

    @Override
    public Boolean visitVariable(VariableElement element, Set<Modifier> modifiers) {
        Boolean result = element.getModifiers().containsAll(modifiers);
        if (result) {
            this.foundElements.add(element);
            return true;
        }
        return super.visitVariable(element,modifiers);
    }
}
