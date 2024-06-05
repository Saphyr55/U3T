package utours.ultimate.net.internal;

import utours.ultimate.net.*;

import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class NetServerApplication implements NetApplication {

    private static final Logger LOGGER = Logger.getLogger(NetServerApplication.class.getName());

    private final NetServer server;
    private boolean stopped = true;

    public NetServerApplication(NetServerConfiguration configuration) {

        this.server = new NetServerSocket(configuration);

        addShutdownHook();
    }

    @Override
    public void start() {

        stopped = false;

        handler(Message.SUBSCRIBE_ADDRESS,  this::onClientSubscribe);
        handler(Message.ERROR_ADDRESS,      this::onError);

        while (!stopped) {
            server.acceptClient();
        }

    }

    private void onError(Context context) {
        context.respond(context.message().content());
    }

    private void onClientSubscribe(Context context) {

        String subAddress = (String) context.message().content();

        server.subscribers()
                .computeIfAbsent(subAddress, s -> new ArrayList<>())
                .add(context.client());
    }

    @Override
    public void stop() {

        if (stopped) {
            return;
        }

        stopped = true;
        server.close();
    }

    @Override
    public void handler(String address, Handler<Context> handler) {
        server.handlers()
                .computeIfAbsent(address, a -> new LinkedList<>())
                .add(handler);
    }

    @Override
    public void sendMessage(String address, Object content) {

        if (!server.subscribers().containsKey(address)) {
            return;
        }

        for (Client client : server.subscribers().get(address)) {
            if (client.isConnected()) {
                client.messageSender().send(address, content);
            }
        }

    }

    private void addShutdownHook() {
        Runtime.getRuntime().addShutdownHook(defaultThread(() -> {
            LOGGER.log(Level.INFO, () -> "Application is shutting down...");
            stop();
            LOGGER.log(Level.INFO, () -> "Cleanup complete.");
        }));
    }

    private static Thread defaultThread(Runnable onStart) {
        return Thread.ofVirtual().factory().newThread(onStart);
    }

}
