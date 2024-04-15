package utours.ultimate.core;

import utours.ultimate.core.base.ApplicationConfigFileProperties;

public interface ApplicationConfiguration {

    static ApplicationConfiguration ofProperties() {
        return new ApplicationConfigFileProperties();
    }

    static ApplicationConfiguration of(String address, int port) {
        return new ApplicationConfiguration() {
            @Override
            public String address() {
                return address;
            }

            @Override
            public int port() {
                return port;
            }
        };
    }

    String address();

    int port();

}
