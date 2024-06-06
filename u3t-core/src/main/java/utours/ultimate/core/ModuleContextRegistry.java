package utours.ultimate.core;

import utours.ultimate.core.internal.ModuleContextRegistryImpl;

import java.util.List;

public interface ModuleContextRegistry {

    ModuleContextRegistry defaultInstance = newInstance();

    static ModuleContextRegistry newInstance() {
        return new ModuleContextRegistryImpl();
    }

    static ModuleContextRegistry getDefault() {
        return defaultInstance;
    }

    List<ModuleContext> moduleContexts();

    ModuleContext get(String identifier);

    void register(ModuleContext moduleContext);

    void remove(String identifier);

    void remove(ModuleContext moduleContext);

}
