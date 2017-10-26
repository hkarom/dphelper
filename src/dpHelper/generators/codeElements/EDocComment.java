package dpHelper.generators.codeElements;

import com.sun.source.doctree.DocCommentTree;
import com.sun.source.doctree.DocTree;

import java.util.ArrayList;
import java.util.List;


public class EDocComment implements ESourceCode {

   private List<String> docTreeCode;

   public EDocComment(DocCommentTree docCommentTree) {
      if (docCommentTree != null)
         replaceCode(docCommentTree.toString());
      else
         docTreeCode = new ArrayList<>();
   }

   public EDocComment(List<String> docComment) {
      docTreeCode = docComment;
   }

   public EDocComment() {
      docTreeCode = new ArrayList<>();
   }

   public String getKind() {
      return DocTree.Kind.DOC_COMMENT.toString();
   }

   @Override
   public String getName() {
      return "Doc_comment";
   }

   @Override
   public void setDocComment(EDocComment comment) {
      docTreeCode = comment.docTreeCode;
   }

   public String toString() {

      StringBuilder str = new StringBuilder("");
      for (String s : docTreeCode) {
         str.append(s);
      }
      if (docTreeCode.size() > 0) str.append(" */\n");
      return str.toString();
   }

   public void addSentence(String sentence) {
      docTreeCode.add(" *" + sentence);
   }

   public void replaceCode(String newCode) {
      docTreeCode = new ArrayList<>();
      docTreeCode.add("/**\n");
      String[] code = newCode.toString().split("\n");
      for (int i = 0; i < code.length; i++) {
         docTreeCode.add(" *" + code[i] + "\n");
      }
   }
}
