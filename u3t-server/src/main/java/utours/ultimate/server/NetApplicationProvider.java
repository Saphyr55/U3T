package utours.ultimate.server;

import utours.ultimate.core.steorotype.Component;
import utours.ultimate.core.steorotype.Mapping;
import utours.ultimate.net.NetApplication;
import utours.ultimate.net.NetApplicationConfiguration;

@Component
public class NetApplicationProvider {

    private final NetApplication netApplication;

    public NetApplicationProvider() {
        NetApplicationConfiguration configuration = NetApplicationConfiguration.ofFileProperties();
        netApplication = NetApplication.ofServer(configuration);
    }

    @Mapping
    @Component
    public NetApplication getNetApplication() {
        return netApplication;
    }

}
