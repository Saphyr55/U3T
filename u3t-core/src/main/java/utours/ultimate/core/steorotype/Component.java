package utours.ultimate.core.steorotype;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface Component {

    String name() default "";

    Strategy strategy() default Strategy.Implementation;

    Type type() default Type.Additional;

    enum Strategy {
        Interface,
        Implementation
    }

    enum Type {
        Additional,
        Maybe,
        Unique
    }
}
