package utours.ultimate.client;

import utours.ultimate.core.ModuleContext;
import utours.ultimate.core.ComponentAnalyser;
import utours.ultimate.core.provider.AnnotationComponentAnalyser;
import utours.ultimate.core.steorotype.ModuleRegister;

import java.util.List;

public enum ClientContext {

    DEFAULT(ModuleContext.of(ClientContext.class));

    private final ModuleContext moduleContext;

    ClientContext(ModuleContext moduleContext) {
        this.moduleContext = moduleContext;
    }

    @ModuleRegister
    public static ModuleContext getContext() {
        return DEFAULT.moduleContext;
    }

}