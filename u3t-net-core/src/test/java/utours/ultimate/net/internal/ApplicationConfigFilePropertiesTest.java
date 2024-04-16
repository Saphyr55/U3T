package utours.ultimate.net.internal;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ApplicationConfigFilePropertiesTest {


    String FILEPATH = "test.properties";

    ApplicationConfigFileProperties appConfigFileProperties;

    @BeforeEach
    void setUp() {
        appConfigFileProperties = new ApplicationConfigFileProperties(FILEPATH);
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void address() {
        assertEquals("192.178.0.1", appConfigFileProperties.address());
    }

    @Test
    void port() {
        assertEquals(6666, appConfigFileProperties.port());
    }

}