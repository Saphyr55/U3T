package utours.ultimate.desktop.view;

import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import utours.ultimate.desktop.ViewLoader;

public class PolymorphicView extends Pane {

    public PolymorphicView() {
        ViewLoader.load(this, "/views/polymorphic.fxml");
    }

    public void replaceRegion(Region region) {
        getChildren().clear();
        getChildren().add(region);
        region.prefWidthProperty().bind(widthProperty());
        region.prefHeightProperty().bind(heightProperty());
    }

}
