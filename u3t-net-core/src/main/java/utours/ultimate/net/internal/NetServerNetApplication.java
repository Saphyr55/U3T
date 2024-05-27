package utours.ultimate.net.internal;

import utours.ultimate.net.*;
import utours.ultimate.net.data.ContextData;
import utours.ultimate.net.data.MessageData;

import java.io.EOFException;
import java.io.IOException;
import java.util.*;

public class NetServerNetApplication implements NetApplication {

    private final Map<String, List<Handler<Context>>> handlers;
    private final Map<String, List<Client>> subscribers;
    private final NetServer server;
    private boolean stopped = true;

    public NetServerNetApplication(NetServerConfiguration configuration) {
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

        var message = new MessageData(context.address(), true, true);

        context.writer().writeObject(message);
        context.writer();
    }

    private void processClient(Client client) {
        try {

            var in = client.input();
            var out = client.output();

            var message = (Message) in.readObject();
            while (message != null) {
                var address = message.address();
                if (hasAddress(address)) {
                    for (var contextHandler : handlers.get(address)) {
                        var context = new ContextData(out, in, message, client, address);
                        contextHandler.handle(context);
                    }
                } else {
                    var failedMessage = new MessageData(message.address(), null, false);
                    out.writeObject(failedMessage);
                    out.flush();
                }
                message = (Message) in.readObject();
            }
        } catch (EOFException ignored) {
        } catch (Exception e) {
            Thread.currentThread().interrupt();
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
                client.output().writeObject(new MessageData(address, content, true));
                client.output().flush();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    boolean hasAddress(String address) {
        return handlers.containsKey(address);
    }

}
