package utours.ultimate.core.internal;

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

    public static Set<Class<?>> classesOf(ClassLoader classLoader, String packageName) {

        String packagePath = denormalizePackageName(packageName);

        InputStream stream = classLoader.getResourceAsStream(packagePath);

        assert stream != null;

        BufferedReader reader = new BufferedReader(new InputStreamReader(stream));

        return reader.lines()
                .filter(line -> line.endsWith(".class"))
                .map(line -> {
                    var clazz = classOf(line, packageName);
                    return clazz;
                })
                .collect(Collectors.toSet());
    }

    public static Class<?> classOf(String className, String packageName) {
        try {
            return Class.forName(packageName + "." + className.substring(0, className.lastIndexOf('.')));
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public static Set<Class<?>> classesOf(ModuleContext context) {
        try {

            URL moduleURL = context.getContextClass().getResource("");
            assert moduleURL != null;
            URI moduleURI = moduleURL.toURI();

            Set<URL> urlClasses;
            Set<String> classNames;

            if (!moduleURL.getProtocol().equals("jar")) {
                Path moduleContextPath = Paths.get(moduleURI);
                var urlClassesFile = Files.find(moduleContextPath, MAX_DEPTH, ClassProviderManagerInternal::isClassFile).toList();

                urlClasses = urlClassesFile.stream().map(path -> {
                    try {
                        return path.toFile().toURI().toURL();
                    } catch (MalformedURLException e) {
                        throw new RuntimeException(e);
                    }
                }).collect(Collectors.toSet());

                Path modulePath = Path.of(moduleURI);
                String packageName = context.getContextClass().getPackageName();
                classNames = urlClassesFile.stream()
                        .map(modulePath::relativize)
                        .map(path -> String.format("%s.%s", packageName, toClassName(path)))
                        .collect(Collectors.toSet());

            } else {

                JarURLConnection connection = (JarURLConnection) moduleURL.openConnection();
                JarFile jarFile = connection.getJarFile();

                urlClasses = Set.of(moduleURL);
                Set<String> cns = new HashSet<>();

                Enumeration<JarEntry> e = jarFile.entries();
                while (e.hasMoreElements()) {
                    JarEntry jarEntry = e.nextElement();
                    if (jarEntry.getName().endsWith(".class")) {
                        String className = jarEntry.getName()
                                .replace("/", ".")
                                .replace(".class", "");
                        cns.add(className);
                    }
                }

                classNames = cns;
            }

            try (URLClassLoader classLoader = URLClassLoader.newInstance(urlClasses.toArray(new URL[]{ }))) {
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

    private static void logDetails(JarURLConnection connection) throws Exception {
        // getJarFileURL() method
        System.out.println("Jar file URL : " + connection.getJarFileURL());

        // getEntryName() method
        System.out.println("Entry Name : " + connection.getEntryName());

        // getJarFile() method
        JarFile jarFile = connection.getJarFile();
        System.out.println("Jar Entry: " + connection.getJarEntry());

        // getManifest() method
        Manifest manifest = jarFile.getManifest();
        System.out.println("Manifest :" + manifest.toString());

        // getJarEntry() method
        JarEntry jentry = connection.getJarEntry();
        System.out.println("Jar Entry : " + jentry.toString());

        // getAttributes() method
        Attributes attr = connection.getAttributes();
        System.out.println("Attributes : " + attr);

        // getMainAttributes() method
        Attributes attrmain = connection.getMainAttributes();

        System.out.println("\nMain Attributes details: " + Arrays.toString(
                attrmain.entrySet().stream()
                    .map(e -> e.getKey() + " " + e.getValue()).toArray()));

        // getCertificates() method
        Certificate[] cert = connection.getCertificates();
        System.out.println("\nCertificates Info : " + Arrays.toString(cert));
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
