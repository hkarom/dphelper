package unitTests;

import com.google.testing.compile.JavaFileObjects;
import com.google.testing.compile.JavaSourcesSubjectFactory;

import javax.tools.JavaFileObject;
import java.io.File;
import java.net.MalformedURLException;
import java.util.LinkedList;
import java.util.List;

import static com.google.common.truth.Truth.ASSERT;
import static com.google.testing.compile.JavaSourceSubjectFactory.javaSource;


public class CompilerProcessorTest {

  public static JavaFileObject initSource(File source) {
    try {

      JavaFileObject fileObject = JavaFileObjects.forResource(source
          .toURI().toURL());

      return fileObject;
    } catch (MalformedURLException e) {
      e.printStackTrace();
      return null;
    }
  }

  public static List<JavaFileObject> initSources(File[] sources) {
    try {
      List<JavaFileObject> fileObjects = new LinkedList<>();
      for (int i = 0; i < sources.length; i++) {
        JavaFileObject fileObject = JavaFileObjects.forResource(
            sources[i].toURI().toURL());
        fileObjects.add(fileObject);
      }
      return fileObjects;
    } catch (MalformedURLException e) {
      e.printStackTrace();
      return null;
    }
  }

   public static PatternsProcessorTest compile(File source) {
    PatternsProcessorTest processorTest = new PatternsProcessorTest();
    JavaFileObject fileObject = initSource(source);
    ASSERT.about(javaSource())
        .that(fileObject)
        .processedWith(processorTest)
        .compilesWithoutError();
    return processorTest;
  }

   public static PatternsProcessorTest compile(File[] sources) {
    PatternsProcessorTest processorTest = new PatternsProcessorTest();
    List<JavaFileObject> fileObjects = initSources(sources);

    ASSERT.about(JavaSourcesSubjectFactory.javaSources())
        .that(fileObjects)
        .processedWith(processorTest)
        .compilesWithoutError();
    return processorTest;
  }
}
