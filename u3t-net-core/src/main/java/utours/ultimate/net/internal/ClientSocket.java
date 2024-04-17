package utours.ultimate.net.internal;

import utours.ultimate.net.Client;
import utours.ultimate.net.Handler;
import utours.ultimate.net.Message;
import utours.ultimate.net.data.MessageData;

import java.io.*;
import java.net.Socket;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

public class ClientSocket implements Client {

    private final String identifier;
    private final Socket clientSocket;
    private final ObjectOutputStream out;
    private final ObjectInputStream in;

    public ClientSocket(Socket clientSocket) throws IOException {
        this.clientSocket = clientSocket;
        this.out = new ObjectOutputStream(clientSocket.getOutputStream());
        this.in = new ObjectInputStream(clientSocket.getInputStream());
        this.identifier = UUID.randomUUID().toString();
    }

    public ClientSocket(String address, int port) throws IOException {
        this(new Socket(address, port));
    }

    @Override
    public Message sendMessage(String address, Object content) {
        try {
            Message messageWrapper = new MessageData(address, content, true);
            out.writeObject(messageWrapper);
            out.flush();
            return (Message) in.readObject();
        } catch (Exception e) {
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
    public void onReceiveMessage(String address, Handler<Message> handler) {
        sendMessage(Message.SUBSCRIBE_ADDRESS, address);
        CompletableFuture.runAsync(() -> {
            try {
                Message message = (Message) in.readObject();
                handler.handle(message);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
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
