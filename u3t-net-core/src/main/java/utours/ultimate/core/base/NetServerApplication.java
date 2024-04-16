package utours.ultimate.core.base;

import utours.ultimate.core.*;
import utours.ultimate.core.data.ContextData;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class NetServerApplication implements Application {

    private final Map<String, List<Handler<Context>>> handlers;
    private final ApplicationConfiguration configuration;
    private final NetServer server;
    private boolean stopped = true;

    public NetServerApplication(ApplicationConfiguration configuration) {
        this.configuration = configuration;
        this.handlers = new HashMap<>();
        this.server = new NetServerSocket(configuration);
    }

    @Override
    public void start() {
        stopped = false;
        while (!stopped) {
            handlers.forEach((address, handlers) -> Thread.ofPlatform()
                    .start(() -> processHandlers(handlers)));
        }
    }

    private void processHandlers(List<Handler<Context>> handlers) {
        try {
            handlers.forEach(contextHandler -> handleContext(server.client(), contextHandler));
        } catch (Exception e) {
            throw new RuntimeException(e);

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

    private void handleContext(Client client, Handler<Context> contextHandler) {
        try (var in = client.input(); var out = client.output()) {
            Object object;
            while ((object = in.readObject()) != null) {
                Context context = new ContextData(out, in, object, client);
                contextHandler.handle(context);
            }
        } catch (Exception ignored) { }
    }

}
