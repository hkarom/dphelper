package unitTests;


import javax.annotation.processing.*;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.Element;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.TypeElement;
import java.util.*;

public class PatternsProcessorTest implements Processor {

  private final SourceVersion supported_version = SourceVersion.latest();
  private final Set<String> options = Collections.emptySet();
  private final Set<String> supported_annotations = new HashSet<>(Arrays
      .asList("dpHelper.annotations.*"));


  private Set<? extends Element> annotatedElements;

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

  }

  @Override
  public boolean process(Set<? extends TypeElement> annotations,
                         RoundEnvironment roundEnv) {
    for (TypeElement annotation : annotations) {
      annotatedElements = roundEnv.getElementsAnnotatedWith(annotation);
    }
    return true;
  }

  @Override
  public Iterable<? extends Completion> getCompletions(Element element,
                               AnnotationMirror annotationMirror,
                               ExecutableElement executableElement, String s) {
    return null;
  }

   List<? extends Element> getAnnotatedElements() {
    List<Element> list = new ArrayList<>();
    for (Element element : annotatedElements) {
      list.add(element);
    }
    return list;
  }

}
