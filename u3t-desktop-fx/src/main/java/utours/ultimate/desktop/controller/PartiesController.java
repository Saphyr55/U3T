package utours.ultimate.desktop.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.layout.VBox;
import utours.ultimate.desktop.view.PartyButton;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

public class PartiesController implements Initializable {

    @FXML
    private VBox container;

    private Map<String, PartyButton> joinButtons = new HashMap<>();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        container.setAlignment(Pos.TOP_CENTER);
        container.setSpacing(10);
        container.setPadding(new Insets(24));

        PartyButton u3tButton = new PartyButton(container);
        u3tButton.setText("Ultimate Tic Tac Toe");

        joinButtons.put("u3t", u3tButton);
    }

}
