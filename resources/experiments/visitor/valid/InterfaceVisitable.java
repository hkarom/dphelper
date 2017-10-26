package experiments.visitor.valid;

import dpHelper.annotations.Visitor;


@Visitor.Visitable(InterfaceVisitor.class)
public interface InterfaceVisitable {

   void accept(InterfaceVisitor v);
}