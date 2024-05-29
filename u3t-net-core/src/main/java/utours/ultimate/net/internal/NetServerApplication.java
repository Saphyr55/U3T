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

    private final NetServer server;
    private boolean stopped = true;

    public NetServerApplication(NetServerConfiguration configuration) {
        this.server = new NetServerSocket(configuration);
    }

    @Override
    public void start() {
        stopped = false;

        handler(Message.SUBSCRIBE_ADDRESS, this::onClientSubscribe);

        while (!stopped) {
            server.acceptClient();
        }
    }

    private void onClientSubscribe(Context context) {
        String subAddress = (String) context.message().content();

        server.subscribers()
                .computeIfAbsent(subAddress, s -> new ArrayList<>())
                .add(context.client());
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
        server.handlers().computeIfAbsent(address, a -> new LinkedList<>()).add(handler);
    }

    @Override
    public void sendMessage(String address, Object content) {

        try {

            if (!server.subscribers().containsKey(address)) {
                return;
            }

            for (Client client : server.subscribers().get(address)) {
                var oos = client.oos();
                oos.writeObject(Message.success(address, content));
                oos.flush();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
