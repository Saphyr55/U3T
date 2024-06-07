package utours.ultimate.core.processing;

import com.google.auto.service.AutoService;
import utours.ultimate.core.steorotype.RegisterModule;

import javax.annotation.processing.*;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.TypeElement;
import javax.tools.Diagnostic;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Set;

@AutoService(Processor.class)
public final class ModuleAnnotationProcessing extends AbstractProcessor {

    public record ClassEntry(String className, String simpleName, String packageName) { }

    private static final String MODULE_LOADER_FILE_NAME = "ModuleLoaderInternal";

    private Messager messager;

    @Override
    public synchronized void init(ProcessingEnvironment processingEnv) {
        super.init(processingEnv);
        this.messager = processingEnv.getMessager();
    }

    @Override
    public Set<String> getSupportedAnnotationTypes() {
        return Set.of(); // RegisterModule.class.getCanonicalName());
    }

    @Override
    public SourceVersion getSupportedSourceVersion() {
        return SourceVersion.RELEASE_21;
    }

    @Override
    public boolean process(Set<? extends TypeElement> annotations,
                           RoundEnvironment roundEnv) {

        var classNames = roundEnv.getElementsAnnotatedWith(RegisterModule.class).stream()
                .filter(el -> el.getKind() == ElementKind.CLASS)
                .map(el -> {

                    String simpleClassName = el.getSimpleName().toString();
                    String packageName = el.getEnclosingElement().toString();
                    String className = packageName + "." + simpleClassName;
                    messager.printMessage(Diagnostic.Kind.NOTE, className);

                    return new ClassEntry(className, simpleClassName, packageName);
                }).toList();

        generateModuleLoaderFile(classNames);

        return true;
    }

    private void generateModuleLoaderFile(List<ClassEntry> classNames) {

        for (ClassEntry entry : classNames) {

            String className = MODULE_LOADER_FILE_NAME;

            try (PrintWriter out = new PrintWriter(processingEnv.getFiler()
                    .createSourceFile(className).openWriter())){

                out.println("""
                package utours.ultimate.core.internal;
                
                public final class %s {

                    static {
                        try {
                            Class.forName("%s");
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                }
                """.formatted(className, entry.className()));

            } catch (IOException e) {
                e.printStackTrace();
            }

        }


    }

}
