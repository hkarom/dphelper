package dpHelper.generators.codeElements;

import com.sun.source.tree.ClassTree;
import com.sun.source.tree.MethodTree;
import com.sun.source.tree.Tree;
import com.sun.source.tree.VariableTree;

import java.util.*;


public class EClass extends AbstractESourceCode {

   private Set<String> interfaces;
   private String extendedClass;
   private LinkedHashMap<EClass, String> classes;
   private LinkedHashMap<EVariable, String> variables;
   private LinkedHashMap<EConstructor, String> constructors;
   private LinkedHashMap<EMethod, String> methods;


   public EClass(String[] modifiers, Tree.Kind kind, String name, ESourceCode enclosing) {
      super(kind, "", name, enclosing, modifiers);
      classes = new LinkedHashMap<>();
      variables = new LinkedHashMap<>();
      constructors = new LinkedHashMap<>();
      methods = new LinkedHashMap<>();
      initializeType();
   }

   public EClass(ClassTree classtree, ESourceCode enclosing) {
      super(classtree, "", classtree.getSimpleName().toString(), enclosing);
      name = classtree.getSimpleName().toString();
      classes = new LinkedHashMap<>();
      variables = new LinkedHashMap<>();
      constructors = new LinkedHashMap<>();
      methods = new LinkedHashMap<>();
      initializeExtendedClass(classtree);
      initializeImplementedInterfaces(classtree);
      initializeModifiers(classtree);
      initializeMembers(classtree);
      initializeType();
   }

   public String getName() {
      return name;
   }

   public void removeImplement(String interfaceToRemove) {

      interfaces.remove(interfaceToRemove);
   }

   public void removeAllImplements() {

      interfaces.clear();
   }

   public void addImplement(String interfaceToAdd) {
      interfaces.add(interfaceToAdd);
   }

   public void removeExtends() {
      extendedClass = null;
   }

   public void addExtends(String classToExtend) {
      extendedClass = classToExtend;
   }

   public void addElement(ESourceCode code, String kind) {
      switch (kind) {
         case "CLASS":
         case "INTERFACE":
         case "ENUM": {
            EClass eClass = (EClass) code;
            eClass.setSpace();
            classes.put(eClass, code.getName());
         }
         break;
         case "VARIABLE": {
            EVariable variable = (EVariable) code;
            variable.setSpace();
            variables.put(variable, variable.getName());
         }
         break;
         case "CONSTRUCTOR": {
            EConstructor constructor = (EConstructor) code;
            constructor.setSpace();
            constructors.put(constructor, code.getName());
         }
         break;
         case "METHOD": {
            EMethod method = (EMethod) code;
            method.setSpace();
            methods.put(method, code.getName());
         }
         break;
         default:
            break;
      }
   }

   public void removeConstructor(EConstructor constructor) {
      constructors.remove(constructor);
   }

   public void removeVariable(EVariable variable) {
      variables.remove(variable);
   }

   public void removeMethod(EMethod method) {
      methods.remove(method);
   }

   public void removeClass(EClass eClass) {
      classes.remove(eClass);
   }

   public List<EMethod> getMethodByName(String name) {
      List<EMethod> tmpMethods = new ArrayList<>();
      for (Map.Entry<EMethod, String> entry : methods.entrySet()) {
         if (entry.getKey().getName().equals(name))
            tmpMethods.add(entry.getKey());
      }
      return tmpMethods;
   }

   public List<EVariable> getVariableByName(String name) {
      List<EVariable> tmpVariables = new ArrayList<>();
      for (Map.Entry<EVariable, String> entry : variables.entrySet()) {
         if (entry.getKey().getName().equals(name))
            tmpVariables.add(entry.getKey());
      }
      return tmpVariables;
   }

   public List<EClass> getClassByName(String name) {
      List<EClass> tmpClasses = new ArrayList<>();
      for (Map.Entry<EClass, String> entry : classes.entrySet()) {
         if (entry.getKey().getName().equals(name))
            tmpClasses.add(entry.getKey());
      }
      return tmpClasses;
   }

   public Set<EConstructor> getConstructors() {
      return constructors.keySet();
   }

   public Set<EVariable> getVariables() {
      return variables.keySet();
   }

   public Set<EMethod> getMethods() {
      return methods.keySet();
   }

   public Set<EClass> getClasses() {
      return classes.keySet();
   }

   public String getInterfaces() {
      StringBuilder tmpInterfaces = new StringBuilder();
      for (String m : interfaces) {
         tmpInterfaces.append(m);
      }
      return tmpInterfaces.toString();
   }

   public String toString() {
      StringBuilder str = new StringBuilder(super.toString());
      str.append(type != null ? type + " " : "");
      str.append(name);

      if (extendedClass != null)
         str.append(" extends " + extendedClass);
      if (interfaces.size() > 0)
         str.append(" implements " + getInterfaces());

      if (this.isEnumeration())
         contentEnumFormatToString(str);
      else
         contentToString(str);

      return str.toString();
   }

   private void contentToString(StringBuilder str) {
      str.append(" {\n");
      mapToString(str, variables);
      mapToString(str, constructors);
      mapToString(str, methods);
      mapToString(str, classes);
      str.append(SPACE + "}\n");

   }


   private void contentEnumFormatToString(StringBuilder str) {
      str.append(" {\n");
      Set<EVariable> allVariables = variables.keySet();
      for (EVariable var : allVariables) {
         str.append(SPACE + var.getName());
         str.append(var.getValue() != null ? "(" + var.getValue() + ")" : "");
         str.append(",\n");
      }
      Set<EConstructor> allConstructors = constructors.keySet();
      if (allConstructors.size() == 1) {
         EConstructor uniqueConstructor = allConstructors.iterator().next();
         if (!uniqueConstructor.isAutoGeneratedConstructor()) {
            str.append(";");
            mapToString(str, constructors);
         }
      } else
         mapToString(str, constructors);

      mapToString(str, methods);
      mapToString(str, classes);
      str.append(SPACE + "}\n");

   }

   public boolean isEnumeration() {
      return this.getKind().equals(Tree.Kind.ENUM.toString());
   }

   private void mapToString(StringBuilder str, Map<? extends ESourceCode, String> map) {
      for (Map.Entry<? extends ESourceCode, String> entry : map.entrySet()) {
         str.append("\n" + SPACE + entry.getKey());
      }
      if (!map.isEmpty())
         str.append("\n");
   }


   private void initializeExtendedClass(ClassTree classtree) {
      if (classtree.getExtendsClause() != null)
         extendedClass = classtree.getExtendsClause().toString();
   }

   private void initializeImplementedInterfaces(ClassTree classtree) {
      interfaces = new LinkedHashSet<>();
      if (!classtree.getImplementsClause().isEmpty()) {
         for (Tree t : classtree.getImplementsClause()) {
            interfaces.add(t.toString() + " ");
         }
      }
   }

   private void initializeModifiers(ClassTree classtree) {
      modifiers = new LinkedHashSet<>();
      if (classtree.getModifiers() != null) {
         String[] tmpModifiers = classtree.getModifiers().toString().split(" ");
         modifiers.addAll(Arrays.asList(tmpModifiers));
      }
   }

   private void initializeMembers(ClassTree classTree) {
      for (Tree tree : classTree.getMembers()) {
         String kind = tree.getKind().toString();
         switch (tree.getKind()) {
            case CLASS:
            case ENUM:
            case INTERFACE: {
               EClass eClass = new EClass((ClassTree) tree, this);
               addElement(eClass, kind);
            }
            break;
            case METHOD: {
               MethodTree methodTree = (MethodTree) tree;
               if (methodTree.getName().contentEquals("<init>")) {
                  EConstructor constructor = new EConstructor(methodTree, this);
                  addElement(constructor, constructor.getKind());
               } else {
                  EMethod method = new EMethod(methodTree, this);
                  addElement(method, kind);
               }
            }
            break;
            case VARIABLE: {
               EVariable variable = new EVariable((VariableTree) tree, this);
               addElement(variable, kind);
            }
         }
      }
   }

   private void initializeType() {
      switch (kind) {
         case CLASS:
            type = "class";
            break;
         case INTERFACE:
            type = "";
            break;
         case ENUM:
            type = "enum";
            break;
         default:
            throw new UnsupportedOperationException();
      }
   }

}
