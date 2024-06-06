package utours.ultimate.client;

import utours.ultimate.core.ModuleContext;
import utours.ultimate.core.steorotype.ModuleRegister;

public enum ClientContext {

    DEFAULT(ModuleContext.ofContextClass(ClientContext.class));

    private final ModuleContext moduleContext;

    ClientContext(ModuleContext moduleContext) {
        this.moduleContext = moduleContext;
    }

    @ModuleRegister
    public static ModuleContext getContext() {
        return DEFAULT.moduleContext;
    }

}