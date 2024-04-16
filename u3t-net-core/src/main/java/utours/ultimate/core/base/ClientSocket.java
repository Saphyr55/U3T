package utours.ultimate.core.base;

import utours.ultimate.core.Client;
import utours.ultimate.core.Message;
import utours.ultimate.core.data.MessageData;

import java.io.*;
import java.net.Socket;

public class ClientSocket implements Client {

    private final Socket clientSocket;
    private final ObjectOutputStream out;
    private final ObjectInputStream in;

    public ClientSocket(Socket clientSocket) throws IOException {
        this.clientSocket = clientSocket;
        this.out = new ObjectOutputStream(clientSocket.getOutputStream());
        this.in = new ObjectInputStream(clientSocket.getInputStream());
    }

    public ClientSocket(String address, int port) throws IOException {
        this(new Socket(address, port));
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T> T sendMessage(String address, Object message, Class<T> tClass) {
        try {

            Message messageWrapper = new MessageData(address, message);
            out.writeObject(messageWrapper);
            out.flush();

            Message receiveMessage = (Message) in.readObject();
            return (T) receiveMessage.content();

        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void close() {
        try {
            out.close();
            in.close();
            clientSocket.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public int port() {
        return clientSocket.getPort();
    }

    @Override
    public String hostAddress() {
        return clientSocket.getInetAddress().getHostAddress();
    }

    @Override
    public ObjectOutputStream output() {
        return out;
    }

    @Override
    public ObjectInputStream input() {
        return in;
    }


}
