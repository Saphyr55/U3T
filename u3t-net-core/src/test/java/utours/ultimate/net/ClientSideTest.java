package utours.ultimate.net;

import org.junit.jupiter.api.*;
import utours.ultimate.net.internal.NetServerApplicationTest;

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

        Message msg1 = client.sendMessage("an.address", "hello");
        assertTrue(msg1.isSuccess());
        assertEquals("hello", msg1.content());

        Message msg2 = client.sendMessage("an.address","world");
        assertTrue(msg2.isSuccess());
        assertEquals("world", msg2.content());

        client.close();
    }



}
