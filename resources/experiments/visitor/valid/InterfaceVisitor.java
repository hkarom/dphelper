package experiments.visitor.valid;

import dpHelper.annotations.Visitor;


@Visitor
public interface InterfaceVisitor {
   void visit(Visitable1 visitable);


}