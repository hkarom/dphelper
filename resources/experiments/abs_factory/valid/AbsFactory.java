package experiments.abs_factory.valid;

import dpHelper.annotations.*;

@AbstractFactory
public interface AbsFactory {

   AbsProduct1 A();

   AbsProduct2 B();

}