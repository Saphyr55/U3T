package utours.ultimate.desktop.view;

import javafx.scene.layout.Pane;
import utours.ultimate.desktop.ViewLoader;

public class DesktopPartiesView extends Pane {

    public DesktopPartiesView() {
        ViewLoader.load(this, "/views/parties.fxml");
    }

}
