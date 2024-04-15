package utours.ultimate.core;

import utours.ultimate.core.base.ApplicationLauncherImpl;

public interface ApplicationLauncher {

    static ApplicationLauncher ofApplication(Application application) {
        return new ApplicationLauncherImpl(application);
    }

    void launch();

}
