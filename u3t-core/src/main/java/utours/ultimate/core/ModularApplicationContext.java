package utours.ultimate.core;

import utours.ultimate.core.internal.ContainerImpl;

public class ModularApplicationContext implements ApplicationContext {

    private final ModuleAnalyzer moduleAnalyzer;
    private Container container;

    public ModularApplicationContext(ModuleAnalyzer moduleAnalyzer) {
        this.moduleAnalyzer = moduleAnalyzer;
        this.container = new ContainerImpl(null);
    }

    @Override
    public ContainerReadOnly getContainerReadOnly() {
        moduleAnalyzer.analyse();
        return container;
    }

}
