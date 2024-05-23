package utours.ultimate.server;

import utours.ultimate.core.settings.Settings;
import utours.ultimate.core.steorotype.Component;
import utours.ultimate.core.steorotype.Mapping;
import utours.ultimate.net.NetApplication;
import utours.ultimate.net.internal.NetServerConfigurationSettings;

@Component
public class NetApplicationProvider {

    private final NetApplication netApplication;
    
    public NetApplicationProvider() {
        netApplication = NetApplication.ofServer(new NetServerConfigurationSettings(Main.getContext().settings()));
    }

    @Mapping
    @Component
    public NetApplication getNetApplication() {
        return netApplication;
    }

}
