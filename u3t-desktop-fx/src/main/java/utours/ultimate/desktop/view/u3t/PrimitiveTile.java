package utours.ultimate.desktop.view.u3t;

import javafx.beans.binding.Bindings;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import utours.ultimate.game.model.Cell;

public class PrimitiveTile extends StackPane {

    public static final String PRIMITIVE_TILE_CLASS = "primitive-tile";

    private Cell.Pos posIn;
    private Cell.Pos posOut;
    private Rectangle rectangle;
    private Button button;
    private Circle circle;
    private Text cross;
    private Cell cell;

    private PrimitiveTile(Cell cell, Cell.Pos posOut, Cell.Pos posIn) {
        this.posOut = posOut;
        this.posIn = posIn;
        setCell(cell);
        setAlignment(Pos.CENTER);

        this.rectangle = newRectangle();

        getChildren().add(this.rectangle);

        getStyleClass().add(PRIMITIVE_TILE_CLASS);
    }

    public void setCell(Cell cell) {
        this.cell = cell;
        switch (cell) {
            case Cell.Round ignored -> {
                circle = newCircle();
                getChildren().add(circle);
                getChildren().remove(cross);
            }
            case Cell.Empty ignored -> {
                getChildren().remove(cross);
                getChildren().remove(circle);
            }
            case Cell.Cross ignored -> {
                cross = newText();
                getChildren().add(cross);
                getChildren().remove(circle);
            }
            default -> throw new IllegalStateException("Unexpected value: " + cell);
        }
    }

    public Cell.Pos getPosOut() {
        return posOut;
    }

    public Cell.Pos getPosIn() {
        return posIn;
    }

    public Cell getCell() {
        return cell;
    }

    private Rectangle newRectangle() {
        return new Rectangle(getPrefWidth(), getPrefHeight(), Color.WHITE);
    }

    private Circle newCircle() {
        var circleRadius = widthProperty().divide(3);
        var circle = new Circle(getPrefWidth() / 2, getPrefHeight() / 2, circleRadius.get());
        circle.radiusProperty().bind(circleRadius);
        circle.setFill(Color.WHITE);
        circle.setStroke(Color.BLUE);
        return circle;
    }

    private Text newText() {
        var prop = prefWidthProperty().divide(2).asString();
        var text = new Text("X");
        text.setFill(Color.RED);
        text.styleProperty().bind(Bindings.concat("-fx-font-size: ", prop));
        return text;
    }

    public static PrimitiveTile newPrimitiveTile(Cell cell, Cell.Pos posOut, Cell.Pos posIn) {
        return new PrimitiveTile(cell, posOut, posIn);
    }

    public static PrimitiveTile newEmptyTile(GridPane gridPane, Cell.Pos posOut, Cell.Pos posIn) {

        PrimitiveTile tile = newPrimitiveTile(new Cell.Empty(), posOut, posIn);

        tile.prefWidthProperty().bind(gridPane.widthProperty().divide(10));
        tile.prefHeightProperty().bind(tile.prefWidthProperty());

        tile.setStyle("""
                -fx-border-color: black;
                -fx-border-width: 1;
                """);

        return tile;
    }

}
