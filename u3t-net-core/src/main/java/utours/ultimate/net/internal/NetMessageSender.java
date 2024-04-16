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
    public MessageSender send(String address, Object message, Handler<Message> onReceive) {
        CompletableFuture
                .supplyAsync(() -> client.sendMessage(address, message, Object.class))
                .thenAccept(object -> {
                    try {
                        onReceive.handle(new MessageData(address, object, true));
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                });
        return this;
    }


}
