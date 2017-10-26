package dpHelper.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(value = {ElementType.TYPE})
@Retention(value = RetentionPolicy.SOURCE)
public @interface Singleton {

    String value() default "getInstance";

    @interface Synchronized {

        String value() default "getInstance";
    }

}
