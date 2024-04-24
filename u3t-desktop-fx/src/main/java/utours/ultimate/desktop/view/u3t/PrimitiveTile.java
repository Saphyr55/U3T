package utours.ultimate.desktop.view.u3t;

import javafx.geometry.Pos;
import utours.ultimate.game.model.Cell;

public class PrimitiveTile extends Tile {

    private Cell.Pos posIn;

    public PrimitiveTile(Cell cell, Cell.Pos posOut, Cell.Pos posIn) {
        super(cell, posOut);
        super.cross = newTextX(2);
        this.posIn = posIn;

        setAlignment(Pos.CENTER);
        getChildren().add(this.rectangle);
    }

    public Cell.Pos getPosIn() {
        return posIn;
    }

}
