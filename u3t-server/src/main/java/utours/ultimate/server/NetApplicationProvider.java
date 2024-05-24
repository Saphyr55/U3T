package utours.ultimate.server;

import utours.ultimate.core.steorotype.Component;
import utours.ultimate.core.steorotype.Mapping;
import utours.ultimate.net.NetApplication;
import utours.ultimate.net.internal.NetServerConfigurationSettings;

@Component
public class NetApplicationProvider {

    private final NetApplication netApplication;

    public NetApplicationProvider() {

        var settings = Main.getContext().settings();
        var configuration = new NetServerConfigurationSettings(settings);

        this.netApplication = NetApplication.ofServer(configuration);
    }

    @Mapping
    @Component
    public NetApplication getNetApplication() {
        return netApplication;
    }

}
