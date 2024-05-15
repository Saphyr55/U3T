package utours.ultimate.core.steorotype;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface Mapping {

    Class<?> clazz() default Class.class;

    Type type() default Type.Unique;

    boolean activate() default true;

    enum Type {
        Unique,
        Additional
    }

}
