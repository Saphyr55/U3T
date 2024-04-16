package utours.ultimate.net;

import utours.ultimate.net.internal.NetMessageSender;

public interface MessageSender {

    static MessageSender ofClient(Client client) {
        return new NetMessageSender(client);
    }

    MessageSender send(String address, Object message, Handler<Message> handler);

}
