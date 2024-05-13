package utours.ultimate.core.internal;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Set;
import java.util.stream.Collectors;

public class ClassProviderInternal {

    public static String denormalizePackageName(String packageName) {
        return packageName.replaceAll("[.]", "/");
    }

    public static Set<Class<?>> classesOf(String packageName) {

        String packagePath = denormalizePackageName(packageName);

        InputStream stream = ClassLoader.getSystemClassLoader().getResourceAsStream(packagePath);

        assert stream != null;

        BufferedReader reader = new BufferedReader(new InputStreamReader(stream));

        return reader.lines()
                .filter(line -> line.endsWith(".class"))
                .map(line -> classOf(line, packageName))
                .collect(Collectors.toSet());
    }

    public static Class<?> classOf(String className, String packageName) {
        try {
            return Class.forName(packageName + "." + className.substring(0, className.lastIndexOf('.')));
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

}
