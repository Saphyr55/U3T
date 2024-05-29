package utours.ultimate.net;

import utours.ultimate.net.data.ContextData;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public interface Context {

    static Context dataOf(Message message, Client client, String address) {
        return new ContextData(client.oos(), client.ois(), message, client, address);
    }

    ObjectOutputStream writer();

    ObjectInputStream reader();

    Message message();

    Client client();

    String address();

    void respond(Object... content);

}
