package utours.ultimate.net.internal;

import utours.ultimate.net.Client;
import utours.ultimate.net.Handler;
import utours.ultimate.net.Message;
import utours.ultimate.net.data.MessageData;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

public class ClientSocket implements Client {

    private final Socket clientSocket;
    private final ObjectOutputStream out;
    private final ObjectInputStream in;
    private final Map<String, List<Handler<Message>>> onReceiveMessageHandlers;
    private Thread clientThread;

    public ClientSocket(Socket clientSocket) throws IOException {
        this.clientSocket = clientSocket;
        this.out = new ObjectOutputStream(clientSocket.getOutputStream());
        this.in = new ObjectInputStream(clientSocket.getInputStream());
        this.onReceiveMessageHandlers = new HashMap<>();
        // this.clientThread = Thread.ofPlatform().start(this::onReceiveMessages);
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
                while (message != null && message.address().equals(address)) {
                    handler.handle(message);
                    message = (Message) in.readObject();
                }
            } catch (Exception e) {
                e.printStackTrace();
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

    private void onReceiveMessages() {
        try {
            /*
            while (true) {
                Message message = (Message) in.readObject();
                for (Handler<Message> messageHandler : onReceiveMessageHandlers.get(message.address())) {
                    messageHandler.handle(message);
                }
            }
             */
        } catch (Throwable t) {
            t.printStackTrace();
        }
    }


}
