package dpHelper.annotations;

import java.lang.annotation.*;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface AbstractFactory {


  @interface Product {
    Class<?>[] value();
  }

  @interface Factory {
    Class<?> value();
  }

}
