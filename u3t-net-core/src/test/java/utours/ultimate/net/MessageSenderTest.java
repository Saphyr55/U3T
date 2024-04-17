package utours.ultimate.net;

import org.junit.jupiter.api.Test;
import utours.ultimate.net.internal.NetServerApplicationTest;

import static org.junit.jupiter.api.Assertions.*;

class MessageSenderTest extends NetServerApplicationTest {

    @Test
    void send() {

        Client client = Client.of(HOST_ADDRESS, PORT);
        client.messageSender()
                .send("an.address", "word",  m -> assertEquals("word", m.content()))
                .send("foo.address", "bar", m -> assertEquals("<foo>bar</foo>", m.content()))
                .send("not.an.address", "word", m -> assertFalse(m.isSuccess()));

        client.close();
    }


}