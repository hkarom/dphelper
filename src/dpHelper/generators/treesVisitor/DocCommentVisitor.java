package dpHelper.generators.treesVisitor;

import com.sun.source.doctree.DocCommentTree;
import com.sun.source.tree.ClassTree;
import com.sun.source.tree.CompilationUnitTree;
import com.sun.source.tree.MethodTree;
import com.sun.source.tree.VariableTree;
import com.sun.source.util.TreePathScanner;

import static dpHelper.tools.ProcessorTools.DOCTREES;


public class DocCommentVisitor extends TreePathScanner<DocCommentTree, Void> {


   private DocCommentTree doc;

   public DocCommentTree getDocComment() {
      return doc;
   }

   @Override
   public DocCommentTree visitCompilationUnit(CompilationUnitTree node, Void aVoid) {
      doc = lookForDocComments();
      return doc;
   }

   @Override
   public DocCommentTree visitMethod(MethodTree node, Void aVoid) {
      doc =  lookForDocComments();
      return doc;
   }

   @Override
   public DocCommentTree visitClass(ClassTree node, Void aVoid) {
      doc = lookForDocComments();
      return doc;
   }

   @Override
   public DocCommentTree visitVariable(VariableTree node, Void aVoid) {
      doc = lookForDocComments();
      return doc;
   }

   private DocCommentTree lookForDocComments() {
      DocCommentTree d = DOCTREES.getDocCommentTree(this.getCurrentPath());
      return d;
   }
}
