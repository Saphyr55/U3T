package utours.ultimate.desktop.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import utours.ultimate.desktop.view.U3TGameView;

import java.net.URL;
import java.util.ResourceBundle;

public class GameController implements Initializable {

    @FXML
    private Pane gamePane;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public void replaceRegion(Region region) {
        gamePane.getChildren().clear();
        gamePane.getChildren().add(region);
        region.prefWidthProperty().bind(gamePane.widthProperty());
        region.prefHeightProperty().bind(gamePane.heightProperty());
    }

}
