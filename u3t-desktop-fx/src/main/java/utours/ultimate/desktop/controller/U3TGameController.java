package utours.ultimate.desktop.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.layout.*;
import utours.ultimate.desktop.view.u3t.PrimitiveTile;
import utours.ultimate.desktop.view.u3t.Tile;
import utours.ultimate.game.feature.U3TGameProvider;
import utours.ultimate.game.feature.U3TGameService;
import utours.ultimate.game.feature.internal.U3TGameAlmostFinishProvider;
import utours.ultimate.game.model.*;
import utours.ultimate.net.Client;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class U3TGameController implements Initializable {

    public static final int GRID_SIZE = 3;

    private final Client client;
    private final U3TGameService gameService;
    private Player currentPlayer;
    private U3TGame game;

    @FXML
    private GridPane u3tGrid;

    @FXML
    private Pane root;

    public U3TGameController(U3TGameService gameService,
                             U3TGame game,
                             Player currentPlayer,
                             Client client) {

        this.gameService = gameService;
        this.game = game;
        this.currentPlayer = currentPlayer;
        this.client = client;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        u3tGrid.setHgap(10);
        u3tGrid.setVgap(10);

        U3TGameProvider gameProvider = new U3TGameAlmostFinishProvider(game);

        var gridPanes = new Region[GRID_SIZE][GRID_SIZE];

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

    private void loadGame(U3TGameProvider gameProvider, Region[][] gridPanes) {
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

        Action action = new Action(currentPlayer, tile.getPosOut(), tile.getPosIn());

        if (gameService.isPlayableAction(game, action)) {

            System.out.printf("Action.of(game.%s, Cell.pos(%s, %s), Cell.pos(%s, %s))\n",
                    currentPlayer.equals(game.crossPlayer()) ? "crossPlayer()" : "roundPlayer()",
                    String.valueOf(tile.getPosOut().x()),
                    String.valueOf(tile.getPosOut().y()),
                    String.valueOf(tile.getPosIn().x()),
                    String.valueOf(tile.getPosIn().y())
            );

            game = gameService.placeMark(game, action);
            IsWinGame isWinGameInner = gameService.checkInnerWinner(game, action);
            game = isWinGameInner.game();
            game = game.lastAction(action);

            currentPlayer = gameService.oppositePlayer(game, currentPlayer);
            Cell cell = gameService.cellAt(game, action.posOut(), action.posIn());

            tile.setCell(cell);

            onWinning(isWinGameInner, action, cell);

            if (gameService.checkOuterWinner(game, action.posOut())) {
                finishGame();
            }

        }
    }

    private void onWinning(IsWinGame isWinGameInner, Action action, Cell cell) {
        if (isWinGameInner.isWin()) {
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

            Player wonPlayer = gameService.oppositePlayer(game, currentPlayer);
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

}
