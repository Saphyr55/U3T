package utours.ultimate.core.data;

import utours.ultimate.core.ApplicationConfiguration;

public record ApplicationConfigurationData(
        String address,
        int port
) implements ApplicationConfiguration {
}
