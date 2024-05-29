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

public class ClientSocket implements Client {

    private final Socket clientSocket;
    private final OutputStream out;
    private final InputStream in;
    private final ObjectOutputStream oos;
    private final ObjectInputStream ois;
    private final Map<String, List<Handler<Message>>> addresses;
    private Runnable onProcess;

    public ClientSocket(Socket clientSocket) throws IOException {
        this(clientSocket, null);
        setOnProcess(this::defaultClientProcess);
    }

    public ClientSocket(Socket clientSocket, Runnable onThread) throws IOException {
        this.clientSocket = clientSocket;
        this.out = clientSocket.getOutputStream();
        this.in = clientSocket.getInputStream();
        this.oos = new ObjectOutputStream(out);
        this.ois = new ObjectInputStream(in);
        this.addresses = new HashMap<>();
        this.onProcess = onThread;
    }

    public ClientSocket(String address, int port) throws IOException {
        this(new Socket(address, port));
    }

    @Override
    public void sendMessage(String address, Object content) {
        try {
            Message messageWrapper = Message.success(address, content);
            oos.writeObject(messageWrapper);
            oos.flush();
        } catch (Exception e) {
            e.printStackTrace();
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
        if (!addresses.containsKey(address)) {
            addresses.computeIfAbsent(address, s -> new ArrayList<>()).add(handler);
            sendMessage(Message.SUBSCRIBE_ADDRESS, address);
        }
    }

    @Override
    public OutputStream output() {
        return out;
    }

    @Override
    public InputStream input() {
        return in;
    }

    @Override
    public ObjectOutputStream oos() {
        return oos;
    }

    @Override
    public ObjectInputStream ois() {
        return ois;
    }

    public void setOnProcess(Runnable onProcess) {
        this.onProcess = onProcess;
    }

    public void startThread() {
        Thread.ofPlatform().start(onProcess);
    }

    private void defaultClientProcess() {
        while (clientSocket.isConnected()) {
            try {

                Message message = (Message) ois.readObject();

                for (Handler<Message> handler : addresses.get(message.address())) {
                    handler.handle(message);
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

}
