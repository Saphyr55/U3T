package utours.ultimate.desktop.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.Pane;
import utours.ultimate.desktop.view.U3TGameView;

import java.net.URL;
import java.util.ResourceBundle;

public class GameController implements Initializable {

    @FXML
    private Pane gamePane;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        
        U3TGameView gameView = new U3TGameView();

        gamePane.getChildren().clear();
        gamePane.getChildren().add(gameView);

        gameView.prefWidthProperty().bind(gamePane.widthProperty());

        gameView.prefHeightProperty().bind(gamePane.heightProperty());
    }



}
