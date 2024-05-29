package utours.ultimate.net;

import utours.ultimate.core.settings.Settings;
import utours.ultimate.net.internal.NetServerConfigurationSettings;
import utours.ultimate.net.data.NetServerConfigurationData;

public interface NetServerConfiguration {

    static NetServerConfiguration of(String address, int port) {
        return new NetServerConfigurationData(address, port);
    }

    static NetServerConfiguration ofSettings(Settings settings) {
        return new NetServerConfigurationSettings(settings);
    }

    String hostAddress();

    int port();

}
