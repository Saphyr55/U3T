package utours.ultimate.core.internal;

import utours.ultimate.common.MapHelper;
import utours.ultimate.core.ModuleContext;
import utours.ultimate.core.ModuleContextRegistry;

import java.util.List;
import java.util.Map;

public class ModuleContextRegistryImpl implements ModuleContextRegistry {

    private final Map<String, ModuleContext> registry = MapHelper.emptyMap();

    @Override
    public List<ModuleContext> moduleContexts() {
        return registry.values().stream().toList();
    }

    @Override
    public ModuleContext get(String identifier) {
        return registry.get(identifier);
    }

    @Override
    public void register(ModuleContext moduleContext) {
        registry.putIfAbsent(moduleContext.getIdentifier(), moduleContext);
    }

    @Override
    public void remove(String identifier) {
        registry.remove(identifier);
    }

    @Override
    public void remove(ModuleContext moduleContext) {
        remove(moduleContext.getIdentifier());
    }

}
