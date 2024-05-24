package utours.ultimate.server;

import utours.ultimate.core.ModuleContext;
import utours.ultimate.core.ComponentAnalyser;
import utours.ultimate.core.provider.AnnotationComponentAnalyser;
import utours.ultimate.core.steorotype.ModuleRegister;
import utours.ultimate.net.NetApplication;


public class Main {

    private static final ModuleContext context = ModuleContext.of(Main.class);

    public static void main(String[] args) {

        context.load();

        NetApplication netApplication = getContext()
                .getContainerReadOnly()
                .getUniqueComponent(NetApplication.class);

        netApplication.start();
    }

    @ModuleRegister
    public static ModuleContext getContext() {
        return context;
    }

}