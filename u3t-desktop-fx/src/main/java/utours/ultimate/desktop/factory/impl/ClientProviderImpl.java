package utours.ultimate.desktop.factory.impl;

import utours.ultimate.core.steorotype.Component;
import utours.ultimate.core.steorotype.Mapping;
import utours.ultimate.desktop.factory.ClientProvider;
import utours.ultimate.net.NetApplicationConfiguration;
import utours.ultimate.net.Client;

@Component
@Mapping
public class ClientProviderImpl implements ClientProvider {
    
    private final Client client;

    public ClientProviderImpl() {
        NetApplicationConfiguration configuration = NetApplicationConfiguration.ofFileProperties();
        client = Client.of(configuration.address(), configuration.port());
    }

    @Override
    public Client getClient() {
        return client;
    }

}
