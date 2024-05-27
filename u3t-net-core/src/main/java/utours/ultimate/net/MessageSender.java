package utours.ultimate.net;

import utours.ultimate.net.internal.NetMessageSender;

public interface MessageSender {

    static MessageSender ofClient(Client client) {
        return new NetMessageSender(client);
    }

    /**
     *
     *
     * @param address
     * @param message
     * @param handler
     * @return
     */
    MessageSender send(String address, Object message, Handler<Message> handler);

    /**
     *
     * @param address
     * @param message
     * @return
     */
    MessageSender send(String address, Object message);

}
