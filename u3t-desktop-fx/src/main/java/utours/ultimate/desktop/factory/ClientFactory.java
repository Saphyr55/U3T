package utours.ultimate.desktop.factory;

import utours.ultimate.net.Client;

public class ClientFactory {

    private final Client client;

    public ClientFactory() {
        client = Client.of("127.0.0.1", 6667);
    }

    public Client getClient() {
        return client;
    }

}
