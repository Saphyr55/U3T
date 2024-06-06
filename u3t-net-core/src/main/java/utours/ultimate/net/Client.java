package utours.ultimate.net;

import utours.ultimate.net.internal.ClientSocket;

import java.io.*;
import java.net.Socket;

/**
 *
 */
public interface Client {

    /**
     * Instantiate a socket client and start the thread to process incoming messages.
     *
     * @param address Server address to connect.
     * @param port Server port to connect.
     * @return the client corresponding.
     */
    static Client of(String address, int port) {
        try {
            ClientSocket clientSocket = new ClientSocket(address, port);
            clientSocket.startThread();
            return clientSocket;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Give the oos from the client.
     *
     * @return A oos from the client.
     */
    ObjectOutputStream oos();

    /**
     * Give the ois from the client.
     *
     * @return A ois from the client.
     */
    ObjectInputStream ois();

    /**
     * Send a message to an address, and return a response from the server.
     *
     * @param content An object corresponding to a message that will be sent to the server.
     * @return The response from the server.
     */
    void sendMessage(String address, Object content);

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

    /**
     * Check if the client is connected.
     *
     * @return is connected.
     */
    boolean isConnected();

    /**
     * Check if the client is closed.
     *
     * @return is closed.
     */
    boolean isClosed();

    /**
     * Give the client socket.
     *
     * @return the client socket.
     */
    Socket socket();

    /**
     * Subscribes the client to the server at a specific address.
     * Attaches a message handler for the server's response.
     *
     * @param address Address.
     * @param handler Message handler.
     */
    void onReceiveMessage(String address, Handler<Message> handler);

    /**
     * Instantiate Message Sender, utility factory method.
     *
     * @return A message sender.
     */
    default MessageSender messageSender() {
        return MessageSender.ofClient(this);
    }

    default MessageReceiver messageReceiver() { return MessageReceiver.ofClient(this); }

}
