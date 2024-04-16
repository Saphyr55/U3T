package utours.ultimate.net.data;

import utours.ultimate.net.ApplicationConfiguration;

public record ApplicationConfigurationData(
        String address,
        int port
) implements ApplicationConfiguration {
}
