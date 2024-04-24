package utours.ultimate.desktop.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.Pane;
import utours.ultimate.desktop.view.Views;
import utours.ultimate.game.feature.internal.U3TGameFinishProvider;

import java.net.URL;
import java.util.ResourceBundle;

public class GameController implements Initializable {

    @FXML
    private Pane gamePane;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Pane pane = Views.loadU3TGamePane();

        gamePane.getChildren().clear();
        gamePane.getChildren().add(pane);

        pane.prefWidthProperty().bind(gamePane.widthProperty());

        pane.prefHeightProperty().bind(gamePane.heightProperty());
    }



}
