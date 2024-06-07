package utours.ultimate.common;

import java.io.File;
import java.io.IOException;
import java.net.JarURLConnection;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.util.*;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.logging.Level;
import java.util.logging.Logger;

public final class JarLoader {

    private static final Logger LOGGER = Logger.getLogger(JarLoader.class.getName());

    /**
     *
     * @param file
     * @return
     */
    public static Optional<URL> urlOfFile(File file) {
        try {
            return Optional.of(file.toURI().toURL());
        } catch (MalformedURLException e) {
            LOGGER.log(Level.WARNING, "Cannot convert file %s to an url"
                    .formatted(file.getName()), e);
            return Optional.empty();
        }
    }

    /**
     *
     * @param url
     * @return
     */
    public static Optional<JarFile> jarFileOf(URL url) {
        try {
            if (!url.getProtocol().equals("jar")) {
                url = URI.create("jar:" + url + "!/").toURL();
            }
            JarURLConnection connection = (JarURLConnection) url.openConnection();
            return Optional.of(connection.getJarFile());
        } catch (IOException e) {
            LOGGER.log(Level.WARNING, "Cannot get the jar file from the url: '%s'"
                    .formatted(url.toString()), e);
            return Optional.empty();
        }
    }

    public static Set<String> classNamesOfJarFile(JarFile jarFile) {

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

        return cns;
    }

}
