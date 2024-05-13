package utours.ultimate.desktop.view;

import javafx.scene.control.ScrollPane;
import utours.ultimate.desktop.ViewLoader;

public class DesktopNavigationView extends ScrollPane {

    public DesktopNavigationView() {
        ViewLoader.load(this, "/views/navigation.fxml");
    }

}
