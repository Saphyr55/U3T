package utours.ultimate.client.impl;

import utours.ultimate.client.ClientContext;
import utours.ultimate.core.steorotype.Component;
import utours.ultimate.core.steorotype.Mapping;
import utours.ultimate.client.ClientProvider;
import utours.ultimate.net.NetServerConfiguration;
import utours.ultimate.net.Client;
import utours.ultimate.net.internal.NetServerConfigurationSettings;

@Mapping
@Component
public class ClientProviderImpl implements ClientProvider {

    private final Client client;

    public ClientProviderImpl() {
        NetServerConfiguration configuration = new NetServerConfigurationSettings(ClientContext.getContext().getSettings());
        client = Client.of(configuration.hostAddress(), configuration.port());
    }

    @Override
    public Client getClient() {
        return client;
    }

}
