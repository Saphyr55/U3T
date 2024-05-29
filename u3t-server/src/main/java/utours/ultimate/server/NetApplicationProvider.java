package utours.ultimate.server;

import utours.ultimate.core.settings.Settings;
import utours.ultimate.core.steorotype.Component;
import utours.ultimate.core.steorotype.Mapping;
import utours.ultimate.net.NetApplication;
import utours.ultimate.net.NetServerConfiguration;

@Component
public class NetApplicationProvider {

    private final NetApplication application;

    public NetApplicationProvider() {

        Settings settings = MainServer.getContext().getSettings();
        NetServerConfiguration configuration = NetServerConfiguration.ofSettings(settings);

        application = NetApplication.serverOf(configuration);
    }

    @Mapping
    @Component
    public NetApplication getApplication() {
        return application;
    }

}
