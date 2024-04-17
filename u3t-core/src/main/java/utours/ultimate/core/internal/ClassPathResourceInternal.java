package utours.ultimate.core.internal;

import utours.ultimate.core.ClassPathResource;

import java.io.*;
import java.nio.file.FileSystems;
import java.util.stream.Collectors;

public class ClassPathResourceInternal {


    public static InputStream getResourceAsStream(String filepath) throws IOException {
        var classLoader = Thread.currentThread().getContextClassLoader();
        var resource = classLoader.getResource(filepath);
        if (resource == null) {
            throw new FileNotFoundException(filepath);
        }
        return new FileInputStream(resource.getPath());
    }

    public static String readAllLines(String path) throws IOException {

        var applicationPropertiesInput = ClassPathResource.class
                .getClassLoader()
                .getResourceAsStream(path);

        if (applicationPropertiesInput == null) {
            throw new FileNotFoundException(path);
        }

        var reader = new InputStreamReader(applicationPropertiesInput);
        var buffer = new BufferedReader(reader);
        var content = buffer.lines().collect(Collectors.joining(FileSystems.getDefault().getSeparator()));

        applicationPropertiesInput.close();

        return content;
    }


}