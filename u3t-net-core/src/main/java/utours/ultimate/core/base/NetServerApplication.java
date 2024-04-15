package utours.ultimate.core.base;

import utours.ultimate.core.*;

import java.io.*;
import java.util.LinkedList;
import java.util.List;

public class NetServerApplication implements Application {

    private final List<Handler<Client>> handlers;
    private final ApplicationConfiguration configuration;
    private final NetServer server;
    private boolean stopped = true;

    public NetServerApplication(ApplicationConfiguration configuration) throws IOException {
        this.configuration = configuration;
        this.handlers = new LinkedList<>();
        this.server = new NetServerSocket(configuration);
    }

    @Override
    public void start() {
        while (!stopped) {
            handlers.forEach(clientHandler -> {
                Thread.ofPlatform().start(() -> {
                    clientHandler.handle(server.client());
                });
            });
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
    public void handler(Handler<Client> handler) {
        handlers.add(handler);
    }


}
