package utours.ultimate.desktop.factory.impl;

import utours.ultimate.desktop.factory.ClientFactory;
import utours.ultimate.net.Client;

public class ClientFactoryImpl implements ClientFactory {

    private final Client client;

    public ClientFactoryImpl() {
        client = Client.of("127.0.0.1", 6667);
    }

    public Client getClient() {
        return client;
    }

}
