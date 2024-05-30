package utours.ultimate.desktop;

import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import utours.ultimate.client.ClientContext;
import utours.ultimate.core.ModuleContext;
import utours.ultimate.core.steorotype.ModuleRegister;
import utours.ultimate.desktop.view.DesktopMainView;

public final class MainApplication extends Application {

    private static ModuleContext context;

    private static final String TITLE = "Software";
    private static final int WIDTH = 1084;
    private static final int HEIGHT = 628;

    @Override
    public void start(Stage stage) {

        Parent root = new DesktopMainView();
        Scene scene = new Scene(root, WIDTH, HEIGHT);
        stage.setTitle(TITLE);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {

        context = ModuleContext.ofContextClass(MainApplication.class);
        context.mergeModule(ClientContext.getContext());
        context.load();

        launch(args);
    }

    @ModuleRegister
    public static ModuleContext getContext() {
        return context;
    }

}