package utours.ultimate.net.data;

import utours.ultimate.net.Client;
import utours.ultimate.net.Context;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public record ContextData(
        ObjectOutputStream writer,
        ObjectInputStream reader,
        Object message,
        Client client
) implements Context {
    
}
