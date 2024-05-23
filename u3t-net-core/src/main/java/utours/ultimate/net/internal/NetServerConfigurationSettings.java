package utours.ultimate.net.internal;

import utours.ultimate.core.settings.Settings;
import utours.ultimate.net.NetServerConfiguration;

public class NetServerConfigurationSettings implements NetServerConfiguration {

    public static final String KEY_SERVER_HOST_ADDRESS = "server.host-address";
    public static final String VALUE_SERVER_HOST_ADDRESS = "127.0.0.1";
    public static final String KEY_SERVER_PORT = "server.port";
    public static final int VALUE_SERVER_PORT = 6667;
    
    private final String hostAddress;
    private final int port;

    public NetServerConfigurationSettings(Settings settings) {
        this.hostAddress = settings.getValue(KEY_SERVER_HOST_ADDRESS, VALUE_SERVER_HOST_ADDRESS);
        this.port = Integer.parseInt(settings.getValue(KEY_SERVER_PORT, String.valueOf(VALUE_SERVER_PORT)));
    }

    @Override
    public String hostAddress() {
        return hostAddress;
    }

    @Override
    public int port() {
        return port;
    }
}
