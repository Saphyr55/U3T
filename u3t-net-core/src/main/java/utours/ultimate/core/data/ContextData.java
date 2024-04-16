package utours.ultimate.core.data;

import utours.ultimate.core.Client;
import utours.ultimate.core.Context;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public record ContextData(
        ObjectOutputStream writer,
        ObjectInputStream reader,
        Object message,
        Client client
) implements Context {
    
}
