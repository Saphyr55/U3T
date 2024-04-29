package utours.ultimate.net;

import utours.ultimate.net.internal.ApplicationConfigFileProperties;
import utours.ultimate.net.data.ApplicationConfigurationData;

public interface ApplicationConfiguration {

    static ApplicationConfiguration ofFileProperties() {
        return new ApplicationConfigFileProperties();
    }

    static ApplicationConfiguration of(String address, int port) {
        return new ApplicationConfigurationData(address, port);
    }

    String address();

    int port();

}
