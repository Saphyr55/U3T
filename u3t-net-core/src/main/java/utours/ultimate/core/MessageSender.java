package utours.ultimate.core;

import utours.ultimate.core.base.NetMessageSender;

public interface MessageSender {

    static MessageSender ofClient(Client client) {
        return new NetMessageSender(client);
    }

    MessageSender send(String address, Object message, Handler<Message> handler);

}
