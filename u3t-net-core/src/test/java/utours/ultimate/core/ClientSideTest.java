package utours.ultimate.core;

import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

public class ClientSideTest {

    private static final String ADDRESS = "127.0.0.1";
    private static final int PORT = 7776;
    private static final ApplicationConfiguration configuration =
            ApplicationConfiguration.of(ADDRESS, PORT);

    @BeforeAll
    public static void setup() {
        Application application = Application.ofServer(configuration);
        application.handler(ClientSideTest::treatment);
        Thread.ofPlatform().start(application::start);
    }

    static void treatment(Client client) {
        try (var in = client.reader(); var out = client.output()) {
            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                out.writeObject(inputLine);
                out.flush();
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void check_multiple_client() {
        for (int i = 0; i < 10; i++) {
            Thread.ofPlatform().start(this::check_if_server_respond_correctly);
        }
    }

    @Test
    public void check_if_port_and_address_are_equal() {
        Client client = Client.of(ADDRESS, PORT);

        assertEquals(ADDRESS, client.hostAddress());
        assertEquals(PORT, client.port());

        client.close();
    }

    @Test
    public void check_if_server_respond_correctly() {
        Client client = Client.of(ADDRESS, PORT);

        String msg1 = client.sendMessage("hello");
        String msg2 = client.sendMessage("world");

        client.close();

        assertEquals("hello", msg1);
        assertEquals("world", msg2);
    }

    @AfterAll
    public static void tearDown() {
        // application.stop();
    }

}
