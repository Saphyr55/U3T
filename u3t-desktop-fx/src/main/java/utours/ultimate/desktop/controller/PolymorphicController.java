package utours.ultimate.desktop.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.Region;
import utours.ultimate.core.steorotype.Component;
import utours.ultimate.desktop.view.PolymorphicView;

import java.net.URL;
import java.util.ResourceBundle;

@Component
public class PolymorphicController implements Initializable {

    @FXML private PolymorphicView root;

    public PolymorphicController() {

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        System.out.println("PolymorphicController Init");
    }

    public void replaceRegion(Region region) {
        root.getChildren().clear();
        root.getChildren().add(region);
        region.prefWidthProperty().bind(root.widthProperty());
        region.prefHeightProperty().bind(root.heightProperty());
    }

}
