package utours.ultimate.desktop.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import utours.ultimate.desktop.view.u3t.PrimitiveTile;
import utours.ultimate.game.feature.U3TGameService;
import utours.ultimate.game.model.U3TGame;

import java.net.URL;
import java.util.ResourceBundle;

public class U3TGameController implements Initializable {

    private U3TGameService gameService;
    private U3TGame game;

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

        initBoard(gridPanes, game);

    }

    private void onPressedTile(MouseEvent mouseEvent, PrimitiveTile tile) {

    }

    private void initBoard(GridPane[][] gridPanes, U3TGame game) {
        for (GridPane[] pane : gridPanes) {
            for (GridPane gridPane : pane) {

                for (int k = 0; k < 3; k++) {
                    for (int l = 0; l < 3; l++) {
                        var tile = PrimitiveTile.newEmptyTile(u3tGrid);
                        tile.setOnMouseClicked(mouseEvent -> onPressedTile(mouseEvent, tile));
                        gridPane.add(tile, k, l);
                    }
                }

            }
        }
    }

    public void setGame(U3TGame game) {
        this.game = game;
    }

    public void setGameService(U3TGameService gameService) {
        this.gameService = gameService;
    }

}
