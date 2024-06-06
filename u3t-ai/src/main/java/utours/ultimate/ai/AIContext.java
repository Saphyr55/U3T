package utours.ultimate.ai;

import utours.ultimate.core.ModuleContext;
import utours.ultimate.core.steorotype.ModuleRegister;

public final class AIContext {

    private static final ModuleContext context =
            ModuleContext.ofContextClass(AIContext.class);

    @ModuleRegister
    public static ModuleContext getContext() {
        return context;
    }

}