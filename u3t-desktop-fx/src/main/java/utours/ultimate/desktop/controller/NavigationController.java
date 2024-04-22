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

        NavButton u3tButton = new NavButton(0, container);
        NavButton chat = new NavButton(1, container);
        NavButton quit = new NavButton(2, container);

        u3tButton.setText("U3T");
        chat.setText("Chat");
        quit.setText("Quit");

        navButtons.put("U3T", u3tButton);
        navButtons.put("Chat", chat);
        navButtons.put("Quit", quit);

        nav.setContent(container);
    }

}
