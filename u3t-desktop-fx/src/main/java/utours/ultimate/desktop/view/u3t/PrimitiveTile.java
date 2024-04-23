package utours.ultimate.desktop.view.u3t;

import javafx.beans.binding.DoubleBinding;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import utours.ultimate.game.model.Cell;

public class PrimitiveTile extends StackPane {

    public static final String PRIMITIVE_TILE_CLASS = "primitive-tile";

    private Rectangle rectangle;
    private Button button;
    private Circle circle;
    private Line firstCrossLine;
    private Line secondCrossLine;
    private Cell cell;

    private PrimitiveTile(Cell cell) {
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
            }
            case Cell.Empty ignored -> {
                getChildren().remove(circle);
            }
            case Cell.Cross ignored -> {
                getChildren().remove(circle);
            }
            default -> throw new IllegalStateException("Unexpected value: " + cell);
        }
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
        circle.setStroke(Color.BLACK);
        return circle;
    }

    public static PrimitiveTile newPrimitiveTile(Cell cell) {
        return new PrimitiveTile(cell);
    }

    public static PrimitiveTile newEmptyTile(GridPane gridPane) {

        PrimitiveTile primitiveTile =
                new PrimitiveTile(new Cell.Empty());

        primitiveTile
                .prefWidthProperty()
                .bind(gridPane.widthProperty().divide(10));

        primitiveTile
                .prefHeightProperty()
                .bind(primitiveTile.prefWidthProperty());

        primitiveTile.setStyle("""
                    -fx-border-color: black;
                    -fx-border-width: 1;
                """);

        return primitiveTile;
    }

}
