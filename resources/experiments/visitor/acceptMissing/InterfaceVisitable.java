package experiments.visitor.acceptMissing;

import dpHelper.annotations.Visitor;


@Visitor.Visitable(InterfaceVisitor.class)
public interface InterfaceVisitable {

   void accept(InterfaceVisitor v);
}