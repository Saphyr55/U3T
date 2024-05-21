package utours.ultimate.desktop.view;

import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import utours.ultimate.desktop.view.u3t.PrimitiveTile;
import utours.ultimate.desktop.view.u3t.Tile;
import utours.ultimate.game.model.Cell;

import java.util.function.Consumer;

public class GameGridView extends GridPane {

    public static final int GRID_SIZE = 3;
    private Region[][] regions;
    private Consumer<PrimitiveTile> onPressedTile;

    public GameGridView() {
        this.onPressedTile = ignored -> { };
        this.regions = new Region[GRID_SIZE][GRID_SIZE];
    }

    public void fillGridPanes() {
        for (int i = 0; i < GRID_SIZE; i++) {
            for (int j = 0; j < GRID_SIZE; j++) {
                var gridPane = new GridPane();
                add(gridPane, i, j);
                regions[i][j] = gridPane;
            }
        }
    }

    public void initBoard() {

        this.fillGridPanes();

        for (int i = 0; i < regions.length; i++) {
            Region[] pane = regions[i];
            for (int j = 0; j < pane.length; j++) {
                Region region = pane[j];
                for (int k = 0; k < GRID_SIZE; k++) {
                    for (int l = 0; l < GRID_SIZE; l++) {
                        if (region instanceof GridPane gridPane) {
                            var tile = Tile.newEmptyPrimitiveTile(this, Cell.pos(i, j), Cell.pos(k, l));
                            tile.setOnMouseClicked(e -> onPressedTile.accept(tile));
                            gridPane.add(tile, k, l);
                        }
                    }
                }
            }
        }
    }

    public void setOnPressedTile(Consumer<PrimitiveTile> onPressedTile) {
        this.onPressedTile = onPressedTile;
    }

    public Consumer<PrimitiveTile> getOnPressedTile() {
        return onPressedTile;
    }

    public Region[][] getRegions() {
        return regions;
    }
}
