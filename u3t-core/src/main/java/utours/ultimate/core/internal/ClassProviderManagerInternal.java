package utours.ultimate.core.internal;

import utours.ultimate.common.JarLoader;
import utours.ultimate.core.ModuleContext;

import java.io.*;
import java.net.*;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;
import java.security.cert.Certificate;
import java.util.*;
import java.util.jar.Attributes;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.jar.Manifest;
import java.util.stream.Collectors;

public class ClassProviderManagerInternal {

    public static final int MAX_DEPTH = 999;

    public static String denormalizePackageName(String packageName) {
        return packageName.replaceAll("[.]", "/");
    }

    public static Class<?> classOf(String className, String packageName) {
        try {
            return Class.forName(packageName + "." + className.substring(0, className.lastIndexOf('.')));
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }


    public static Set<Class<?>> classesOf(Class<?> contextClass) {
        try {

            URL moduleURL = contextClass.getResource("");
            assert moduleURL != null;
            URI moduleURI = moduleURL.toURI();

            Set<URL> urlClasses;
            Set<String> classNames;

            if (!moduleURL.getProtocol().equals("jar")) {

                Path moduleContextPath = Paths.get(moduleURI);
                List<Path> urlClassesFile = Files.find(moduleContextPath, MAX_DEPTH,
                        ClassProviderManagerInternal::isClassFile).toList();

                urlClasses = urlClassesFile.stream().map(path -> {
                    try {
                        return path.toFile().toURI().toURL();
                    } catch (MalformedURLException e) {
                        throw new RuntimeException(e);
                    }
                }).collect(Collectors.toSet());

                Path modulePath = Path.of(moduleURI);
                String packageName = contextClass.getPackageName();
                classNames = urlClassesFile.stream()
                        .map(modulePath::relativize)
                        .map(path -> String.format("%s.%s", packageName, toClassName(path)))
                        .collect(Collectors.toSet());

            } else {
                JarURLConnection connection = (JarURLConnection) moduleURL.openConnection();
                JarFile jarFile = connection.getJarFile();
                urlClasses = Set.of(moduleURL);
                classNames = JarLoader.classNamesOfJarFile(jarFile);
            }

            try (URLClassLoader classLoader = URLClassLoader.newInstance(urlClasses.toArray(URL[]::new))) {
                return classNames.stream().map(name -> {
                    try {
                        return classLoader.loadClass(name);
                    } catch (ClassNotFoundException e) {
                        throw new RuntimeException(e);
                    }
                }).collect(Collectors.toSet());
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }


    }

    private static boolean isClassFile(Path p, BasicFileAttributes bfa) {
        return bfa.isRegularFile() && p.toFile().getPath().endsWith(".class");
    }

    private static String toClassName(Path path) {

        String file = path.toString();
        String fileNoExt = file.replaceFirst("[.][^.]+$", "");
        return fileNoExt.replace(FileSystems.getDefault().getSeparator(), ".");
    }

}
