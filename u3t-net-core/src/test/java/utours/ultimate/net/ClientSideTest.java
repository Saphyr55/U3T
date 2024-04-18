package utours.ultimate.net;

import org.junit.jupiter.api.*;
import utours.ultimate.net.internal.NetServerApplicationTest;

import java.util.List;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.*;


public class ClientSideTest extends NetServerApplicationTest {

    @Test
    void check_multiple_client() {
        var clients = IntStream.range(0, 10)
                .mapToObj(value -> Client.of(HOST_ADDRESS, PORT))
                .toList();

        for (Client client : clients) {
            Message msg1 = client.sendMessage("an.address", "hello");
            assertTrue(msg1.isSuccess());
            assertEquals("hello", msg1.content());
        }

        for (Client client : clients) {
            client.close();
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

    @Test
    void check_if_server_send_correctly() {

        Client client = Client.of(HOST_ADDRESS, PORT);

        client.sendMessage("an.address", "hello");

        client.onReceiveMessage("baz.server.address", msg -> {
            assertEquals("baz", msg.content());
            client.close();
        });

        application.sendMessage("baz.server.address", "baz");
    }



}
