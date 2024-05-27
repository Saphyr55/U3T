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
                .supplyAsync(() -> client.sendMessage(address, content))
                .thenAccept(response -> acceptResponse(address, response, onReceive))
                .exceptionally(throwable -> acceptError(address, throwable, onReceive));

        return this;
    }

    @Override
    public MessageSender send(String address, Object content) {

        CompletableFuture
                .supplyAsync(() -> client.sendMessage(address, content))
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

    private void acceptResponse(String address, Message response, Handler<Message> onReceive) {
        try {
            onReceive.handle(new MessageData(address, response.content(), response.isSuccess()));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
