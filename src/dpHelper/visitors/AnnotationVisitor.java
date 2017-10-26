package dpHelper.visitors;

import javax.lang.model.element.*;
import java.lang.annotation.Annotation;


public class AnnotationVisitor extends AbstractVisitor<Boolean,
      Class<? extends Annotation>> {

   @Override
   public Boolean visitExecutable(ExecutableElement element, Class<? extends Annotation> annotation) {
      if(visitElement(element,annotation)) return true;
      return super.visitExecutable(element,annotation);
   }

   @Override
   public Boolean visitPackage(PackageElement element, Class<? extends Annotation> annotation) {
      if(visitElement(element,annotation)) return true;
      return super.visitPackage(element,annotation);
   }

   @Override
   public Boolean visitType(TypeElement element, Class<? extends Annotation> annotation) {
      if(visitElement(element,annotation)) return true;
      return super.visitType(element,annotation);
   }

   @Override
   public Boolean visitTypeParameter(TypeParameterElement element, Class<?
         extends Annotation> annotation) {
      if(visitElement(element,annotation)) return true;
      return super.visitTypeParameter(element,annotation);
   }

   @Override
   public Boolean visitVariable(VariableElement element, Class<? extends Annotation> annotation) {
      if(visitElement(element,annotation)) return true;
      return super.visitVariable(element,annotation);
   }

   private Boolean visitElement(Element element, Class<? extends Annotation>
         annotation) {
      Boolean result = element.getAnnotation(annotation) != null;
      if(result) {
         foundElements.add(element);
         return true;
      }
      return false;
   }
}
