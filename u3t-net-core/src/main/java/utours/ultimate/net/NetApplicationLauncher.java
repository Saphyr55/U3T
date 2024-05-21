package utours.ultimate.net;

import utours.ultimate.net.internal.NetApplicationLauncherImpl;

public interface NetApplicationLauncher {

    static NetApplicationLauncher ofApplication(NetApplication netApplication) {
        return new NetApplicationLauncherImpl(netApplication);
    }

    void launch();

}
