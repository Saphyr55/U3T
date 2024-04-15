package utours.ultimate.core.base;

import utours.ultimate.core.Application;
import utours.ultimate.core.ApplicationLauncher;

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
