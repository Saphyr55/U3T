package utours.ultimate.core.base;

import utours.ultimate.core.Client;
import utours.ultimate.core.Handler;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ClientSocket implements Client {

    // Must always be true.
    private static final boolean IS_FLUSH = true;

    private final Socket clientSocket;
    private final PrintWriter out;
    private final BufferedReader in;

    public ClientSocket(Socket clientSocket) throws IOException {
        this.clientSocket = clientSocket;
        this.out = new PrintWriter(clientSocket.getOutputStream(), IS_FLUSH);
        this.in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
    }

    public ClientSocket(String address, int port) throws IOException {
        this(new Socket(address, port));
    }

    @Override
    public String sendMessage(String message) {
        try {
            out.println(message);
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
    public PrintWriter writer() {
        return out;
    }

    @Override
    public BufferedReader reader() {
        return in;
    }


}
