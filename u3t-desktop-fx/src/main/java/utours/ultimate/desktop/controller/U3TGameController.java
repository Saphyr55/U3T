package utours.ultimate.desktop.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.layout.*;
import utours.ultimate.client.ClientGameService;
import utours.ultimate.client.ClientService;
import utours.ultimate.desktop.service.DesktopGameLoader;
import utours.ultimate.desktop.view.GameGridView;
import utours.ultimate.desktop.view.u3t.PrimitiveTile;
import utours.ultimate.desktop.view.u3t.Tile;
import utours.ultimate.game.feature.GameProvider;
import utours.ultimate.game.feature.GameLoader;
import utours.ultimate.game.feature.GameService;
import utours.ultimate.game.model.*;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.function.Consumer;

public final class U3TGameController implements Initializable {

    private final ClientService clientService;
    private final ClientGameService gameService;

    private Game game;
    private GameLoader gameLoader;

    private @FXML GameGridView gameGridView;
    private @FXML Pane root;

    public U3TGameController(ClientService clientService, ClientGameService gameService) {

        this.clientService = clientService;
        this.gameService = gameService;

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        gameService.setClientPlayer(clientService.getCurrentPlayer());

        game = clientService.currentGame();

        gameLoader = new DesktopGameLoader(gameGridView, this::onPressedTile);

        gameGridView.prefWidthProperty().bind(root.heightProperty());
        gameGridView.prefHeightProperty().bind(root.heightProperty());

        gameGridView.setHgap(10);
        gameGridView.setVgap(10);

        gameGridView.setOnPressedTile(this::onPressedTile);
        gameGridView.initBoard();

        gameLoader.loadGame(game);

        setOnChangedGame();
    }

    private void setOnChangedGame() {
        clientService.onChangedGame(game, g -> {
            game = g;
            gameLoader.loadGame(game);
        });
    }

    private void performAction(Action action, Consumer<Cell> acceptNewCell) {

        if (!gameService.isPlayableAction(game, action)) {
            return;
        }

        game = gameService.placeMark(game, action);
        IsWinGame isWinGameInner = gameService.checkInnerWinner(game, action);
        game = isWinGameInner.game();
        game = game.addAction(action);

        game = gameService.turnPlayer(game);

        Cell cell = gameService.cellAt(game, action.posOut(), action.posIn());
        acceptNewCell.accept(cell);

        onWinning(action, cell, isWinGameInner.isWin());

        if (gameService.checkOuterWinner(game, action.posOut())) {
            finishGame();
        }

    }

    private void onPressedTile(PrimitiveTile tile) {

        Cell.Pos posOut = tile.getPosOut();
        Cell.Pos posIn = tile.getPosIn();

        Action action = Action.of(game.currentPlayer(), posOut, posIn);
        performAction(action, tile::setCell);
    }

    private void onWinning(Action action, Cell cell, boolean isWin) {

        if (!isWin) return;

        int x = action.posOut().x();
        int y = action.posOut().y();

        if (gameGridView.nodeAt(x, y) instanceof GridPane) {

            Cell.Pos posOut = action.posOut();
            Tile tileOut = Tile.newTile(gameGridView, cell, posOut);
            gameGridView.add(tileOut, x, y);
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

}
