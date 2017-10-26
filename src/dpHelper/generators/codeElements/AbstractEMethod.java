package dpHelper.generators.codeElements;

import com.sun.source.tree.BlockTree;
import com.sun.source.tree.MethodTree;
import com.sun.source.tree.Tree;
import com.sun.source.tree.VariableTree;

import java.util.*;


public abstract class AbstractEMethod extends AbstractESourceCode {

   private List<String> body;
   protected LinkedHashMap<String, String> parameters;


    AbstractEMethod(String[] modifiers, String name, String type,
                          Map<String, String> param, String body[], ESourceCode
                          enclosing) {
      super(Tree.Kind.METHOD,type,name,enclosing,modifiers);
      this.parameters = new LinkedHashMap<>(param);
      this.body = new ArrayList<>(Arrays.asList(body));
   }

    AbstractEMethod(MethodTree methodTree,String type, ESourceCode enclosing) {
      super(methodTree,type, methodTree.getName().toString(),enclosing);
      initModifiers(methodTree);
      initParameters(methodTree);
      BlockTree block = methodTree.getBody();
      if (block != null) initBody(block);
   }


   public void clearBody() {body.clear();}

   public void addStatement(String statement) {
      this.body.add(statement);
   }

   public List<String> getBody() {
      return body;
   }

   public void setBody(String[] body) {
       this.body.clear();
       this.body.addAll(Arrays.asList(body));
   }


   public void removeParameter(String parameterName) {
      for (Map.Entry<String, String> param : parameters.entrySet()) {
         if (param.getKey().equals(parameterName)) {
            parameters.remove(param);
            break;
         }
      }
   }

   public void addParameter(String fullParameter, String paramName) {

      parameters.put(paramName, fullParameter);
   }

   public String getParameters() {
      StringBuilder tmpParam = new StringBuilder();
      int i = 0;
      for (Map.Entry<String, String> param : parameters.entrySet()) {
         if (i == parameters.size() - 1)
            tmpParam.append(String.format("%s", param.getValue()));
         else
            tmpParam.append(String.format("%s,", param.getValue()));
         i++;
      }
      return tmpParam.toString();
   }

   public String toString() {
      StringBuilder str = new StringBuilder(super.toString());
      str.append(String.format("%s %s(%s)",type,name,getParameters()));
      if(body != null)
         str.append(String.format(" {\n %s %s}",body(),SPACE+SPACE));
      else str.append(";");
      return str.toString();
   }


   private void initModifiers(MethodTree methodtree) {
      modifiers = new LinkedHashSet<>();
      if (methodtree.getModifiers() != null) {
         String[] tmpModifiers = methodtree.getModifiers().toString().split(" ");
         modifiers.addAll(Arrays.asList(tmpModifiers));
      }
   }

   private void initParameters(MethodTree methodTree) {
      parameters = new LinkedHashMap<>();
      for (VariableTree v : methodTree.getParameters()) {
         String paramName = v.getName().toString();
         parameters.put(paramName, v.toString());
      }
   }

   private void initBody(BlockTree block) {
       body = new ArrayList<>();
      String [] strBody = block.toString().split("\n");
      strBody = Arrays.copyOfRange(strBody,1,strBody.length-1);
      body.addAll(Arrays.asList(strBody));
   }

   private String body() {
       StringBuilder str = new StringBuilder();

       for(String statement : body) {
          str.append((SPACE+SPACE+SPACE)+statement+"\n");
       }
       return str.toString();
   }

}
