package utours.ultimate.core.internal;

import utours.ultimate.core.Module;
import utours.ultimate.core.ModuleProvider;

import java.util.Set;

public class AnnotationModuleProvider implements ModuleProvider {

    private final Set<Class<?>> classes;

    public AnnotationModuleProvider(String packageName) {
        classes = ClassProviderInternal.classesOf(packageName);
    }

    public Set<Class<?>> getClasses() {
        return classes;
    }

    @Override
    public Module provideModule() {
        return null;
    }

}
