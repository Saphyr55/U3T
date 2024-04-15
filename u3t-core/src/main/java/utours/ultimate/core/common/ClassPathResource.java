package utours.ultimate.core.common;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.stream.Collectors;

public class ClassPathResource {

    public static String readAllLines(String fileName) throws IOException {

        var applicationPropertiesInput = ClassPathResource.class
                .getClassLoader()
                .getResourceAsStream(fileName);

        if (applicationPropertiesInput == null) {
            throw new FileNotFoundException(fileName);
        }

        var reader = new InputStreamReader(applicationPropertiesInput);
        var buffer = new BufferedReader(reader);
        var content = buffer.lines().collect(Collectors.joining());

        applicationPropertiesInput.close();
        return content;
    }

}
