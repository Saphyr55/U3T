package utours.ultimate.desktop.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.layout.*;
import utours.ultimate.desktop.view.u3t.PrimitiveTile;
import utours.ultimate.desktop.view.u3t.Tile;
import utours.ultimate.game.feature.GameProvider;
import utours.ultimate.game.feature.GameService;
import utours.ultimate.game.feature.internal.GameAlmostFinishProvider;
import utours.ultimate.game.model.*;
import utours.ultimate.net.Client;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class U3TGameController implements Initializable {

    public static final int GRID_SIZE = 3;

    private final Client client;
    private final GameService gameService;
    private Game game;

    @FXML
    private GridPane u3tGrid;

    @FXML
    private Pane root;

    public U3TGameController(GameService gameService,
                             Game game,
                             Client client) {

        this.gameService = gameService;
        this.game = game;
        this.client = client;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        u3tGrid.prefWidthProperty().bind(root.widthProperty());
        u3tGrid.prefHeightProperty().bind(root.widthProperty());
        u3tGrid.setHgap(10);
        u3tGrid.setVgap(10);

        GameProvider gameProvider = new GameAlmostFinishProvider(game);

        Region[][] gridPanes = new Region[GRID_SIZE][GRID_SIZE];

        for (int i = 0; i < GRID_SIZE; i++) {
            for (int j = 0; j < GRID_SIZE; j++) {
                var gridPane = new GridPane();
                u3tGrid.add(gridPane, i, j);
                gridPanes[i][j] = gridPane;
            }
        }

        initBoard(gridPanes);
        loadGame(gameProvider, gridPanes);
    }

    private void loadGame(GameProvider gameProvider, Region[][] gridPanes) {
        for (Action action : gameProvider.actions()) {
            Region region = gridPanes[action.posOut().x()][action.posOut().y()];
            if (region instanceof GridPane gridPane) {
                var tile = gridPane.getChildren().get(action.posIn().x() * GRID_SIZE + action.posIn().y());
                if (tile instanceof PrimitiveTile primitiveTile) {
                    onPressedTile(primitiveTile);
                }
            }
        }
    }

    private void onPressedTile(PrimitiveTile tile) {

        Action action = new Action(game.currentPlayer(), tile.getPosOut(), tile.getPosIn());

        if (gameService.isPlayableAction(game, action)) {

            game = gameService.placeMark(game, action);
            IsWinGame isWinGameInner = gameService.checkInnerWinner(game, action);
            game = isWinGameInner.game();
            game = game.lastAction(action);

            game = gameService.oppositePlayer(game);

            Cell cell = gameService.cellAt(game, action.posOut(), action.posIn());

            tile.setCell(cell);

            onWinning(action, cell, isWinGameInner.isWin());

            // severing();

            if (gameService.checkOuterWinner(game, action.posOut())) {
                finishGame();
            }

        }

    }

    private void severing() {
        client.messageSender().send("u3t.game.%d".formatted(game.gameID()), game, message -> {
            if (!message.isSuccess()) {
                throw new IllegalStateException("Unexpected error: " + message.content());
            }
        });
    }

    private void onWinning(Action action, Cell cell, boolean isWin) {
        if (isWin) {
            int i = action.posOut().x();
            int j = action.posOut().y();
            var child = u3tGrid.getChildren().get(i * GRID_SIZE + j);
            if (child instanceof GridPane) {
                Tile tileOut = Tile.newTile(u3tGrid, cell, action.posOut());
                u3tGrid.add(tileOut, i, j);
            }
        }
    }

    private void initBoard(Region[][] regions) {
        for (int i = 0; i < regions.length; i++) {
            Region[] pane = regions[i];
            for (int j = 0; j < pane.length; j++) {
                Region region = pane[j];
                for (int k = 0; k < GRID_SIZE; k++) {
                    for (int l = 0; l < GRID_SIZE; l++) {
                        if (region instanceof GridPane gridPane) {
                            var tile = Tile.newEmptyPrimitiveTile(u3tGrid, Cell.pos(i, j), Cell.pos(k, l));
                            tile.setOnMouseClicked(e -> onPressedTile(tile));
                            gridPane.add(tile, k, l);
                        }
                    }
                }
            }
        }
    }

    private void finishGame() {
        try {
            root.getChildren().remove(u3tGrid);

            Player wonPlayer = gameService.oppositePlayer(game, game.currentPlayer());
            U3TGameWonController controller = new U3TGameWonController(wonPlayer);

            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/views/u3t-game-won.fxml"));
            loader.setController(controller);

            Parent parent = loader.load();

            root.getChildren().add(parent);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void printActions(PrimitiveTile tile) {
        System.out.printf("Action.of(game.%s, Cell.pos(%d, %d), Cell.pos(%d, %d))\n",
                game.currentPlayer().equals(game.crossPlayer()) ? "crossPlayer()" : "roundPlayer()",
                tile.getPosOut().x(),
                tile.getPosOut().y(),
                tile.getPosIn().x(),
                tile.getPosIn().y()
        );
    }

}
