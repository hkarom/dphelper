package dpHelper.generators.codeElements;


public interface ESourceCode {

   String toString();

   String getKind();

   String getName();


   void setDocComment(EDocComment comment);


}
