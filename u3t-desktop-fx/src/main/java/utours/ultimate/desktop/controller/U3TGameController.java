package utours.ultimate.desktop.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.layout.*;
import utours.ultimate.desktop.view.u3t.PrimitiveTile;
import utours.ultimate.game.feature.U3TGameService;
import utours.ultimate.game.model.Cell;
import utours.ultimate.game.model.Player;
import utours.ultimate.game.model.U3TGame;

import java.net.URL;
import java.util.ResourceBundle;

public class U3TGameController implements Initializable {

    private U3TGameService gameService;
    private U3TGame game;
    private Player currentPlayer;

    @FXML
    private GridPane u3tGrid;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        u3tGrid.setHgap(10);
        u3tGrid.setVgap(10);

        var gridPanes = new GridPane[3][3];

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                var gridPane = new GridPane();
                u3tGrid.add(gridPane, i, j);
                gridPanes[i][j] = gridPane;
            }
        }

        initBoard(gridPanes);
    }

    private void onPressedTile(PrimitiveTile tile) {
        if (gameService.isPlayableCell(tile.getCell())) {
            game = gameService.placeMark(game, currentPlayer, tile.getPosOut(), tile.getPosIn());
            currentPlayer = gameService.oppositePlayer(game, currentPlayer);
            Cell cell = gameService.cellAt(game, tile.getPosOut(), tile.getPosIn());
            tile.setCell(cell);
        }
    }

    private void initBoard(GridPane[][] gridPanes) {
        for (int i = 0; i < gridPanes.length; i++) {
            GridPane[] pane = gridPanes[i];
            for (int j = 0; j < pane.length; j++) {
                GridPane gridPane = pane[j];
                for (int k = 0; k < 3; k++) {
                    for (int l = 0; l < 3; l++) {
                        var tile = PrimitiveTile.newEmptyTile(u3tGrid, Cell.pos(i, j), Cell.pos(k, l));
                        tile.setOnMouseClicked(e -> onPressedTile(tile));
                        gridPane.add(tile, k, l);
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
