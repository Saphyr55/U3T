package utours.ultimate.desktop.controller;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import utours.ultimate.client.ClientService;
import utours.ultimate.desktop.MainApplication;
import utours.ultimate.desktop.view.DesktopPartiesView;
import utours.ultimate.desktop.view.U3TGameView;
import utours.ultimate.game.model.Game;
import utours.ultimate.net.Client;

import java.net.URL;
import java.util.ResourceBundle;

public class PartiesController implements Initializable {

    public static final int BUTTON_PADDING_SIZE = 30;
    public static final int BUTTON_HEIGHT_FACTOR_SIZE = 13;

    private final Client client;
    private final ClientService clientService;

    private final MainController mainController;

    private @FXML DesktopPartiesView partiesView;
    private @FXML VBox container;

    public PartiesController(MainController mainController,
                             ClientService clientService,
                             Client client) {
        this.client = client;
        this.clientService = clientService;
        this.mainController = mainController;
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

        button.prefWidthProperty()
                .bind(container.widthProperty().subtract(BUTTON_PADDING_SIZE));

        button.prefHeightProperty()
                .bind(container.heightProperty().divide(BUTTON_HEIGHT_FACTOR_SIZE));
    }

    private void onJoinGame(Game game) {
        Platform.runLater(() -> mainController
                .getMainRightPolymorphic()
                .replaceRegion(new U3TGameView()));
    }

}
