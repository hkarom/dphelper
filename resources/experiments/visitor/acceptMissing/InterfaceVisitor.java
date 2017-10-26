package experiments.visitor.acceptMissing;

import dpHelper.annotations.Visitor;


@Visitor
public interface InterfaceVisitor {
   void visit(Visitable1 visitable);


}