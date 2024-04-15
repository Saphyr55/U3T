package utours.ultimate.core;

import utours.ultimate.core.base.ClientSocket;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

public interface Client {

    static Client of(String address, int port) {
        try {
            return new ClientSocket(address, port);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    PrintWriter writer();

    BufferedReader reader();

    String sendMessage(String message);

    void close();

    int port();

    String hostAddress();

}
