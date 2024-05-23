package utours.ultimate.server;

import utours.ultimate.core.ModuleContext;
import utours.ultimate.core.ModuleContextRegistry;
import utours.ultimate.core.ModuleEvaluatorProvider;
import utours.ultimate.core.provider.AnnotationModuleEvaluatorProvider;
import utours.ultimate.core.settings.ClassPathSettingsLoader;
import utours.ultimate.core.steorotype.ModuleRegister;
import utours.ultimate.net.NetApplication;


public class Main {

    private static ModuleContext context;

    public static void main(String[] args) {

        context = ModuleContext.of("u3t-server", getEvalutorProvider(), new ClassPathSettingsLoader());

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

    private static ModuleEvaluatorProvider getEvalutorProvider() {
        return new AnnotationModuleEvaluatorProvider(
                "utours.ultimate.server",
                "utours.ultimate.server.handlers",
                "utours.ultimate.server.internal"
        );
    }

}