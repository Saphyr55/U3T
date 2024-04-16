package utours.ultimate.core.base;

import utours.ultimate.core.*;

import javax.net.ServerSocketFactory;
import java.io.*;
import java.net.ServerSocket;

public class NetServerSocket implements NetServer {

    private final ServerSocket serverSocket;

    public NetServerSocket(ApplicationConfiguration configuration) {
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
    public Client client() {
        try {
            return new ClientSocket(serverSocket.accept());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private ServerSocket createServerSocket(ApplicationConfiguration configuration) {
        try {
            return ServerSocketFactory
                    .getDefault()
                    .createServerSocket(configuration.port());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


}
