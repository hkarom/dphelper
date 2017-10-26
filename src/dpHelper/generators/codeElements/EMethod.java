package dpHelper.generators.codeElements;

import com.sun.source.tree.MethodTree;

import java.util.Map;


public class EMethod extends AbstractEMethod {

   public EMethod(String[] modifiers, String name, String type, Map<String, String>
         param, String[] body, ESourceCode enclosing) {

      super(modifiers, name, type, param, body, enclosing);
   }

   public EMethod(MethodTree methodTree, ESourceCode enclosing) {
      super(methodTree, methodTree.getReturnType().toString(), enclosing);
   }

   public void setName(String name) {
      this.name = name;
   }

}
