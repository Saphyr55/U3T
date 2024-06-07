package utours.ultimate.client;

import utours.ultimate.core.ModuleContext;
import utours.ultimate.core.steorotype.RegisterModule;

@RegisterModule
public enum ClientContext {

    DEFAULT(ModuleContext.ofContextClass(ClientContext.class));

    private final ModuleContext moduleContext;

    ClientContext(ModuleContext moduleContext) {
        this.moduleContext = moduleContext;
    }

    public static ModuleContext getContext() {
        return DEFAULT.moduleContext;
    }

}