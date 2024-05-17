package utours.ultimate.net.internal;

import utours.ultimate.core.ClassPathResource;
import utours.ultimate.net.NetApplicationConfiguration;

import java.io.*;
import java.util.Properties;

public class NetApplicationConfigFileProperties implements NetApplicationConfiguration {

    public static final String KEY_SERVER_HOST_ADDRESS = "server.host-address";
    public static final String VALUE_SERVER_HOST_ADDRESS = "127.0.0.1";

    public static final String KEY_SERVER_PORT = "server.port";
    public static final int VALUE_SERVER_PORT = 6667;

    public static final String PROPERTIES_FILENAME = "application.properties";

    private final Properties properties;
    private final String address;
    private final int port;

    public NetApplicationConfigFileProperties(String filepath) {
        this.properties = getAndLoadProperties(filepath);
        this.address = (String) properties.getOrDefault(KEY_SERVER_HOST_ADDRESS, VALUE_SERVER_HOST_ADDRESS);
        this.port = Integer.parseInt((String) properties.getOrDefault(KEY_SERVER_PORT, VALUE_SERVER_PORT));
    }

    public NetApplicationConfigFileProperties() {
        this(PROPERTIES_FILENAME);
    }

    @Override
    public String address() {
        return address;
    }

    @Override
    public int port() {
        return port;
    }

    private Properties getAndLoadProperties(String filepath) {
        try {
            var properties = new Properties();
            properties.load(ClassPathResource.getResourceAsStream(filepath));
            return properties;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
