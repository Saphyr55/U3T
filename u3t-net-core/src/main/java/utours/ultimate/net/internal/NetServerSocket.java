package utours.ultimate.net.internal;

import utours.ultimate.net.*;
import utours.ultimate.net.data.ContextData;

import javax.net.ServerSocketFactory;
import java.io.*;
import java.net.ServerSocket;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class NetServerSocket implements NetServer {

    private final ServerSocket serverSocket;
    private final Map<String, List<Handler<Context>>> handlers;
    private final Map<String, List<Client>> subscribers;

    public NetServerSocket(NetServerConfiguration configuration) {
        this.serverSocket = createServerSocket(configuration);
        this.handlers = new HashMap<>();
        this.subscribers = new HashMap<>();
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

            ClientSocket client = new ClientSocket(serverSocket.accept());
            client.setOnProcess(() -> processClient(client));
            client.startThread();

            return client;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private ServerSocket createServerSocket(NetServerConfiguration configuration) {
        try {
            return ServerSocketFactory.getDefault()
                    .createServerSocket(configuration.port());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void processClient(Client client) {
        try {

            while (client.isConnected()) {

                ObjectInputStream ois = client.ois();

                Message message = (Message) ois.readObject();
                String address = message.address();

                if (hasAddress(address)) {

                    for (Handler<Context> handler : handlers.get(address)) {
                        Context context = Context.dataOf(message, client, address);
                        handler.handle(context);
                    }

                }

            }

        } catch (Exception e) {
            Thread.currentThread().interrupt();
        } finally {
            disconnetClient(client);
            client.close();
        }

    }

    private void disconnetClient(Client client) {

        if (client == null) return;

        for (var el : subscribers.entrySet()) {
            el.getValue().remove(client);
        }

    }

    @Override
    public boolean hasAddress(String address) {
        return handlers.containsKey(address);
    }

    @Override
    public Map<String, List<Handler<Context>>> handlers() {
        return handlers;
    }

    @Override
    public Map<String, List<Client>> subscribers() {
        return subscribers;
    }

    @Override
    public ServerSocket serverSocket() {
        return serverSocket;
    }

}
