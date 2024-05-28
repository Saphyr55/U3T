package utours.ultimate.net.internal;

import utours.ultimate.net.Client;
import utours.ultimate.net.Handler;
import utours.ultimate.net.Message;
import utours.ultimate.net.data.MessageData;

import java.io.*;
import java.net.Socket;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

public class ClientSocket implements Client {

    private final Socket clientSocket;
    private final OutputStream out;
    private final InputStream in;
    private final ObjectOutputStream oos;
    private final Map<String, List<Handler<Message>>> onReceiveMessageHandlers;
    private final ObjectInputStream ois;
    private Thread clientThread;

    public ClientSocket(Socket clientSocket) throws IOException {
        this.clientSocket = clientSocket;
        this.out = clientSocket.getOutputStream();
        this.in = clientSocket.getInputStream();
        this.onReceiveMessageHandlers = new HashMap<>();
        // this.clientThread = Thread.ofPlatform().start(this::onReceiveMessages);
        this.oos = new ObjectOutputStream(out);
        this.ois = new ObjectInputStream(in);
    }

    public ClientSocket(String address, int port) throws IOException {
        this(new Socket(address, port));
    }

    @Override
    public Message sendMessage(String address, Object content) {
        try {

            Message messageWrapper = Message.success(address, content);
            oos.writeObject(messageWrapper);
            oos.flush();

            return (Message) ois.readObject();
        } catch (Exception e) {
            e.printStackTrace();
            return Message.error(address);
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
    public boolean isConnected() {
        return clientSocket.isConnected();
    }

    @Override
    public boolean isClosed() {
        return clientSocket.isClosed();
    }

    @Override
    public Socket socket() {
        return clientSocket;
    }

    @Override
    public void onReceiveMessage(String address, Handler<Message> handler) {
        sendMessage(Message.SUBSCRIBE_ADDRESS, address);
        CompletableFuture.runAsync(() -> {
            try {
                Message message = (Message) ois.readObject();
                while (message != null && message.address().equals(address)) {
                    handler.handle(message);
                    message = (Message) ois.readObject();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    @Override
    public OutputStream output() {
        try {
            return clientSocket.getOutputStream();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public InputStream input() {
        try {
            return clientSocket.getInputStream();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public ObjectOutputStream oos() {
        return oos;
    }

    @Override
    public ObjectInputStream ois() {
        return ois;
    }


}
