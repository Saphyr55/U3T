package utours.ultimate.desktop.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import utours.ultimate.game.model.Player;

import java.net.URL;
import java.util.ResourceBundle;

public final class U3TGameWonController implements Initializable {

    private final Player player;

    private @FXML Button resetButton;
    private @FXML Label winnerLabel;

    public U3TGameWonController(Player player) {
        this.player = player;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

}
