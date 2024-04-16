package utours.ultimate.core;

import org.junit.jupiter.api.*;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

public class ClientSideTest {

    private static final String ADDRESS = "127.0.0.1";
    private static final int PORT = 7776;
    private static final ApplicationConfiguration configuration =
            ApplicationConfiguration.of(ADDRESS, PORT);

    @BeforeAll
    static void setup() {
        Application application = Application.ofServer(configuration);
        application.handler(ClientSideTest::treatment);
        Thread.ofPlatform().start(application::start);
    }

    static void treatment(Client client) {
        boolean check = true;
        try (var in = client.reader(); var out = client.output()) {
            Object object;
            while (check && (object =  in.readObject()) != null) {
                out.writeObject(object);
                out.flush();
            }
        } catch (IOException | ClassNotFoundException e) {
            // throw new RuntimeException(e);
            check = false;
        }
    }

    @Test
    void check_multiple_client() {
        for (int i = 0; i < 10; i++) {
            Thread.ofPlatform().start(this::check_if_server_respond_correctly);
        }
    }

    @Test
    void check_if_port_and_address_are_equal() {
        Client client = Client.of(ADDRESS, PORT);

        assertEquals(ADDRESS, client.hostAddress());
        assertEquals(PORT, client.port());

        client.close();
    }

    @Test
    void check_if_server_respond_correctly() {
        Client client = Client.of(ADDRESS, PORT);

        String msg1 = client.sendMessage("hello", String.class);
        String msg2 = client.sendMessage("world", String.class);

        client.close();

        assertEquals("hello", msg1);
        assertEquals("world", msg2);
    }

    @AfterAll
    public static void tearDown() {
        // application.stop();
    }

}
