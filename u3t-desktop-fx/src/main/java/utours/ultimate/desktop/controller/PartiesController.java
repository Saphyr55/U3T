package utours.ultimate.desktop.controller;

import javafx.application.Platform;
import javafx.beans.binding.DoubleBinding;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import utours.ultimate.client.AsyncPendingGameInventory;
import utours.ultimate.client.ClientService;
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
    private final AsyncPendingGameInventory pendingGameInventory;

    private @FXML VBox container;

    private Button addGameButton;

    public PartiesController(MainController mainController,
                             ClientService clientService,
                             AsyncPendingGameInventory pendingGameInventory) {
        
        this.clientService = clientService;
        this.mainController = mainController;
        this.pendingGameInventory = pendingGameInventory;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        initContainer();

        addGameButton = createButton();
        addGameButton.setText("Create a game");
        addGameButton.setOnMouseClicked(this::onCreatePendingGame);

        container.getChildren().add(addGameButton);

        pendingGameInventory.findAll()
                .thenAccept(this::addPendingGamesButtons)
                .thenRun(this::setupObservableInventory);
    }

    private void setupObservableInventory() {
        clientService.onChangedPendingGames(this::addPendingGamesButtons);
    }

    private void onCreatePendingGame(MouseEvent mouseEvent) {
        pendingGameInventory.add(PendingGame.ofDefault());
    }

    private void addPendingGamesButtons(List<PendingGame> pendingGames) {

        Platform.runLater(this::reset);
        pendingGames.forEach(this::addPendingGameButton);
    }

    private void addPendingGameButton(PendingGame pendingGame) {

        Button button = createButton();
        button.setText(textOfPendingGame(pendingGame));
        button.setOnMouseClicked(mouseEvent -> joinPendingGame(pendingGame));

        Platform.runLater(() -> container.getChildren().add(button));
    }

    private static String textOfPendingGame(PendingGame pendingGame) {
        return "%d/2 UTTT #%s".formatted(
                pendingGame.totalPlayer(),
                pendingGame.gameID());
    }

    private void initContainer() {

        container.setAlignment(Pos.TOP_CENTER);
        container.setSpacing(10);
        container.setPadding(new Insets(24));
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

    private void joinPendingGame(PendingGame pendingGame) {
        clientService.joinPendingGame(pendingGame, this::joinGame);
    }

    private void joinGame(Game game) {
        Platform.runLater(() -> mainController
                .getMainRightPolymorphic()
                .replaceRegion(createU3TGameView()));
    }

    private U3TGameView createU3TGameView() {
        return new U3TGameView();
    }

}
