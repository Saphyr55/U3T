package utours.ultimate.core;

import utours.ultimate.core.internal.ClassProviderManagerInternal;

import java.util.Set;

public class ClassProviderManager {

    /**
     *
     * @param packageName
     * @return
     */
    public static String denormalizePackageName(String packageName) {
        return ClassProviderManagerInternal.denormalizePackageName(packageName);
    }

    /**
     *
     * @param packageName
     * @return
     */
    public static Set<Class<?>> classesOf(String packageName) {
        return ClassProviderManagerInternal.classesOf(packageName);
    }

    /**
     *
     * @param className
     * @param packageName
     * @return
     */
    public static Class<?> classOf(String className, String packageName) {
        return ClassProviderManagerInternal.classOf(className, packageName);
    }


}
