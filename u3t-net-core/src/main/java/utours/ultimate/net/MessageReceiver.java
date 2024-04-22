package utours.ultimate.net;

import utours.ultimate.net.internal.NetClientMessageReceiver;

public interface MessageReceiver {

    static MessageReceiver ofClient(Client client) {
        return new NetClientMessageReceiver(client);
    }

    MessageReceiver receive(String address, Handler<Message> handler);

}
