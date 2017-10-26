package dpHelper.generators;

import dpHelper.generators.codeElements.EClass;

import javax.lang.model.element.Element;
import java.util.ArrayList;
import java.util.List;

public class AbstractFactoryGenerator extends AbstractGenerator {


   List<EClass> classes = new ArrayList<>();


   public AbstractFactoryGenerator(List<Element> elements) {

   }


   @Override
   public void launchGeneration() {

   }


   public String prepareAbsFactoryGeneration(Element absFactory) {
         return "";
   }



   public String prepareProductGeneration(Element product) {
      return "";
   }

   public String prepareFactoryGeneration(Element factory) {
      return "";
   }
}
