package utours.ultimate.desktop.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import utours.ultimate.desktop.view.u3t.PrimitiveTile;
import utours.ultimate.game.model.Board;
import utours.ultimate.game.model.Cell;

import java.net.URL;
import java.util.ResourceBundle;

public class U3TGameController implements Initializable {

    @FXML
    private GridPane u3tGrid;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        u3tGrid.setHgap(10);
        u3tGrid.setVgap(10);

        GridPane[][] gridPanes = new GridPane[3][3];

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                var gridPane = new GridPane();
                u3tGrid.add(gridPane, i, j);
                gridPanes[i][j] = gridPane;
            }
        }

        for (GridPane[] pane : gridPanes) {
            for (GridPane gridPane : pane) {
                for (int k = 0; k < 3; k++) {
                    for (int l = 0; l < 3; l++) {
                        gridPane.add(newDefaultPrimitiveTile(), k, l);
                    }
                }
            }
        }

    }

    private PrimitiveTile newDefaultPrimitiveTile() {
        PrimitiveTile primitiveTile = new PrimitiveTile(new Cell.Empty());

        primitiveTile.prefWidthProperty()
                .bind(u3tGrid.widthProperty().divide(10));

        primitiveTile.prefHeightProperty()
                .bind(primitiveTile.prefWidthProperty());

        primitiveTile.setStyle("""
                    -fx-border-color: black;
                    -fx-border-width: 1;
                """);

        Rectangle rectangle = new Rectangle(primitiveTile.getPrefWidth(), primitiveTile.getPrefHeight(), Color.WHITE);
        primitiveTile.getChildren().add(rectangle);

        return primitiveTile;
    }


    private static Border newBorder(BorderStroke borderStroke) {
        return new Border(borderStroke);
    }

    private static BorderWidths newDefaultBorderWidths() {
        return new BorderWidths(1, 0, 0, 0);
    }

    private static BorderStroke newDefaultBorderStroke(BorderWidths borderWidths) {
        return new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, borderWidths);
    }

}
