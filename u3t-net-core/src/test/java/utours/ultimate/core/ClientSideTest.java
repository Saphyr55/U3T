package utours.ultimate.core;

import org.junit.jupiter.api.*;
import utours.ultimate.core.base.NetServerApplication;
import utours.ultimate.core.base.NetServerApplicationTest;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;


public class ClientSideTest extends NetServerApplicationTest {

    @Test
    void check_multiple_client() {
        for (int i = 0; i < 10; i++) {
            Thread.ofPlatform().start(this::check_if_server_respond_correctly);
        }
    }

    @Test
    void check_if_port_and_address_are_equal() {
        Client client = Client.of(HOST_ADDRESS, PORT);

        assertEquals(HOST_ADDRESS, client.hostAddress());
        assertEquals(PORT, client.port());

        client.close();
    }

    @Test
    void check_if_server_respond_correctly() {

        Client client = Client.of(HOST_ADDRESS, PORT);

        String msg1 = client.sendMessage("an.address", "hello", String.class);
        String msg2 = client.sendMessage("an.address","world", String.class);

        client.close();

        assertEquals("hello", msg1);
        assertEquals("world", msg2);
    }


}
