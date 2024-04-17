package utours.ultimate.net.internal;

import utours.ultimate.net.*;
import utours.ultimate.net.data.ContextData;
import utours.ultimate.net.data.MessageData;

import java.io.EOFException;
import java.util.*;

public class NetServerApplication implements Application {

    private final Map<String, List<Handler<Context>>> handlers;
    private final NetServer server;
    private boolean stopped = true;

    public NetServerApplication(ApplicationConfiguration configuration) {
        this.handlers = new HashMap<>();
        this.server = new NetServerSocket(configuration);
    }

    @Override
    public void start() {
        stopped = false;
        while (!stopped) {
            Client client = server.client();
            processClient(client);
        }
    }

    private void processClient(Client client) {
        try {

            var in = client.input();
            var out = client.output();

            Message message = (Message) in.readObject();
            while (message != null) {
                var address = message.address();
                if (hasAddress(address)) {
                    for (Handler<Context> contextHandler : handlers.get(address)) {
                        Context context = new ContextData(out, in, message, client, address);
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
        handlers.computeIfAbsent(address, a -> new LinkedList<>())
                .add(handler);
    }

    boolean hasAddress(String address) {
        return handlers.containsKey(address);
    }

}
