package utours.ultimate.desktop.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import utours.ultimate.core.steorotype.Component;
import utours.ultimate.desktop.service.ClientService;
import utours.ultimate.desktop.view.DesktopPartiesView;
import utours.ultimate.game.model.Game;
import utours.ultimate.net.Client;

import java.net.URL;
import java.util.ResourceBundle;

@Component
public class PartiesController implements Initializable {

    public static final int BUTTON_PADDING_SIZE = 30;
    public static final int BUTTON_HEIGHT_FACTOR_SIZE = 13;

    private final Client client;
    private final ClientService clientService;

    @FXML private DesktopPartiesView partiesView;
    @FXML private VBox container;

    public PartiesController(ClientService clientService, Client client) {
        this.client = client;
        this.clientService = clientService;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        container.setAlignment(Pos.TOP_CENTER);
        container.setSpacing(10);
        container.setPadding(new Insets(24));

        Button button = new Button();
        button.setText("Ultimate Tic Tac Toe");
        button.setOnMouseClicked(mouseEvent -> clientService.joinGame(client, this::onJoinGame));

        container.getChildren().add(button);
        button.prefWidthProperty().bind(container.widthProperty().subtract(BUTTON_PADDING_SIZE));
        button.prefHeightProperty().bind(container.heightProperty().divide(BUTTON_HEIGHT_FACTOR_SIZE));
    }

    private void onJoinGame(Game game) {

    }

}
