package utours.ultimate.net.internal;

import utours.ultimate.net.Application;
import utours.ultimate.net.ApplicationLauncher;

public class ApplicationLauncherImpl implements ApplicationLauncher {

    private final Application application;

    public ApplicationLauncherImpl(Application application) {
        this.application = application;
    }

    @Override
    public void launch() {
        application.start();
    }

}
