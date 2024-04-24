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

public class PrimitiveTile extends Tile {

    public static final String PRIMITIVE_TILE_CLASS = "primitive-tile";

    private Cell.Pos posIn;

    public PrimitiveTile(Cell cell, Cell.Pos posOut, Cell.Pos posIn) {
        super(cell, posOut);
        this.posIn = posIn;
    }

    public Cell.Pos getPosIn() {
        return posIn;
    }

}
