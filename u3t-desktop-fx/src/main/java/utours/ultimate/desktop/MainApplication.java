package utours.ultimate.desktop;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.stage.Stage;
import utours.ultimate.client.ClientContext;
import utours.ultimate.core.ModuleContext;
import utours.ultimate.core.steorotype.ModuleRegister;
import utours.ultimate.desktop.view.DesktopMainView;

public final class MainApplication extends Application {


    public static final String MODULE_IDENTIFIER = "u3t-desktop-fx";
    private static ModuleContext context;

    private static Thread fxThread;

    private static final String TITLE = "Software";
    private static final int WIDTH = 1084;
    private static final int HEIGHT = 628;

    @Override
    public void start(Stage stage) {

        Platform.runLater(() -> {
            // fxThread is the JavaFX Application Thread after this call
            fxThread = Thread.currentThread();
        });

        DesktopMainView desktopMainView = new DesktopMainView();
        Scene scene = new Scene(desktopMainView, WIDTH, HEIGHT);
        stage.setTitle(TITLE);
        stage.setScene(scene);
        stage.show();
    }

    public static Thread getFxThread() {
        return fxThread;
    }

    public static void main(String[] args) {

        context = ModuleContext.of(MainApplication.class);
        context.mergeModule(ClientContext.getContext());
        context.load();

        launch(args);
    }

    @ModuleRegister
    public static ModuleContext getContext() {
        return context;
    }

}