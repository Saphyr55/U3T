package utours.ultimate.desktop;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.stage.Stage;
import utours.ultimate.core.ReadOnlyContainer;
import utours.ultimate.core.ModuleContext;
import utours.ultimate.core.ModuleEvaluatorProvider;
import utours.ultimate.core.provider.AnnotationModuleEvaluatorProvider;
import utours.ultimate.desktop.view.DesktopMainView;
import utours.ultimate.net.Client;

public class MainApplication extends Application {

    private static ModuleContext context;

    private static final String TITLE = "U3T";
    private static final int WIDTH = 1084;
    private static final int HEIGHT = 628;

    @Override
    public void start(Stage stage) {
        DesktopMainView desktopMainView = new DesktopMainView();
        Scene scene = new Scene(desktopMainView, WIDTH, HEIGHT);
        stage.setTitle(TITLE);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        context = ModuleContext.createAndLoad(getModuleProvider());
        launch(args);
    }

    public static ModuleContext getContext() {
        return context;
    }

    private static ModuleEvaluatorProvider getModuleProvider() {
        return new AnnotationModuleEvaluatorProvider(
                "utours.ultimate.desktop",
                "utours.ultimate.desktop.action",
                "utours.ultimate.desktop.controller",
                "utours.ultimate.desktop.factory",
                "utours.ultimate.desktop.factory.impl",
                "utours.ultimate.desktop.service",
                "utours.ultimate.desktop.service.impl",
                "utours.ultimate.desktop.view",
                "utours.ultimate.desktop.view.u3t"
        );
    }

}