package experiments.abs_factory.productWithoutFamily;

import dpHelper.annotations.*;


public class Product2 implements AbsProduct2 {

}


class Product2bis extends Product2 {

}

@AbstractFactory.Product(AbsFactory.class)
abstract class Product2bis2 extends Product2bis {

}

