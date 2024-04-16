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
            handlers.forEach((address, handlers) ->
                    new Thread((() -> processHandlers(address, handlers))).start());
        }
    }

    private void processHandlers(String address, List<Handler<Context>> handlers) {
        handlers.forEach(contextHandler -> handleContext(address, server.client(), contextHandler));
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

        try (var in = client.input(); var out = client.output()) {

            Message message;
            while ((message = (Message) in.readObject()) != null) {

                if (!handlers.containsKey(message.address())) {
                    message = new MessageData(address, message.content(), false);
                }

                if (message.address().equals(address)) {
                    Context context = new ContextData(out, in, message, client, address);
                    contextHandler.handle(context);
                }
            }
        } catch (EOFException e) {
            e.printStackTrace();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
