package dpHelper.generators.codeElements;

import com.sun.source.tree.ImportTree;
import com.sun.source.tree.Tree;


public class EImport implements ESourceCode {

  private String importName;
  private EDocComment commentElement;

  public EImport(String imp) {
    importName = imp;
    commentElement = new EDocComment();
  }

  public EImport(ImportTree imp) {
    importName = imp.toString();
    commentElement = new EDocComment();
  }

  public String toString() {
    return commentElement + importName;
  }

  @Override
  public String getKind() {
    return Tree.Kind.IMPORT.toString();
  }

  @Override
  public String getName() {
    return importName;
  }

  @Override
  public void setDocComment(EDocComment comment) {
    commentElement = comment;
  }
}
