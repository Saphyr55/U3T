package utours.ultimate.net;

import utours.ultimate.net.internal.ApplicationLauncherImpl;

public interface ApplicationLauncher {

    static ApplicationLauncher ofApplication(Application application) {
        return new ApplicationLauncherImpl(application);
    }

    void launch();

}
