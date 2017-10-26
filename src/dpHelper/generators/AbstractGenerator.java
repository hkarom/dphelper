package dpHelper.generators;


public abstract class AbstractGenerator {

   final String ROOT = "resources.experiments";
   final String FOLDER_NAME = "generated_files";
   protected Boolean launch;






   public String consoleDisplayFrame(String body) {
      StringBuilder str = new StringBuilder();
      str.append("\n/*----Generated code----*/\n");
      str.append(body);
      str.append("/*----------------------*/\n");
      return str.toString();
   }


   public abstract void launchGeneration();

}
