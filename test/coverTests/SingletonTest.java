package coverTests;

import com.google.testing.compile.JavaFileObjects;
import dpHelper.PatternsProcessor;
import org.junit.Before;

import javax.tools.JavaFileObject;

import java.io.File;
import java.net.MalformedURLException;

import static com.google.common.truth.Truth.ASSERT;
import static com.google.testing.compile.JavaSourceSubjectFactory.javaSource;

public class SingletonTest {
  final String FILES_PATH = "resources/experiments/singleton/";
  private JavaFileObject fileObject;

  @Before
  public void setup() {
    File source = new File(FILES_PATH + "ClassSingletonValid.java");

    initSource(source);
  }

  @org.junit.Test
  public void testSingletonValid() {
    File source = new File(FILES_PATH + "ClassSingletonValid.java");
    initSource(source);

    ASSERT.about(javaSource())
        .that(fileObject)
        .processedWith(new PatternsProcessor())
        .compilesWithoutError();

  }

  @org.junit.Test
  public void testSingletonWithPublicConstructor() {
    File source = new File(FILES_PATH + "SingletonWithPublicConstructor.java");
    initSource(source);

    ASSERT.about(javaSource())
        .that(fileObject)
        .processedWith(new PatternsProcessor())
        .failsToCompile()
        .withErrorContaining("Need all constructors to be private");

  }

  @org.junit.Test
  public void testSingletonWithPublicInstance() {
    File source = new File(FILES_PATH + "SingletonWithPublicInstance.java");
    initSource(source);

    ASSERT.about(javaSource())
        .that(fileObject)
        .processedWith(new PatternsProcessor())
        .failsToCompile()
        .withErrorContaining("Need unique private static field with type " +
            "SingletonWithPublicInstance");

  }

  @org.junit.Test
  public void testSingletonWithInstanceNotHavingTypeOfClass() {
    File source = new File(FILES_PATH + "InstanceNotHavingTypeOfClass.java");
    initSource(source);

    ASSERT.about(javaSource())
        .that(fileObject)
        .processedWith(new PatternsProcessor())
        .failsToCompile()
        .withErrorContaining("Need unique private static field with type " +
            "InstanceNotHavingTypeOfClass");

  }

  @org.junit.Test
  public void testSingletonWithInstanceNotStatic() {
    File source = new File(FILES_PATH + "WithInstanceNotStatic.java");
    initSource(source);

    ASSERT.about(javaSource())
        .that(fileObject)
        .processedWith(new PatternsProcessor())
        .failsToCompile()
        .withErrorContaining("Need unique private static field with type " +
            "WithInstanceNotStatic");

  }

  @org.junit.Test
  public void testSingletonWithPrivateGetInstance() {
    File source = new File(FILES_PATH + "SingletonWithPrivateGetInstance.java");
    initSource(source);

    ASSERT.about(javaSource())
        .that(fileObject)
        .processedWith(new PatternsProcessor())
        .failsToCompile()
        .withErrorContaining("Need public static method(s) 'getInstance'");

  }

  @org.junit.Test
  public void testSingletonWithGetInstanceNotStatic() {
    File source = new File(FILES_PATH + "WithGetInstanceNotStatic.java");
    initSource(source);

    ASSERT.about(javaSource())
        .that(fileObject)
        .processedWith(new PatternsProcessor())
        .failsToCompile()
        .withErrorContaining("Need public static method(s) 'getInstance' " +
            "returning WithGetInstanceNotStatic");

  }

  @org.junit.Test
  public void testSingletonWithSyntaxError() {
    File source = new File(FILES_PATH + "SingletonWithSyntaxError.java");
    initSource(source);

    ASSERT.about(javaSource())
        .that(fileObject)
        .processedWith(new PatternsProcessor())
        .failsToCompile()
        .withErrorContaining("';' expected");

  }

  @org.junit.Test
  public void testSingletonWithGetInstanceNotHavingReturn() {
    File source = new File(FILES_PATH + "WithGetInstanceNotHavingReturn.java");
    initSource(source);

    ASSERT.about(javaSource())
        .that(fileObject)
        .processedWith(new PatternsProcessor())
        .failsToCompile()
        .withErrorContaining("missing return statement");

  }

  @org.junit.Test
  public void testSingletonImplementNotExistingInterface() {
    File source = new File(FILES_PATH + "ImplementNotExistingInterface.java");
    initSource(source);

    ASSERT.about(javaSource())
        .that(fileObject)
        .processedWith(new PatternsProcessor())
        .failsToCompile()
        .withErrorContaining("cannot find symbol\n" +
            "  symbol: class Xyz");

  }

  private void initSource(File source) {
    try {
      fileObject = JavaFileObjects.forResource(source.toURI().toURL());
    } catch (MalformedURLException e) {
      e.printStackTrace();
    }
  }
}
