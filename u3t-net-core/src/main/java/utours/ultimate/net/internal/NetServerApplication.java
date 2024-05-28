package utours.ultimate.net.internal;

import utours.ultimate.net.*;
import utours.ultimate.net.data.ContextData;
import utours.ultimate.net.data.MessageData;

import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.SocketException;
import java.util.*;
import java.util.function.Consumer;

public class NetServerApplication implements NetApplication {

    private final Map<String, List<Handler<Context>>> handlers;
    private final Map<String, List<Client>> subscribers;
    private final NetServer server;
    private boolean stopped = true;

    public NetServerApplication(NetServerConfiguration configuration) {
        this.handlers = new HashMap<>();
        this.server = new NetServerSocket(configuration);
        this.subscribers = new HashMap<>();
    }

    @Override
    public void start() {
        stopped = false;
        handler(Message.SUBSCRIBE_ADDRESS, this::onClientSubscribe);
        while (!stopped) {
            Client client = server.acceptClient();
            Thread.ofPlatform().start(() -> processClient(client));
        }
    }

    private void onClientSubscribe(Context context) throws IOException {

        String subAddress = (String) context.message().content();

        subscribers
                .computeIfAbsent(subAddress, s -> new ArrayList<>())
                .add(context.client());

        Message message = Message.success(context.address(), true);

        context.writer().writeObject(message);
        context.writer().flush();
    }

    private void processClient(Client client) {
        try {

            var ois = client.ois();
            var oos = client.oos();

            while (client.isConnected()) {

                Message message = (Message) ois.readObject();
                String address = message.address();

                if (hasAddress(address)) {
                    for (var contextHandler : handlers.get(address)) {
                        var context = new ContextData(oos, ois, message, client, address);
                        contextHandler.handle(context);
                    }
                } else {
                    var failedMessage = Message.error(message.address());
                    oos.writeObject(failedMessage);
                    oos.flush();
                }
            }
        } catch (EOFException ignored) {
        } catch (SocketException e) {
            client.close();
        } catch (Exception e) {
            Thread.currentThread().interrupt();
        }

    }

    private void disconnetClient(Client client) {

        if (client == null) return;

        for (var el : subscribers.entrySet()) {
            el.getValue().remove(client);
        }

    }

    @Override
    public void stop() {
        if (stopped) return;
        try {
            stopped = true;
            server.stop();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void handler(String address, Handler<Context> handler) {
        handlers.computeIfAbsent(address, a -> new LinkedList<>()).add(handler);
    }

    @Override
    public void sendMessage(String address, Object content) {

        try {

            if (!subscribers.containsKey(address)) {
                return;
            }

            for (Client client : subscribers.get(address)) {
                var oos = client.oos();
                oos.writeObject(Message.success(address, content));
                oos.flush();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    boolean hasAddress(String address) {
        return handlers.containsKey(address);
    }

}
