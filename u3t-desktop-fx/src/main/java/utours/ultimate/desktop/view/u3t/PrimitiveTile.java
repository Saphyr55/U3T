package utours.ultimate.desktop.view.u3t;

import javafx.geometry.Pos;
import javafx.scene.layout.StackPane;
import utours.ultimate.game.model.Cell;

public class PrimitiveTile extends StackPane {

    public static final String PRIMITIVE_TILE_CLASS = "primitive-tile";

    private Cell cell;

    public PrimitiveTile(Cell cell) {
        this.cell = cell;

        getStyleClass().add(PRIMITIVE_TILE_CLASS);
        setAlignment(Pos.CENTER);
    }



}
