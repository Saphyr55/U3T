package utours.ultimate.net.internal;

import utours.ultimate.net.Client;
import utours.ultimate.net.Handler;
import utours.ultimate.net.Message;
import utours.ultimate.net.MessageReceiver;

public class NetClientMessageReceiver implements MessageReceiver {

    private final Client client;

    public NetClientMessageReceiver(Client client) {
        this.client = client;
    }

    @Override
    public MessageReceiver receive(String address, Handler<Message> handler) {
        client.onReceiveMessage(address, handler);
        return this;
    }

}
