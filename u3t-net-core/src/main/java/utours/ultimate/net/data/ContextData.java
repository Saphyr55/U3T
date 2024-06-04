package utours.ultimate.net.data;

import utours.ultimate.net.Client;
import utours.ultimate.net.Context;
import utours.ultimate.net.Message;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public record ContextData(
        ObjectOutputStream oos,
        ObjectInputStream ois,
        Message message,
        Client client,
        String address
) implements Context {

    @Override
    public void respond(Object... content) {
        try {
            for (Object o : content) {
                oos.writeObject(o);
            }
            oos.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
