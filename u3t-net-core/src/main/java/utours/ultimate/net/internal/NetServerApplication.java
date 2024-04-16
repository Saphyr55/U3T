package utours.ultimate.net.internal;

import utours.ultimate.net.*;
import utours.ultimate.net.data.ContextData;
import utours.ultimate.net.data.MessageData;

import java.io.EOFException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

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
            Thread.ofPlatform().start(() -> handlers.forEach((address, handlers) -> {
                processHandlers(client, address, handlers);
            }));
        }
    }

    private void processHandlers(Client client, String address, List<Handler<Context>> handlers) {
        handlers.forEach(contextHandler -> handleContext(address, client, contextHandler));
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

    private void handleContext(String address, Client client, Handler<Context> contextHandler) {
        try {
            Message message;
            var in = client.input(); var out = client.output();
            while ((message = (Message) in.readObject()) != null) {

                if (!handlers.containsKey(message.address())) {
                    message = new MessageData(address, message.content(), false);
                }

                if (message.address().equals(address)) {
                    Context context = new ContextData(out, in, message, client, address);
                    contextHandler.handle(context);
                }
            }
        } catch (EOFException ignored) {
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
