package dpHelper.generators.codeElements;

import com.sun.source.tree.Tree;
import com.sun.source.util.TreePath;
import dpHelper.generators.treesVisitor.DocCommentVisitor;

import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.Set;

import static dpHelper.tools.ProcessorTools.TREES;
import static dpHelper.tools.ProcessorTools.repeatChar;

public abstract class AbstractESourceCode implements ESourceCode {

   protected EDocComment commentElement;
   protected Set<String> modifiers;
   protected Tree.Kind kind;
   protected String type;
   protected String name;
   protected ESourceCode enclosing;
   private int currentSpace = 0;
   protected String SPACE = repeatChar(' ', currentSpace);


   AbstractESourceCode(Tree.Kind kind, String type, String name, ESourceCode
         enclosingElement, String... modifiers) {
      this.kind = kind;
      this.type = type;
      this.name = name;
      this.modifiers = new LinkedHashSet<>(Arrays.asList(modifiers));
      this.commentElement = new EDocComment();
      enclosing = enclosingElement;
   }

   AbstractESourceCode(Tree tree, String nodeType, String nodeName, ESourceCode
         enclosingElement) {
      kind = tree.getKind();
      type = nodeType;
      name = nodeName;
      enclosing = enclosingElement;
      getDocComment(tree);
   }

   public String getModifiers() {
      StringBuilder tmpModifiers = new StringBuilder();
      for (String m : modifiers) {
         tmpModifiers.append(m + " ");
      }
      return tmpModifiers.toString();
   }

   public void getDocComment(Tree tree) {
      DocCommentVisitor visitor = new DocCommentVisitor();
      TreePath path = TREES.getPath(ECode.getUnitTree(),tree);
      visitor.scan(path,null);
      commentElement = new EDocComment(visitor.getDocComment());
   }

   public void addModifier(String... modifiersToAdd) {
      modifiers.addAll(Arrays.asList(modifiersToAdd));
   }

   public void setType(String type) {
      this.type = type;
   }

   public String getType() {
      return type;
   }

   public void removeModifier(String modifierToRemove) {
      modifiers.remove(modifierToRemove);
   }

   protected void setSpace() {
      int indent = 2;
      this.currentSpace += indent;
      this.SPACE = repeatChar(' ',this.currentSpace);
   }


   public void clearModifier() {
      modifiers.clear();
   }

   public String getName() {
      return name;
   }

   public void setName(String name) {
      this.name = name;
   }

   public String getKind() {
      return kind.toString();
   }

   public void setDocComment(EDocComment comment) {
      commentElement = comment;
   }

   public String toString() {
      return SPACE + commentElement +SPACE+ getModifiers();
   }

}
