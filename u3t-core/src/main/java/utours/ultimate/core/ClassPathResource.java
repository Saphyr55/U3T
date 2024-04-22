package utours.ultimate.core;

import utours.ultimate.core.internal.ClassPathResourceInternal;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;


public class ClassPathResource {

    /**
     *
     * @param path
     * @return
     * @throws IOException
     */
    public static URL getResource(String path) throws IOException {
        return ClassPathResourceInternal.getResource(path);
    }

    /**
     *
     * @param filepath
     * @return
     * @throws IOException
     */
    public static InputStream getResourceAsStream(String filepath) throws IOException {
        return ClassPathResourceInternal.getResourceAsStream(filepath);
    }

    /**
     *
     * @param path
     * @return
     * @throws IOException
     */
    public static String readAllLines(String path) throws IOException {
        return ClassPathResourceInternal.readAllLines(path);

    }

}
