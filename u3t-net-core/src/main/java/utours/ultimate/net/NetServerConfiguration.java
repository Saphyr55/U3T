package utours.ultimate.net;

import utours.ultimate.net.internal.NetServerConfigurationSettings;
import utours.ultimate.net.data.NetServerConfigurationData;

public interface NetServerConfiguration {

    static NetServerConfiguration of(String address, int port) {
        return new NetServerConfigurationData(address, port);
    }


    String hostAddress();

    int port();

}
