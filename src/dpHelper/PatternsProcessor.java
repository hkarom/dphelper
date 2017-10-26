package dpHelper;

import com.sun.source.util.DocTrees;
import com.sun.source.util.Trees;
import dpHelper.annotations.*;
import dpHelper.annotations.Observer;
import dpHelper.tools.ProcessorTools;
import dpHelper.verifiers.*;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import java.util.*;

import static dpHelper.tools.ProcessorTools.reportNote;

public class PatternsProcessor extends AbstractProcessor {

   private final SourceVersion supported_version = SourceVersion.latest();
   private final Set<String> options = Collections.emptySet();
   private final Set<String> supported_annotations = new HashSet<>( Collections
         .singleton("dpHelper.annotations.*"));


   private Map<String, PatternVerifier<?>> verifiers = new HashMap<>();

   @Override
   public Set<String> getSupportedOptions() {
      return options;
   }

   @Override
   public Set<String> getSupportedAnnotationTypes() {
      return supported_annotations;
   }

   @Override
   public SourceVersion getSupportedSourceVersion() {
      return supported_version;
   }


   @Override
   public void init(ProcessingEnvironment processingEnv) {
      ProcessorTools.TYPES = processingEnv.getTypeUtils();
      ProcessorTools.FILER = processingEnv.getFiler();
      ProcessorTools.ELEMENTS = processingEnv.getElementUtils();
      ProcessorTools.MESSAGER = processingEnv.getMessager();
      ProcessorTools.TREES = Trees.instance(processingEnv);
      ProcessorTools.DOCTREES = DocTrees.instance(processingEnv);
      initializeVerifiers();
   }

   @Override
   public boolean process(Set<? extends TypeElement> annotations,
                          RoundEnvironment roundEnv) {
      reportNote(annotations.toString());
      //reportNote(roundEnv.toString());
      reportNote(" --------------------------------------------- ");

      initCompilingClass(roundEnv.getRootElements());

      for (TypeElement annotation : annotations) {
         String q_name = annotation.getQualifiedName().toString();

         if (verifiers.containsKey(q_name)) {
            Set<Element> annotatedElements = new HashSet<>(roundEnv
                  .getElementsAnnotatedWith(annotation));
            verifiers.get(q_name).verifyAll(annotatedElements);
         }
      }
      return true;
   }

   private void initCompilingClass(Set<? extends Element> rootElements) {
      for (Element element : rootElements) {
         ProcessorTools.allCompilingClasses.put(element.toString(), element);
      }
   }


   private void initializeVerifiers() {
      verifiers.put(Singleton.class.getCanonicalName(), new
            SingletonVerifier(Singleton.class.getSimpleName()));

      verifiers.put(Singleton.Synchronized.class.getCanonicalName(), new
            SingletonVerifier(Singleton.Synchronized.class.getSimpleName()));

      verifiers.put(AbstractFactory.class.getCanonicalName(), new
            AbstractFactoryVerifier(AbstractFactory.class.getSimpleName()));

      verifiers.put(AbstractFactory.Product.class.getCanonicalName(), new
            AbstractFactoryVerifier(AbstractFactory.Product.class.getSimpleName()));

      verifiers.put(AbstractFactory.Factory.class.getCanonicalName(), new
            AbstractFactoryVerifier(AbstractFactory.Factory.class.getSimpleName()));

      verifiers.put(Visitor.class.getCanonicalName(), new
            VisitorVerifier(Visitor.class.getSimpleName()));

      verifiers.put(Visitor.Visitable.class.getCanonicalName(), new
            VisitorVerifier(Visitor.Visitable.class.getSimpleName()));

      verifiers.put(Visitor.Concrete.class.getCanonicalName(), new
            VisitorVerifier(Visitor.Concrete.class.getSimpleName()));

      verifiers.put(Observer.class.getCanonicalName(), new ObserverVerifier());

      verifiers.put(Decorator.class.getCanonicalName(), new DecoratorVerifier());
   }
}
