package utours.ultimate.core.steorotype;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface Mapping {
    
    Class<?> clazz();

    Type type() default Type.Unique;

    enum Type {
        Unique,
        Additional
    }

}