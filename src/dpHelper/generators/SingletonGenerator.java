package dpHelper.generators;


import com.sun.source.tree.CompilationUnitTree;
import dpHelper.generators.codeElements.*;

import javax.lang.model.element.Element;
import javax.tools.JavaFileObject;
import java.io.IOException;
import java.io.Writer;
import java.util.*;

import static dpHelper.tools.ProcessorTools.*;


public class SingletonGenerator extends AbstractGenerator {

   final String instanceName = "INSTANCE";

   private Element root;
   private ECode eSource;
   private EClass annotatedClass;

   public SingletonGenerator(Element element) {
      root = element;
      CompilationUnitTree originNode = TREES.getPath(root).getCompilationUnit();
      eSource = new ECode(originNode);
      annotatedClass = eSource.getClassByName(root.getSimpleName().toString());
      launch = false;
   }


   public void launchGeneration() {

      if (launch) {
         eSource.setPackage(eSource.getPackageName() + "." + FOLDER_NAME);
         try {
            String file = String.format("%s.%s.%s", ROOT, eSource.getPackageName(), root
                  .getSimpleName());
            JavaFileObject in = createSourceFile(file);
            Writer writer = in.openWriter();
            writer.write(eSource.toString());
            writer.close();

         } catch (IOException e) {
            reportWarning("Could not proceed to generation" + e.getMessage());
         }
      }
   }


   public String prepareConstructorGeneration() {

      Set<EConstructor> constructors = annotatedClass.getConstructors();
      StringBuilder str = new StringBuilder();
      for (EConstructor constructor : constructors) {
         constructor.clearModifier();
         constructor.addModifier("private");
         str.append(constructor.toString() + "\n");
      }

      launch = true;
      return consoleDisplayFrame(str.toString());
   }

   public String prepareMethodGeneration(String methodName) {
      StringBuilder str = new StringBuilder();
      List<EMethod> methods = annotatedClass.getMethodByName(methodName);
      String[] modifiers = {"public", "static"};
      boolean modified = false;

      for (EMethod method : methods) {
         if (method.getType().equals(annotatedClass.getName())) {
            modified = true;
            method.clearModifier();
            method.addModifier(modifiers);
            method.setBody(methodBody());
            str.append(method.toString() + "\n");
         }
      }
      if (!modified) str.append(createSingletonEMethod(modifiers, methodName));

      launch = true;
      return consoleDisplayFrame(str.toString());
   }

   private String createSingletonEMethod(String[] modifiers, String methodName) {
      EMethod method = new EMethod(modifiers, methodName, annotatedClass.getName(),
            new LinkedHashMap<>(), methodBody(), annotatedClass);
      annotatedClass.addElement(method, method.getKind());
      return method.toString() + "\n";
   }


   public String prepareInstanceGeneration() {

      StringBuilder str = new StringBuilder();

      String[] modifiers = {"private", "static"};
      Set<EVariable> variables = new HashSet<>(annotatedClass.getVariables());

      for (EVariable eVariable : variables) {
         if (eVariable.getType().equals(annotatedClass.getName())) {
            annotatedClass.removeVariable(eVariable);
         }
      }
      EVariable instance = new EVariable(modifiers, annotatedClass.getName(),
            instanceName, "null", annotatedClass);
      annotatedClass.addElement(instance, instance.getKind());

      str.append(instance.toString() + "\n");
      launch = true;
      return consoleDisplayFrame(str.toString());
   }


   private String[] methodBody() {
      List<String> str = new ArrayList<>();

      str.add(String.format("if(%s == null)", instanceName));
      str.add(String.format("   %s = new %s();", instanceName, annotatedClass.getName()));
      str.add(String.format("return %s;", instanceName));
      String[] body = str.toArray(new String[0]);
      return body;
   }

}
