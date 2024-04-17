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
        CompletableFuture.runAsync(() -> {
            try {
                Message response = client.sendMessage(address, content);
                onReceive.handle(new MessageData(address,
                        response.content(),
                        response.isSuccess()));
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
        return this;
    }


}
