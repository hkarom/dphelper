package unitTests;

import dpHelper.exceptions.NumberOfElementException;
import org.junit.Before;
import org.junit.Test;

import javax.lang.model.element.*;

import java.io.File;
import java.util.*;

import static dpHelper.verifiers.fluentapi.Checker.CHECK;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class TestChecker {
  final String FILES_PATH = "resources/experiments/filesForUnitTests/";

  private List<Element> foundElements;
  private Element constructor;
  private Element method;
  private Element variable;
  private PatternsProcessorTest processorTest;
  private Element annotatedElement1;
  private Element annotatedElement2;


  @Before
  public void setup() {
    processorTest = new PatternsProcessorTest();
    File[] sources = new File[5];
    sources[0] = new File(FILES_PATH + "Classe1.java");
    sources[1] = new File(FILES_PATH + "Classe2.java");
    sources[2] = new File(FILES_PATH + "SuperClass.java");
    sources[3] = new File(FILES_PATH + "Interface1.java");
    sources[4] = new File(FILES_PATH + "Interface2.java");

    processorTest = CompilerProcessorTest.compile(sources);
    annotatedElement1 = processorTest.getAnnotatedElements().get(0);
    annotatedElement2 = processorTest.getAnnotatedElements().get(1);

    variable = annotatedElement1.getEnclosedElements().get(0);
    constructor = annotatedElement1.getEnclosedElements().get(1);
    method = annotatedElement1.getEnclosedElements().get(2);

    foundElements = new ArrayList<>();
  }

  @Test
  public void TestFindConstructors() {
    launchFind(annotatedElement1, ElementKind.CONSTRUCTOR);
    assertThat(foundElements, hasSize(1));
    assertThat(foundElements, contains(constructor));
  }

  @Test
  public void TestFindMethods() {
    launchFind(annotatedElement1, ElementKind.METHOD);
    assertThat(foundElements, hasSize(1));
    assertThat(foundElements, contains(method));
  }

  @Test
  public void TestNotFindMethods() {
    launchFind(annotatedElement2, ElementKind.METHOD);
    assertThat(foundElements, hasSize(0));
    assertThat(foundElements, not(contains(method)));
  }


  @Test
  public void TestFindFields() {
    launchFind(annotatedElement1, ElementKind.FIELD);
    assertThat(foundElements, hasSize(1));
    assertThat(foundElements, contains(variable));
  }

  @Test
  public void TestNotFindFields() {
    launchFind(annotatedElement2, ElementKind.FIELD);
    assertThat(foundElements, hasSize(0));
    assertThat(foundElements, not(contains(variable)));
  }


  private boolean launchFind(Element element, ElementKind kind) {
    try {
      foundElements = CHECK.that(kind).in(element).getSelectedElements();
    } catch (NoSuchElementException | NumberOfElementException e) {
      return false;
    }
    return true;
  }

}