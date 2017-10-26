package dpHelper.generators.codeElements;

import com.sun.source.tree.ExpressionTree;
import com.sun.source.tree.NewClassTree;
import com.sun.source.tree.Tree;
import com.sun.source.tree.VariableTree;

import java.util.Arrays;
import java.util.LinkedHashSet;


public class EVariable extends AbstractESourceCode {

   protected String value;


   public EVariable(String[] modifiers, String type, String name, String value,
                    ESourceCode enclosing) {

      super(Tree.Kind.VARIABLE, type, name, enclosing, modifiers);
      this.value = value;
   }

   public EVariable(String[] modifiers, String type, String name, ESourceCode enclosing) {
      super(Tree.Kind.VARIABLE, type, name, enclosing, modifiers);
   }

   public EVariable(VariableTree variabletree, ESourceCode enclosing) {
      super(variabletree, variabletree.getType().toString(), variabletree.getName()
            .toString(), enclosing);
      ExpressionTree initializer = variabletree.getInitializer();

      if (enclosing.getKind().equals(Tree.Kind.ENUM.toString())) {
         NewClassTree tmpTree = (NewClassTree) initializer;
         value = !tmpTree.getArguments().isEmpty() ? tmpTree.getArguments().toString() :
               null;
      } else
         value = initializer != null ? initializer.toString() : null;

      initModifiers(variabletree);
   }

   public void setValue(String value) {
      this.value = value;
   }

   public String getValue() {
      return value;
   }

   public String toString() {
      StringBuilder str = new StringBuilder();

         str.append(super.toString());
         str.append(String.format("%s ", type));
         str.append(name);
         str.append(value != null ? String.format(" = %s", value) : "");
         str.append(";");

      return str.toString();
   }

   private void initModifiers(VariableTree variabletree) {
      modifiers = new LinkedHashSet<>();
      if (variabletree.getModifiers() != null) {
         String[] tmpModifiers = variabletree.getModifiers().toString().split(" ");
         modifiers.addAll(Arrays.asList(tmpModifiers));
      }
   }


}
