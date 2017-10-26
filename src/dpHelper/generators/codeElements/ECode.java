package dpHelper.generators.codeElements;

import com.sun.source.tree.*;
import dpHelper.generators.treesVisitor.DocCommentVisitor;

import javax.lang.model.element.Modifier;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


public class ECode implements ESourceCode {

   public EClass MAIN_CLASS;
   private String packageName;
   private List<EImport> imports;
   private Set<EClass> classes;
   private static CompilationUnitTree unitTree;
   private EDocComment commentElement;


   public ECode(String packageName) {
      this.packageName = packageName;
      imports = new ArrayList<>();
      classes = new HashSet<>();
      commentElement = new EDocComment();
   }

   public ECode(CompilationUnitTree originTree) {
      packageName = initPackage(originTree);
      imports = initImports(originTree);
      unitTree = originTree;
      DocCommentVisitor visitor = new DocCommentVisitor();
      visitor.scan(originTree,null);
      commentElement = new EDocComment(visitor.getDocComment());
      classes = new HashSet<>();
      initClass(originTree);
      unitTree = null;
   }

   public String getImports() {
      StringBuilder str = new StringBuilder();
      for (EImport imp : imports) {
         str.append(imp.toString());
      }
      return str.toString();
   }

   public static CompilationUnitTree getUnitTree() {
      return unitTree;
   }

   public String getPackageName() {
      return packageName;
   }

   public String getPackage() {
      return "package " + packageName + ";";
   }

   public void setPackage(String packageName) {
      this.packageName = packageName;
   }

   public void addImport(EImport newImport) {
      imports.add(newImport);
   }

   public void setMainClass(EClass classElement) {
      MAIN_CLASS = classElement;
      addEClass(classElement);
   }

   public void addEClass(EClass classElement) {
      if (MAIN_CLASS != null)
         classes.add(classElement);
      else throw new UnsupportedOperationException("Main class need to be set first");
   }

   public String toString() {
      StringBuilder str = new StringBuilder();
      str.append(String.format("%s %s \n\n %s ", commentElement, getPackage(),
            getImports()));
      for (EClass eClass : classes) {
         str.append(String.format("\n%s", eClass.toString()));
      }
      return str.toString();
   }

   @Override
   public String getKind() {
      return Tree.Kind.COMPILATION_UNIT.toString();
   }

   @Override
   public String getName() {
      return MAIN_CLASS.getName();
   }

   public EClass getClassByName(String className) {
      for (EClass eClass : classes) {
         if (eClass.getName().equals(className))
            return eClass;
      }
      return null;
   }

   @Override
   public void setDocComment(EDocComment comment) {
      commentElement = comment;
   }

   private List<EImport> initImports(CompilationUnitTree origin) {
      List<EImport> tmpImports = new ArrayList<>();
      for (ImportTree imp : origin.getImports()) {
         tmpImports.add(new EImport(imp));
      }
      return tmpImports;
   }

   private String initPackage(CompilationUnitTree originTree) {
      ExpressionTree pack = originTree.getPackageName();
      String packageFullName = pack != null ? pack.toString() : "";
      return packageFullName;
   }

   private void initClass(CompilationUnitTree tree) {
      for (Tree t : tree.getTypeDecls()) {
         ClassTree classTree = (ClassTree) t;
         if (classTree.getModifiers().getFlags().contains(Modifier.PUBLIC))
            MAIN_CLASS = new EClass(classTree, this);
         classes.add(new EClass(classTree, this));
      }
   }

}
