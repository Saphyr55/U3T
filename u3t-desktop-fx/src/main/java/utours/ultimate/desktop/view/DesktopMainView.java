package utours.ultimate.desktop.view;

import javafx.scene.layout.GridPane;
import utours.ultimate.desktop.ViewLoader;

public class DesktopMainView extends GridPane {

    public DesktopMainView() {
        ViewLoader.load(this, "/views/main.fxml");
    }

}
