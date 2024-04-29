package utours.ultimate.desktop;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import utours.ultimate.core.ContainerReadOnly;
import utours.ultimate.core.ModuleContext;
import utours.ultimate.core.internal.ClassPathXmlModuleProvider;
import utours.ultimate.desktop.view.DesktopMainView;
import utours.ultimate.net.Client;

public class MainApplication extends Application {

    private static final ModuleContext context =
            ModuleContext.createAndLoad(new ClassPathXmlModuleProvider());

    private static final String TITLE = "U3T";
    private static final int WIDTH = 1084;
    private static final int HEIGHT = 628;

    @Override
    public void start(Stage stage) {

        ContainerReadOnly container = getContext().getContainerReadOnly();
        Client client = container.getUniqueComponent(Client.class);

        client.messageSender().send("any.address", "Hello World!", message -> {
            System.out.println("Server respond " + message.content() + " at " + message.address());
        });

        DesktopMainView desktopMainView = new DesktopMainView();
        Scene scene = new Scene(desktopMainView, WIDTH, HEIGHT);
        stage.setTitle(TITLE);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }

    public static ModuleContext getContext() {
        return context;
    }

}