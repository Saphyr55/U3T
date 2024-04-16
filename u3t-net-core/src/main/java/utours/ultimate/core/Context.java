package utours.ultimate.core;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public interface Context {

    ObjectOutputStream writer();

    ObjectInputStream reader();

    Object message();

    Client client();

}
