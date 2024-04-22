package utours.ultimate.core;

import java.lang.annotation.Annotation;

public interface OnAnnotationAnalyze<T extends Annotation> {

    void setup(T annotation);

}

