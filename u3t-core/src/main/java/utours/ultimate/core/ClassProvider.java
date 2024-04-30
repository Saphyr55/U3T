package utours.ultimate.core;

import utours.ultimate.core.internal.ClassProviderInternal;

import java.util.Set;

public class ClassProvider {

    /**
     *
     * @param packageName
     * @return
     */
    public static Set<Class<?>> classesOf(String packageName) {
        return ClassProviderInternal.classesOf(packageName);
    }

    /**
     *
     * @param className
     * @param packageName
     * @return
     */
    public static Class<?> classOf(String className, String packageName) {
        return ClassProviderInternal.classOf(className, packageName);
    }


}
