package utours.ultimate.desktop.service;

import javafx.scene.Node;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import utours.ultimate.desktop.view.GameGridView;
import utours.ultimate.desktop.view.u3t.PrimitiveTile;
import utours.ultimate.game.feature.GameLoader;
import utours.ultimate.game.feature.GameActionsProvider;
import utours.ultimate.game.model.Action;

import java.util.function.Consumer;

import static utours.ultimate.desktop.view.GameGridView.GRID_SIZE;

public final class DesktopGameLoader implements GameLoader {

    private final GameGridView gridView;
    private final Consumer<PrimitiveTile> onAcceptTile;

    public DesktopGameLoader(GameGridView gridView,
                             Consumer<PrimitiveTile> onAcceptTile) {

        this.gridView = gridView;
        this.onAcceptTile = onAcceptTile;
    }

    @Override
    public void loadGame(GameActionsProvider gameActionsProvider) {

        for (Action action : gameActionsProvider.actions()) {

            int outX = action.posOut().x();
            int outY = action.posOut().y();

            Region region = gridView.getRegions()[outX][outY];

            if (region instanceof GridPane gridPane) {

                int inX = action.posIn().x();
                int inY = action.posIn().y();
                int pos = inX * GRID_SIZE + inY;

                Node tile = gridPane.getChildren().get(pos);

                if (tile instanceof PrimitiveTile primitiveTile) {
                    onAcceptTile.accept(primitiveTile);
                }

            }
        }
    }

}
