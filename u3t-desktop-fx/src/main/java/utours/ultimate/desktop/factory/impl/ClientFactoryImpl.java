package utours.ultimate.desktop.factory.impl;

import utours.ultimate.core.steorotype.Component;
import utours.ultimate.core.steorotype.Mapping;
import utours.ultimate.desktop.factory.ClientFactory;
import utours.ultimate.net.ApplicationConfiguration;
import utours.ultimate.net.Client;

@Component
@Mapping
public class ClientFactoryImpl implements ClientFactory {
    
    private final Client client;

    public ClientFactoryImpl() {
        ApplicationConfiguration configuration = ApplicationConfiguration.ofFileProperties();
        client = Client.of(configuration.address(), configuration.port());
    }

    @Component
    public Client getClient() {
        return client;
    }

}
