package utours.ultimate.server;

import utours.ultimate.core.ModuleContext;
import utours.ultimate.core.provider.ClassPathXmlModuleEvaluatorProvider;
import utours.ultimate.net.NetApplication;
import utours.ultimate.net.NetApplicationConfiguration;
import utours.ultimate.net.Context;


public class Main {

    private static final ModuleContext context;
    static {
        context = ModuleContext.createAndLoad(new ClassPathXmlModuleEvaluatorProvider());
    }

    public static void main(String[] args) {
        NetApplicationConfiguration configuration = NetApplicationConfiguration.ofFileProperties();

        NetApplication netApplication = NetApplication.ofServer(configuration);

        netApplication.handler("any.address", Main::treatment);

        netApplication.start();
    }

    static void treatment(Context context) {
        System.out.println(context.message().content());
    }

    public static ModuleContext getContext() {
        return context;
    }

}