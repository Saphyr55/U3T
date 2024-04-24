package utours.ultimate.desktop.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.*;
import utours.ultimate.desktop.view.u3t.PrimitiveTile;
import utours.ultimate.desktop.view.u3t.Tile;
import utours.ultimate.game.feature.U3TGameService;
import utours.ultimate.game.model.Action;
import utours.ultimate.game.model.Cell;
import utours.ultimate.game.model.Player;
import utours.ultimate.game.model.U3TGame;

import java.net.URL;
import java.util.ResourceBundle;

public class U3TGameController implements Initializable {

    public static final int GRID_SIZE = 3;

    private U3TGameService gameService;
    private U3TGame game;
    private Player currentPlayer;

    @FXML
    private GridPane u3tGrid;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        u3tGrid.setHgap(10);
        u3tGrid.setVgap(10);

        var gridPanes = new Region[GRID_SIZE][GRID_SIZE];

        for (int i = 0; i < GRID_SIZE; i++) {
            for (int j = 0; j < GRID_SIZE; j++) {
                var gridPane = new GridPane();
                u3tGrid.add(gridPane, i, j);
                gridPanes[i][j] = gridPane;
            }
        }

        initBoard(gridPanes);
    }

    private void onPressedTile(PrimitiveTile tile) {

        var action = new Action(currentPlayer, tile.getPosOut(), tile.getPosIn());
        if (gameService.isPlayableAction(game, action)) {

            game = gameService.placeMark(game, action);
            var winGame = gameService.checkInnerWinner(game, action);
            game = winGame.game();
            game = game.lastAction(action);

            currentPlayer = gameService.oppositePlayer(game, currentPlayer);
            Cell cell = gameService.cellAt(game, action.posOut(), action.posIn());

            tile.setCell(cell);

            if (winGame.isWin()) {
                int i = action.posOut().x();
                int j = action.posOut().y();
                Tile tileOut = Tile.newTile(u3tGrid, cell, action.posOut());
                u3tGrid.getChildren().set(i * GRID_SIZE + j, tileOut);
            }

        }
    }

    private void initBoard(Region[][] gridPanes) {
        for (int i = 0; i < gridPanes.length; i++) {
            Region[] pane = gridPanes[i];
            for (int j = 0; j < pane.length; j++) {
                Region region = pane[j];
                for (int k = 0; k < GRID_SIZE; k++) {
                    for (int l = 0; l < GRID_SIZE; l++) {
                        var tile = Tile.newEmptyPrimitiveTile(u3tGrid, Cell.pos(i, j), Cell.pos(k, l));
                        tile.setOnMouseClicked(e -> onPressedTile(tile));
                        if (region instanceof GridPane gridPane) {
                            gridPane.add(tile, k, l);
                        }
                    }
                }
            }
        }
    }

    public void setGame(U3TGame game) {
        this.game = game;
        this.currentPlayer = game.crossPlayer();
    }

    public void setGameService(U3TGameService gameService) {
        this.gameService = gameService;
    }

}
