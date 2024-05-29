package utours.ultimate.desktop.controller;

import javafx.application.Platform;
import javafx.beans.binding.DoubleBinding;
import javafx.collections.ListChangeListener;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import utours.ultimate.client.AsyncPendingGameInventory;
import utours.ultimate.client.ClientService;
import utours.ultimate.desktop.view.DesktopPartiesView;
import utours.ultimate.desktop.view.U3TGameView;
import utours.ultimate.game.model.Game;
import utours.ultimate.game.model.PendingGame;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public final class PartiesController implements Initializable {

    public static final int BUTTON_PADDING_SIZE = 30;
    public static final int BUTTON_HEIGHT_FACTOR_SIZE = 13;

    private final ClientService clientService;
    private final MainController mainController;
    private final AsyncPendingGameInventory asyncPendingGameInventory;

    private @FXML DesktopPartiesView partiesView;
    private @FXML VBox container;
    private Button addGameButton;

    public PartiesController(MainController mainController,
                             ClientService clientService,
                             AsyncPendingGameInventory asyncPendingGameInventory) {

        this.clientService = clientService;
        this.mainController = mainController;
        this.asyncPendingGameInventory = asyncPendingGameInventory;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        container.setAlignment(Pos.TOP_CENTER);
        container.setSpacing(10);
        container.setPadding(new Insets(24));

        addGameButton = createButton();
        addGameButton.setText("Create a game");
        addGameButton.setOnMouseClicked(this::onCreatePendingGame);
        container.getChildren().add(addGameButton);

        asyncPendingGameInventory.findAll()
                .thenAccept(this::addPendingGamesButtons)
                .thenRun(this::setupObservableInventory);
    }

    private void setupObservableInventory() {
        clientService.onChangedPendingGames(this::addPendingGamesButtons);
    }

    private void onCreatePendingGame(MouseEvent mouseEvent) {
        asyncPendingGameInventory.add(PendingGame.builder().build());
    }

    private void addPendingGamesButtons(List<PendingGame> pendingGames) {

        Platform.runLater(this::reset);
        pendingGames.forEach(this::addPendingGameButton);
    }

    private void addPendingGameButton(PendingGame pendingGame) {

        Button button = createButton();
        button.setText("UTTT #%s".formatted(String.valueOf(pendingGame.gameID())));
        button.setOnMouseClicked(mouseEvent -> {
            clientService.joinGame(this::onJoinGame);
        });

        Platform.runLater(() -> container.getChildren().add(button));
    }

    private void reset() {

        container.getChildren().clear();
        container.getChildren().add(addGameButton);
    }

    private Button createButton() {

        Button button = new Button();

        DoubleBinding widthProperty = container
                .widthProperty()
                .subtract(BUTTON_PADDING_SIZE);

        button.prefWidthProperty().bind(widthProperty);
        button.minWidth(widthProperty.get());

        DoubleBinding heightProperty = container
                .heightProperty()
                .divide(BUTTON_HEIGHT_FACTOR_SIZE);

        button.prefHeightProperty().bind(heightProperty);
        button.minHeight(heightProperty.get());

        return button;
    }

    private void onJoinGame(Game game) {
        Platform.runLater(() -> mainController
                .getMainRightPolymorphic()
                .replaceRegion(new U3TGameView()));
    }

}
