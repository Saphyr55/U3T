package utours.ultimate.net.internal;

import utours.ultimate.net.Client;
import utours.ultimate.net.Handler;
import utours.ultimate.net.Message;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ClientSocket implements Client {

    private static final Logger LOGGER = Logger.getLogger(ClientSocket.class.getName());

    private final Socket clientSocket;
    private final ObjectOutputStream oos;
    private final ObjectInputStream ois;
    private final Map<String, List<Handler<Message>>> addresses;
    private Runnable task;

    public ClientSocket(Socket clientSocket) throws IOException {
        this(clientSocket, null);

        setTask(this::defaultClientProcess);
    }

    public ClientSocket(Socket clientSocket, Thread onThread) throws IOException {

        this.clientSocket = clientSocket;
        this.oos = new ObjectOutputStream(clientSocket.getOutputStream());
        this.ois = new ObjectInputStream(clientSocket.getInputStream());
        this.addresses = new HashMap<>();
        this.task = onThread;

        addShutdownHook();
    }

    public ClientSocket(String address, int port) throws IOException {
        this(new Socket(address, port));
    }

    @Override
    public void sendMessage(String address, Object content) {

        try {

            synchronized (this) {
                Message message = Message.success(address, content);
                oos.writeObject(message);
                oos.flush();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void close() {
        try {

            if (oos != null) {
                oos.close();
            }

            if (ois != null) {
                ois.close();
            }

            if (clientSocket != null && !clientSocket.isClosed()) {
                clientSocket.close();
            }

        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    @Override
    public int port() {
        return clientSocket.getPort();
    }

    @Override
    public String hostAddress() {

        if (!isConnected()) {
            return null;
        }

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
        if (!addresses.containsKey(address)) {
            addresses.computeIfAbsent(address, s -> new ArrayList<>()).add(handler);
            sendMessage(Message.SUBSCRIBE_ADDRESS, address);
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

    public void setTask(Runnable task) {
        this.task = task;
    }

    public void startThread() {
        Thread.ofVirtual().start(task);
    }

    private void defaultClientProcess() {

        try {

            while (isProcessing()) {

                Message message = (Message) ois.readObject();

                for (Handler<Message> handler : addresses.get(message.address())) {
                    try {
                        handler.handle(message);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close();
            Thread.currentThread().interrupt();
        }

    }

    private boolean isProcessing() {
        return isConnected() && !Thread.currentThread().isInterrupted();
    }

    private void addShutdownHook() {
        Runtime.getRuntime().addShutdownHook(defaultThread(() -> {
            LOGGER.log(Level.INFO, () -> "Client is shutting down...");
            close();
            LOGGER.log(Level.INFO, () -> "Client is down.");
        }));
    }

    private static Thread defaultThread(Runnable task) {
        return Thread.ofVirtual().factory().newThread(task);
    }

}
