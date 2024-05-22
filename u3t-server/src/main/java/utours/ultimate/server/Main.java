package utours.ultimate.server;

import utours.ultimate.core.ModuleContext;
import utours.ultimate.core.ModuleEvaluatorProvider;
import utours.ultimate.core.provider.AnnotationModuleEvaluatorProvider;
import utours.ultimate.core.provider.ClassPathXmlModuleEvaluatorProvider;
import utours.ultimate.net.NetApplication;
import utours.ultimate.net.NetApplicationConfiguration;
import utours.ultimate.net.Context;


public class Main {

    private static final ModuleContext context;
    static {
        context = ModuleContext.createAndLoad(getEvalutorProvider());
    }

    private static ModuleEvaluatorProvider getEvalutorProvider() {
        return new AnnotationModuleEvaluatorProvider(
                "utours.ultimate.server",
                "utours.ultimate.server.handlers",
                "utours.ultimate.server.internal"
        );
    }

    public static void main(String[] args) {

        NetApplication netApplication = getContext()
                .getContainerReadOnly()
                .getUniqueComponent(NetApplication.class);

        netApplication.start();
    }

    public static ModuleContext getContext() {
        return context;
    }

}