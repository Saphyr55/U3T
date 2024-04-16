package utours.ultimate.net.data;

import utours.ultimate.net.Client;
import utours.ultimate.net.Context;
import utours.ultimate.net.Message;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public record ContextData(
        ObjectOutputStream writer,
        ObjectInputStream reader,
        Message message,
        Client client,
        String address
) implements Context {
    
}
