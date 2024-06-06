package utours.ultimate.desktop.view;

import javafx.scene.Node;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import utours.ultimate.desktop.action.OnPressedPrimitiveTile;
import utours.ultimate.desktop.view.u3t.PrimitiveTile;
import utours.ultimate.desktop.view.u3t.Tile;
import utours.ultimate.game.model.Action;
import utours.ultimate.game.model.Cell;
import utours.ultimate.game.model.Game;
import utours.ultimate.game.model.Player;

public class GameGridView extends GridPane {

    public static final int GRID_SIZE = 3;

    private int gridSize;
    private final Region[][] regions;
    private OnPressedPrimitiveTile onPressedTile;

    public GameGridView() {

        this.gridSize = GRID_SIZE;
        this.onPressedTile = (action, primitive) -> { };
        this.regions = new Region[gridSize][gridSize];
    }
    
    public Node nodeAt(int i, int j) {

        int linearPos = i * getGridSize() + j;

        return getChildren().get(linearPos);
    }

    public void fillGridPanes() {

        for (int i = 0; i < getGridSize(); i++) {
            for (int j = 0; j < getGridSize(); j++) {
                var gridPane = new GridPane();
                add(gridPane, i, j);
                regions[i][j] = gridPane;
            }
        }

    }

    public void initBoard(Player player) {

        fillGridPanes();

        for (int i = 0; i < regions.length; i++) {
            Region[] pane = regions[i];

            for (int j = 0; j < pane.length; j++) {
                Region region = pane[j];

                for (int k = 0; k < getGridSize(); k++) {
                    for (int l = 0; l < getGridSize(); l++) {

                        if (region instanceof GridPane gridPane) {

                            Cell.Pos posOut = Cell.pos(i, j);
                            Cell.Pos posIn = Cell.pos(k, l);

                            PrimitiveTile tile = Tile.newEmptyPrimitiveTile(this, posOut, posIn);

                            tile.setOnMouseClicked(e -> {
                                Action action = Action.of(player, posOut, posIn);
                                onPressedTile.onPressed(action, tile);
                            });

                            gridPane.add(tile, k, l);
                        }

                    }
                }
            }
        }

    }

    public void setOnPressedTile(OnPressedPrimitiveTile onPressedTile) {
        this.onPressedTile = onPressedTile;
    }

    public OnPressedPrimitiveTile getOnPressedTile() {
        return onPressedTile;
    }

    public Region[][] getRegions() {
        return regions;
    }

    public int getGridSize() {
        return gridSize;
    }

    public void setGridSize(int gridSize) {
        this.gridSize = gridSize;
    }
}
