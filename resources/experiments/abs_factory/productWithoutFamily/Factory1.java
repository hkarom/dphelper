package experiments.abs_factory.productWithoutFamily;

import dpHelper.annotations.*;


public class Factory1 implements AbsFactory {

   public AbsProduct2 B() {
      return null;
   }


   public AbsProduct1 A() {
      return null;
   }

}