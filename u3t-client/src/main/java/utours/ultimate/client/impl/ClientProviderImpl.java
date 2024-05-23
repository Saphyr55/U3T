package utours.ultimate.client.impl;

import utours.ultimate.client.ClientContext;
import utours.ultimate.core.settings.Settings;
import utours.ultimate.core.steorotype.Component;
import utours.ultimate.core.steorotype.Mapping;
import utours.ultimate.client.ClientProvider;
import utours.ultimate.net.NetServerConfiguration;
import utours.ultimate.net.Client;
import utours.ultimate.net.internal.NetServerConfigurationSettings;

@Component
@Mapping
public class ClientProviderImpl implements ClientProvider {
    
    private final Client client;

    public ClientProviderImpl() {
        NetServerConfiguration configuration = new NetServerConfigurationSettings(ClientContext.getContext().settings());
        client = Client.of(configuration.hostAddress(), configuration.port());
    }

    @Override
    public Client getClient() {
        return client;
    }

}
