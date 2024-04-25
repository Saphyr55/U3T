package utours.ultimate.desktop;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import utours.ultimate.core.ApplicationContext;
import utours.ultimate.core.ModularApplicationContext;
import utours.ultimate.core.internal.ContainerImpl;
import utours.ultimate.desktop.view.Views;

public class MainApplication extends Application {

    private static final String TITLE = "U3T";
    private static final int WIDTH = 1084;
    private static final int HEIGHT = 628;

    @Override
    public void start(Stage stage) throws Exception {

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(Views.MAIN_FXML));
        Parent parent = fxmlLoader.load();

        Scene scene = new Scene(parent, WIDTH, HEIGHT);

        stage.setTitle(TITLE);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }

}