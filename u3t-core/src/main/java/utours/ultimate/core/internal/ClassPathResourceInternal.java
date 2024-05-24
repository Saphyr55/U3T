package utours.ultimate.core.internal;

import java.io.*;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.FileSystems;
import java.nio.file.Paths;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class ClassPathResourceInternal {

    public static InputStream getResourceAsStream(String filepath) {
        try {
            File file = new File(Objects.requireNonNull(ClassPathResourceInternal.class.getResource(filepath)).toURI());

            System.out.println("-------- " +  file + " ----------");

            return new FileInputStream(file);
        } catch (URISyntaxException | FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public static String readAllLines(String path) throws IOException {

        var applicationPropertiesInput = getResourceAsStream(path);

        var reader = new InputStreamReader(applicationPropertiesInput);
        var buffer = new BufferedReader(reader);
        var content = buffer.lines().collect(Collectors.joining(FileSystems.getDefault().getSeparator()));

        applicationPropertiesInput.close();

        return content;
    }

    public static URL getResource(String path) {
        return ClassPathResourceInternal.class.getClassLoader().getResource(path);
    }

}
