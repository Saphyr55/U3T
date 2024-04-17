package utours.ultimate.core;

import utours.ultimate.core.internal.ClassPathResourceInternal;

import java.io.IOException;
import java.io.InputStream;


public class ClassPathResource {

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
