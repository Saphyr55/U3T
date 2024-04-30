package utours.ultimate.desktop.factory.impl;

import utours.ultimate.desktop.factory.ClientFactory;
import utours.ultimate.net.ApplicationConfiguration;
import utours.ultimate.net.Client;

public class ClientFactoryImpl implements ClientFactory {

    private final Client client;

    public ClientFactoryImpl() {
        ApplicationConfiguration configuration = ApplicationConfiguration.ofFileProperties();
        client = Client.of(configuration.address(), configuration.port());
    }

    public Client getClient() {
        return client;
    }

}
