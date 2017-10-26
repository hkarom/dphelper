package dpHelper.visitors;

import javax.lang.model.element.*;


public class TypeVisitor extends AbstractVisitor<Boolean, Element> {

   @Override
   public Boolean visitExecutable(ExecutableElement element, Element type) {
      Boolean result;
      if (isConstructor(element))
         result = type.asType().equals(element.getEnclosingElement().asType());
      else
         result = type.asType().equals(element.getReturnType());

      if (result) {
         this.foundElements.add(element);
         return true;
      }
      return super.visitExecutable(element, type);
   }

   @Override
   public Boolean visitPackage(PackageElement element, Element type) {
      Boolean result = type.asType().equals(element.asType());
      if (result) {
         this.foundElements.add(element);
         return true;
      }
      return super.visitPackage(element, type);
   }

   @Override
   public Boolean visitType(TypeElement element, Element type) {
      Boolean result = type.asType().equals(element.asType());
      if (result) {
         this.foundElements.add(element);
         return true;
      }
      return super.visitType(element, type);
   }

   @Override
   public Boolean visitTypeParameter(TypeParameterElement element, Element type) {
      Boolean result = type.asType().equals(element.asType());
      if (result) {
         this.foundElements.add(element);
         return true;
      }
      return super.visitTypeParameter(element, type);
   }

   @Override
   public Boolean visitVariable(VariableElement element, Element type) {
      Boolean result = type.asType().equals(element.asType());
      if (result) {
         this.foundElements.add(element);
         return true;
      }
      return super.visitVariable(element, type);
   }

   private boolean isConstructor(Element element) {
      return "<init>".equals(element.getSimpleName().toString());
   }
}
