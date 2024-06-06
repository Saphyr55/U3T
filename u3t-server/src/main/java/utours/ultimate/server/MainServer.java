package utours.ultimate.server;

import utours.ultimate.core.ModuleContext;
import utours.ultimate.core.steorotype.ModuleRegister;
import utours.ultimate.net.NetApplication;


public final class MainServer {

    private static final ModuleContext context =
            ModuleContext.ofContextClass(MainServer.class);

    public static void main(String[] args) {

        context.load();

        NetApplication application = getContext()
                .getContainerReadOnly()
                .getUniqueComponent(NetApplication.class);

        application.start();
    }

    @ModuleRegister
    public static ModuleContext getContext() {
        return context;
    }

}