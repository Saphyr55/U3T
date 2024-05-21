package utours.ultimate.net.data;

import utours.ultimate.net.NetApplicationConfiguration;

public record NetApplicationConfigurationData(
        String address,
        int port
) implements NetApplicationConfiguration { }
