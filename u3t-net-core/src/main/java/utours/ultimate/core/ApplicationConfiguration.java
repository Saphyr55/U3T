package utours.ultimate.core;

import utours.ultimate.core.base.ApplicationConfigFileProperties;
import utours.ultimate.core.data.ApplicationConfigurationData;

public interface ApplicationConfiguration {
    
    static ApplicationConfiguration ofProperties() {
        return new ApplicationConfigFileProperties();
    }

    static ApplicationConfiguration of(String address, int port) {
        return new ApplicationConfigurationData(address, port);
    }

    String address();

    int port();

}
