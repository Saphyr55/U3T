package utours.ultimate.net.common;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.FileSystems;

import static org.junit.jupiter.api.Assertions.*;

class ClassPathResourceTest {

    static String PROPERTIES_FILENAME = "test.properties";

    @Test
    void readAllLines() throws IOException {

        assertDoesNotThrow(() -> ClassPathResource.readAllLines(PROPERTIES_FILENAME));

        String content = ClassPathResource.readAllLines(PROPERTIES_FILENAME);

        assertEquals("server.port=6666" +
                FileSystems.getDefault().getSeparator() +
                "server.host-address=192.178.0.1", content);

    }

}