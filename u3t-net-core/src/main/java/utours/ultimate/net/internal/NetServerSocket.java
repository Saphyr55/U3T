package utours.ultimate.net.internal;

import utours.ultimate.net.*;

import javax.net.ServerSocketFactory;
import java.io.*;
import java.net.ServerSocket;

public class NetServerSocket implements NetServer {

    private final ServerSocket serverSocket;

    public NetServerSocket(NetServerConfiguration configuration) {
        this.serverSocket = createServerSocket(configuration);
    }

    @Override
    public void stop() {
        try {
            serverSocket.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Client acceptClient() {
        try {
            return new ClientSocket(serverSocket.accept());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private ServerSocket createServerSocket(NetServerConfiguration configuration) {
        try {
            return ServerSocketFactory
                    .getDefault()
                    .createServerSocket(configuration.port());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


}
