package utours.ultimate.core.base;

import utours.ultimate.core.ApplicationConfiguration;

public record ApplicationConfigurationData(
        String address,
        int port
) implements ApplicationConfiguration {
}
