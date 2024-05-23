package utours.ultimate.client;

import utours.ultimate.core.ModuleContext;
import utours.ultimate.core.ModuleEvaluatorProvider;
import utours.ultimate.core.provider.AnnotationModuleEvaluatorProvider;
import utours.ultimate.core.steorotype.ModuleRegister;

import java.util.List;

public enum ClientContext {
    DEFAULT(ModuleContext.of("u3t-client", getModuleEvaluator()));

    private final ModuleContext moduleContext;

    ClientContext(ModuleContext moduleContext) {
        this.moduleContext = moduleContext;
    }

    @ModuleRegister
    public static ModuleContext getContext() {
        return DEFAULT.moduleContext;
    }

    private static ModuleEvaluatorProvider getModuleEvaluator() {
        return new AnnotationModuleEvaluatorProvider(getPackageNames());
    }

    private static List<String> getPackageNames() {
        return List.of(
                "utours.ultimate.client",
                "utours.ultimate.client.impl"
        );
    }

}