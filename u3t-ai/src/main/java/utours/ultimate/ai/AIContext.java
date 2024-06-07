package utours.ultimate.ai;

import utours.ultimate.core.ModuleContext;
import utours.ultimate.core.steorotype.RegisterModule;

@RegisterModule
public class AIContext {
    
    public static final ModuleContext context =
            ModuleContext.ofContextClass(AIContext.class);

    static {
        System.out.println("Initializing AI Context");
    }

    public static ModuleContext getContext() {
        return context;
    }

}