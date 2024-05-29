package utours.ultimate.net.internal;

import utours.ultimate.net.*;
import utours.ultimate.net.data.MessageData;

import java.util.concurrent.CompletableFuture;

public class NetMessageSender implements MessageSender {

    private final Client client;

    public NetMessageSender(Client client) {
        this.client = client;
    }

    @Override
    public MessageSender send(String address, Object content, Handler<Message> onReceive) {

        CompletableFuture
                .runAsync(() -> client.sendMessage(address, content))
                .exceptionally(throwable -> acceptError(address, throwable, onReceive));

        return this;
    }

    @Override
    public MessageSender send(String address, Object content) {

        CompletableFuture
                .runAsync(() -> client.sendMessage(address, content))
                .thenAccept(response -> { })
                .exceptionally(throwable -> null);

        return this;
    }

    private Void acceptError(String address, Throwable throwable, Handler<Message> onReceive) {
        try {
            onReceive.handle(Message.error(address));
        } catch (Exception e) {
            e.addSuppressed(throwable);
            throw new RuntimeException(e);
        }
        return null;
    }

}
