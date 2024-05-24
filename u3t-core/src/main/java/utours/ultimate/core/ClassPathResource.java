package utours.ultimate.core;

import utours.ultimate.core.internal.ClassPathResourceInternal;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;


public final class ClassPathResource {

    /**
     * Get url resource from the class path.
     *
     * @param path path
     * @return url
     */
    public static URL getResource(String path){
        return ClassPathResourceInternal.getResource(path);
    }

    /**
     * Get an input stream from the class path.
     *
     * @param filepath path
     * @return url
     * @throws IOException IO exception.
     */
    public static InputStream getResourceAsStream(String filepath) {
        return ClassPathResourceInternal.getResourceAsStream(filepath);
    }

    /**
     * Get a file content from the class path.
     *
     * @param path path
     * @return url
     * @throws IOException IO exception.
     */
    public static String readAllLines(String path) throws IOException {
        return ClassPathResourceInternal.readAllLines(path);

    }

}
