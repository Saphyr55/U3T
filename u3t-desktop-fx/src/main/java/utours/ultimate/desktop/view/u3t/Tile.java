package utours.ultimate.desktop.view.u3t;

import javafx.beans.binding.Bindings;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import utours.ultimate.game.model.Cell;

public class Tile extends StackPane {

    public static final int CIRCLE_DEFAULT_SIZE_STROKE = 4;

    protected Cell.Pos posOut;
    protected Rectangle rectangle;
    protected Circle circle;
    protected Text cross;
    protected Cell cell;

    protected Tile(Cell cell, Cell.Pos posOut) {
        this.posOut = posOut;
        this.circle = newCircle();
        this.cross = newTextX(0.7);
        this.rectangle = newRectangle();
        setCell(cell);
    }

    public void setCell(Cell cell) {
        this.cell = cell;
        switch (cell) {
            case Cell.Round ignored -> {
                getChildren().add(circle);
                getChildren().remove(cross);
            }
            case Cell.Empty ignored -> {
                getChildren().remove(cross);
                getChildren().remove(circle);
            }
            case Cell.Cross ignored -> {
                getChildren().add(cross);
                getChildren().remove(circle);
            }
            default -> throw new IllegalStateException("Unexpected value: " + cell);
        }
    }

    public Cell.Pos getPosOut() {
        return posOut;
    }

    public Cell getCell() {
        return cell;
    }

    protected Rectangle newRectangle() {
        return new Rectangle(getPrefWidth(), getPrefHeight(), Color.WHITE);
    }

    protected Circle newCircle() {
        var circleRadius = widthProperty().divide(3);
        var circle = new Circle(getPrefWidth() / 2, getPrefHeight() / 2, circleRadius.get());
        circle.radiusProperty().bind(circleRadius);
        circle.setFill(Color.TRANSPARENT);
        circle.setStrokeWidth(CIRCLE_DEFAULT_SIZE_STROKE);
        circle.setStroke(Color.BLUE);
        return circle;
    }

    protected Text newTextX(double size) {
        var prop = prefWidthProperty().divide(size).asString();
        var text = new Text("X");
        text.setFill(Color.RED);
        text.styleProperty().bind(Bindings.concat("-fx-font-size: ", prop));
        return text;
    }

    public static Tile newTile(Cell cell, Cell.Pos posOut) {
        return new Tile(cell, posOut);
    }

    public static Tile newTile(GridPane gridPane, Cell cell, Cell.Pos posOut) {
        Tile tile = newTile(cell, posOut);
        setupDefaultTile(gridPane, tile);
        return tile;
    }

    public static PrimitiveTile newPrimitiveTile(Cell cell, Cell.Pos posOut, Cell.Pos posIn) {
        return new PrimitiveTile(cell, posOut, posIn);
    }

    public static PrimitiveTile newEmptyPrimitiveTile(GridPane gridPane, Cell.Pos posOut, Cell.Pos posIn) {
        PrimitiveTile tile = newPrimitiveTile(new Cell.Empty(), posOut, posIn);
        setupDefaultTile(gridPane, tile);
        return tile;
    }

    public static void setupDefaultTile(GridPane gridPane, Tile tile) {
        tile.prefWidthProperty().bind(gridPane.widthProperty().divide(10));
        tile.prefHeightProperty().bind(tile.prefWidthProperty());

        tile.setStyle("""
                -fx-border-color: black;
                -fx-border-width: 1;
                """);

    }

}
