package coverTests;

import com.google.testing.compile.JavaSourcesSubjectFactory;
import dpHelper.PatternsProcessor;
import org.junit.Before;

import javax.tools.JavaFileObject;
import java.io.File;
import java.util.List;

import static com.google.common.truth.Truth.ASSERT;
import static unitTests.CompilerProcessorTest.initSources;


public class TestDecorator {
   final String FILES_PATH = "resources/experiments/decorator/";
   private List<JavaFileObject> fileObjects;
   @Before
   public void setup() {

   }

   @org.junit.Test
   public void testDecoratorValid() {
      fileObjects = initSources(getSources("decoratorValid"));

      ASSERT.about(JavaSourcesSubjectFactory.javaSources())
            .that(fileObjects)
            .processedWith(new PatternsProcessor())
            .compilesWithoutError();

   }

   @org.junit.Test
   public void testDecoratorHavingNoAggregation() {
      fileObjects = initSources(getSources("decoratorWithoutAgregation"));

      ASSERT.about(JavaSourcesSubjectFactory.javaSources())
            .that(fileObjects)
            .processedWith(new PatternsProcessor())
            .failsToCompile()
            .withErrorCount(0)
            .withErrorContaining("Not any FIELD found in [ComponentDecorator]");
   }
   private File [] getSources(String directory){
      File [] sources = new File[6];
      sources[0] = new File(FILES_PATH+"/"+directory+"/Component.java");
      sources[1] = new File(FILES_PATH+"/"+directory+"/ComponentConcret.java");
      sources[2] = new File(FILES_PATH+"/"+directory+"/ComponentConcret2.java");
      sources[3] = new File(FILES_PATH+"/"+directory+"/ComponentDecorator.java");
      sources[4] = new File(FILES_PATH+"/"+directory+"/DecoratorConcret1.java");
      sources[5] = new File(FILES_PATH+"/"+directory+"/DecoratorConcret2.java");
      return sources;
   }
}