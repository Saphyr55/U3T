package utours.ultimate.net.data;

import utours.ultimate.net.NetServerConfiguration;

public record NetServerConfigurationData(
        String hostAddress,
        int port
) implements NetServerConfiguration { }
