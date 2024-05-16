package utours.ultimate.core;

import utours.ultimate.core.internal.ClassProviderManagerInternal;

import java.lang.reflect.Constructor;
import java.util.Optional;
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
     * @param clazz
     * @return
     */
    public static boolean hasInterfaces(Class<?> clazz) {
        return clazz.getInterfaces().length > 0;
    }

    /**
     *
     * @param clazz
     * @return
     */
    public static Optional<Class<?>> getFirstInterfaceImplemented(Class<?> clazz) {
        if (hasInterfaces(clazz)) {
            return Optional.of(clazz.getInterfaces()[0]);
        }
        return Optional.empty();
    }

    /**
     *
     * @param clazz
     * @return
     */
    public static Optional<Constructor<?>> getFirstDeclaredConstructor(Class<?> clazz) {
        if (clazz.getDeclaredConstructors().length < 1) return Optional.empty();
        return Optional.of(clazz.getDeclaredConstructors()[0]);
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
