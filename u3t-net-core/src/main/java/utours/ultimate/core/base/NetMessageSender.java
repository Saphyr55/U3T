package utours.ultimate.core.base;

import utours.ultimate.core.*;
import utours.ultimate.core.data.MessageData;

public class NetMessageSender implements MessageSender {

    private final Client client;

    public NetMessageSender(Client client) {
        this.client = client;
    }

    @Override
    public MessageSender send(String address, Object message, Handler<Message> onReceive) {
        try {
            Object messageReceive = client.sendMessage(address, message, Object.class);
            onReceive.handle(new MessageData(address, messageReceive));
            return this;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


}
