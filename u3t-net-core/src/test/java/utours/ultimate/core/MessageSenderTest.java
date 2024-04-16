package utours.ultimate.core;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import utours.ultimate.core.base.NetServerApplicationTest;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class MessageSenderTest extends NetServerApplicationTest {

    @Test
    void send() {

        Client client = Client.of(HOST_ADDRESS, PORT);

        client.messageSender()
                .send("any.address", "hello", m -> assertEquals("hello", m.content()))
                .send("any.address", "word", m -> assertEquals("word", m.content()))
        //        .send("not.a.address", "word", message -> assertNull(message.content()))
        ;
    }
}