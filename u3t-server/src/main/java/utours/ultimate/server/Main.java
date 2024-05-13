package utours.ultimate.server;

import utours.ultimate.core.ModuleContext;
import utours.ultimate.core.provider.ClassPathXmlModuleEvaluatorProvider;
import utours.ultimate.net.Application;
import utours.ultimate.net.ApplicationConfiguration;
import utours.ultimate.net.Context;


public class Main {

    private static final ModuleContext context;
    static {
        context = ModuleContext.createAndLoad(new ClassPathXmlModuleEvaluatorProvider());
    }

    public static void main(String[] args) {
        ApplicationConfiguration configuration = ApplicationConfiguration.ofFileProperties();

        Application application = Application.ofServer(configuration);

        application.handler("any.address", Main::treatment);

        application.start();
    }

    static void treatment(Context context) {
        System.out.println(context.message().content());
    }

    public static ModuleContext getContext() {
        return context;
    }

}