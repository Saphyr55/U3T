package utours.ultimate.core.settings;

import utours.ultimate.core.ComponentAnalyser;
import utours.ultimate.core.provider.AnnotationComponentAnalyser;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.util.UUID;

public class ModuleContextSettings {

    private static final String KEY_MODULE_CONTEXT_ID = "module.context.identifier";
    private static final String KEY_MODULE_COMPONENT_EVALUATOR = "module.context.component-evaluator";

    private final Settings settings = new Settings();

    public ModuleContextSettings(SettingsLoader settingsLoader) {
        settingsLoader.load(settings);
    }

    public Settings getSettings() {
        return settings;
    }

    public String getIdentifier() {
        return settings.getValue(KEY_MODULE_CONTEXT_ID, UUID.randomUUID().toString());
    }

    public ComponentAnalyser getComponentEvaluator() {
        try {

            String className = settings.getValue(
                    KEY_MODULE_COMPONENT_EVALUATOR,
                    AnnotationComponentAnalyser.class.getName()
            );

            Class<?> clazz = Class.forName(className);

            MethodHandles.Lookup lookup = MethodHandles.lookup();
            MethodHandle mh = lookup.findConstructor(clazz, MethodType.methodType(void.class));

            return (ComponentAnalyser) mh.invoke();

        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }


}
