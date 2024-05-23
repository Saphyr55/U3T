package utours.ultimate.core;

import utours.ultimate.core.internal.ModuleContextRegistryImpl;

public interface ModuleContextRegistry {

    ModuleContextRegistry defaultInstance = newInstance();

    static ModuleContextRegistry newInstance() {
        return new ModuleContextRegistryImpl();
    }

    static ModuleContextRegistry getDefault() {
        return defaultInstance;
    }

    ModuleContext get(String identifier);

    void register(ModuleContext moduleContext);

    void remove(String identifier);

    void remove(ModuleContext moduleContext);

}
