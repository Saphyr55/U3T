package utours.ultimate.core;

import utours.ultimate.core.base.ClientSocket;

import java.io.*;

/**
 *
 */
public interface Client {

    /**
     * Instantiate a client.
     *
     * @param address Server address to connect.
     * @param port Server port to connect.
     * @return the client corresponding.
     */
    static Client of(String address, int port) {
        try {
            return new ClientSocket(address, port);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Give the writer from the client.
     *
     * @return A writer from the client.
     */
    ObjectOutputStream output();

    /**
     * Give the reader from the client.
     *
     * @return A reader from the client.
     */
    ObjectInputStream reader();

    /**
     * Send a message, and return a response from the server.
     *
     * @param message An object corresponding to a message that will be sent to the server.
     * @return The response from the server.
     */
    <T> T sendMessage(Object message, Class<T> tClass);

    /**
     * Close the client.
     */
    void close();

    /**
     * Give the server port.
     *
     * @return Server port.
     */
    int port();

    /**
     * Give the server host address.
     *
     * @return Server host address.
     */
    String hostAddress();

}