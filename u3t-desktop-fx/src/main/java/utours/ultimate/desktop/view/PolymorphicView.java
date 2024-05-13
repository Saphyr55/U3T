package utours.ultimate.desktop.view;

import javafx.scene.layout.Pane;
import utours.ultimate.desktop.ViewLoader;

public class PolymorphicView extends Pane {

    public PolymorphicView() {
        ViewLoader.load(this, "/views/polymorphic.fxml");
    }

}
