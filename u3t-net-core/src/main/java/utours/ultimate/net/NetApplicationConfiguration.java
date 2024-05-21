package utours.ultimate.net;

import utours.ultimate.net.internal.NetApplicationConfigFileProperties;
import utours.ultimate.net.data.NetApplicationConfigurationData;

public interface NetApplicationConfiguration {

    static NetApplicationConfiguration ofFileProperties() {
        return new NetApplicationConfigFileProperties();
    }

    static NetApplicationConfiguration of(String address, int port) {
        return new NetApplicationConfigurationData(address, port);
    }

    String address();

    int port();

}
