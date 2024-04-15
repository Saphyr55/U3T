package utours.ultimate.core.base;

import utours.ultimate.core.Client;

import java.io.*;
import java.net.Socket;

public class ClientSocket implements Client {

    // Must always be true.
    private static final boolean IS_FLUSH = true;

    private final Socket clientSocket;
    private final ObjectOutputStream out;
    private final BufferedReader in;

    public ClientSocket(Socket clientSocket) throws IOException {
        this.clientSocket = clientSocket;
        this.out = new ObjectOutputStream(clientSocket.getOutputStream());
        this.in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
    }

    public ClientSocket(String address, int port) throws IOException {
        this(new Socket(address, port));
    }

    @Override
    public String sendMessage(Object message) {
        try {
            out.writeObject(message);
            out.flush();
            return in.readLine();
        } catch (IOException e) {
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
    public BufferedReader reader() {
        return in;
    }


}
