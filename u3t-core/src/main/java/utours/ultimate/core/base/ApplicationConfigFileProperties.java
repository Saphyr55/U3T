package utours.ultimate.core.base;

import utours.ultimate.core.ApplicationConfiguration;
import utours.ultimate.core.common.ClassPathResource;

import java.io.*;
import java.net.URL;
import java.util.stream.Collectors;

public class ApplicationConfigFileProperties implements ApplicationConfiguration {

    public static final String PROPERTIES_FILENAME = "application.properties";

    private final String content;

    public ApplicationConfigFileProperties() {
        this.content = readPropertiesFile();
    }

    @Override
    public String address() {
        return "";
    }

    @Override
    public int port() {
        return 0;
    }

    private String readPropertiesFile() {
        try {
            return ClassPathResource.readAllLines(PROPERTIES_FILENAME);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
