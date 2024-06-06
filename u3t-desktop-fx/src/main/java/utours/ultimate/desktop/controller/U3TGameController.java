package utours.ultimate.desktop.controller;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.layout.*;
import utours.ultimate.client.ClientGameService;
import utours.ultimate.client.ClientService;
import utours.ultimate.desktop.view.GameGridView;
import utours.ultimate.desktop.view.u3t.PrimitiveTile;
import utours.ultimate.desktop.view.u3t.Tile;
import utours.ultimate.game.model.*;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public final class U3TGameController implements Initializable {

    private final ClientService clientService;
    private final ClientGameService gameService;

    private Game game;

    private @FXML GameGridView gameGridView;
    private @FXML Pane root;

    public U3TGameController(ClientService clientService,
                             ClientGameService gameService) {

        this.clientService = clientService;
        this.gameService = gameService;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        gameService.setClientPlayer(clientService.getCurrentPlayer());

        game = clientService.currentGame();

        gameGridView.prefWidthProperty().bind(root.heightProperty());
        gameGridView.prefHeightProperty().bind(root.heightProperty());

        gameGridView.setHgap(10);
        gameGridView.setVgap(10);

        gameGridView.setOnPressedTile(this::onPressedTile);
        gameGridView.initBoard(gameService.getClientPlayer());

        clientService.setOnChangedGame(game, this::onChangedGame);
    }

    private void onChangedGame(Game game) {
        this.game = game;
        Platform.runLater(this::onLastAction);
    }

    private void onLastAction() {

        Action action = game.lastAction();

        Cell cellOut = gameService.cellAt(game, action.posOut());
        Cell cell = gameService.cellAt(game, action.posOut(), action.posIn());

        IsWinGame isWinGameInner = gameService.checkInnerWinner(game, action);
        game = isWinGameInner.game();

        int outX = action.posOut().x();
        int outY = action.posOut().y();

        Region region = gameGridView.getRegions()[outX][outY];

        if (region instanceof GridPane gridPane) {

            int inX = action.posIn().x();
            int inY = action.posIn().y();

            int linearPos = inX * gameGridView.getGridSize() + inY;

            Node tile = gridPane.getChildren().get(linearPos);

            if (tile instanceof PrimitiveTile primitiveTile) {
                primitiveTile.setCell(cell);
            }

            if (isWinGameInner.isWin()) {
                System.out.println("Is Inner Win");
            }

            boolean isWin = cellOut instanceof Cell.Round ||
                            cellOut instanceof Cell.Cross;

            onWinning(action, cell, isWin);

            if (gameService.checkOuterWinner(game, action.posOut())) {
                finishGame();
            }

        }

    }

    private void onPressedTile(Action action, PrimitiveTile tile) {
        game = gameService.performAction(game, action);
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
