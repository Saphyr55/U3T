package utours.ultimate.desktop.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;
import utours.ultimate.desktop.view.NavButton;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

public class NavigationController implements Initializable {

    @FXML
    private ScrollPane nav;

    private VBox container;

    private Map<String, NavButton> navButtons = new HashMap<>();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        container = new VBox();
        container.setSpacing(10);
        container.setPadding(new Insets(24));
        container.setAlignment(Pos.CENTER);

        var partiesButton = new NavButton(container);
        var chatButton = new NavButton(container);
        var historyButton = new NavButton(container);
        var quitButton = new NavButton(container);

        historyButton.setText("History");
        partiesButton.setText("Parties");
        chatButton.setText("Chat");
        quitButton.setText("Quit");

        navButtons.put("Parties", partiesButton);
        navButtons.put("Chat", chatButton);
        navButtons.put("Quit", quitButton);
        navButtons.put("History", partiesButton);

        nav.setContent(container);
    }

}
