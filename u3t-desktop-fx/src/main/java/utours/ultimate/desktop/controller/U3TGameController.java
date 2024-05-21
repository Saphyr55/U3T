package utours.ultimate.desktop.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.layout.*;
import utours.ultimate.desktop.service.impl.DesktopGameLoader;
import utours.ultimate.desktop.view.GameGridView;
import utours.ultimate.desktop.view.u3t.PrimitiveTile;
import utours.ultimate.desktop.view.u3t.Tile;
import utours.ultimate.game.feature.GameActionsProvider;
import utours.ultimate.game.feature.GameLoader;
import utours.ultimate.game.feature.GameService;
import utours.ultimate.game.model.*;
import utours.ultimate.net.Client;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.function.Consumer;

public class U3TGameController implements Initializable {

    private final Client client;
    private final GameService gameService;
    private final GameActionsProvider gameActionsProvider;
    private Game game;

    private @FXML GameGridView gameGridView;
    private @FXML Pane root;

    public U3TGameController(GameService gameService, Game game,
                             GameActionsProvider gameActionsProvider,
                             Client client) {
        this.game = game;
        this.gameService = gameService;
        this.gameActionsProvider = gameActionsProvider;
        this.client = client;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        GameLoader gameLoader = new DesktopGameLoader(gameGridView, this::onPressedTile);

        gameGridView.prefWidthProperty().bind(root.heightProperty());
        gameGridView.prefHeightProperty().bind(root.heightProperty());

        gameGridView.setHgap(10);
        gameGridView.setVgap(10);

        gameGridView.setOnPressedTile(this::onPressedTile);
        gameGridView.initBoard();

        gameLoader.loadGame(gameActionsProvider);
    }


    private void performAction(Action action, Consumer<Cell> acceptNewCell) {

        if (!gameService.isPlayableAction(game, action)) {
            return;
        }

        game = gameService.placeMark(game, action);
        IsWinGame isWinGameInner = gameService.checkInnerWinner(game, action);
        game = isWinGameInner.game();
        game = game.lastAction(action);

        game = gameService.oppositePlayer(game);

        Cell cell = gameService.cellAt(game, action.posOut(), action.posIn());
        acceptNewCell.accept(cell);

        onWinning(action, cell, isWinGameInner.isWin());

        if (gameService.checkOuterWinner(game, action.posOut())) {
            finishGame();
        }

    }

    private void onPressedTile(PrimitiveTile tile) {
        Action action = Action.of(game.currentPlayer(), tile.getPosOut(), tile.getPosIn());
        performAction(action, tile::setCell);
    }

    private void onWinning(Action action, Cell cell, boolean isWin) {
        if (!isWin) return;

        int i = action.posOut().x();
        int j = action.posOut().y();

        var child = gameGridView.getChildren().get(i * GameGridView.GRID_SIZE + j);
        if (child instanceof GridPane) {
            Tile tileOut = Tile.newTile(gameGridView, cell, action.posOut());
            gameGridView.add(tileOut, i, j);
        }
    }


    private void finishGame() {
        try {
            root.getChildren().remove(gameGridView);

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

    /*

    // Just a trace to debug each action.
    private void printActions(PrimitiveTile tile) {

        System.out.printf("Action.of(game.%s, Cell.pos(%d, %d), Cell.pos(%d, %d))\n",
                game.currentPlayer().equals(game.crossPlayer()) ? "crossPlayer()" : "roundPlayer()",
                tile.getPosOut().x(),
                tile.getPosOut().y(),
                tile.getPosIn().x(),
                tile.getPosIn().y()
        );
    }
    */
}
