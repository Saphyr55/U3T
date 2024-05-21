package utours.ultimate.net.internal;

import utours.ultimate.net.NetApplication;
import utours.ultimate.net.NetApplicationLauncher;

public class NetApplicationLauncherImpl implements NetApplicationLauncher {

    private final NetApplication netApplication;

    public NetApplicationLauncherImpl(NetApplication netApplication) {
        this.netApplication = netApplication;
    }

    @Override
    public void launch() {
        netApplication.start();
    }

}
