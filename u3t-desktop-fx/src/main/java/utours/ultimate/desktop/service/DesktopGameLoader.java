package utours.ultimate.desktop.service;

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

    public DesktopGameLoader(GameGridView gridView, Consumer<PrimitiveTile> onAcceptTile) {
        this.gridView = gridView;
        this.onAcceptTile = onAcceptTile;
    }

    @Override
    public void loadGame(GameActionsProvider gameActionsProvider) {
        for (Action action : gameActionsProvider.actions()) {
            Region region = gridView.getRegions()[action.posOut().x()][action.posOut().y()];
            if (region instanceof GridPane gridPane) {
                var tile = gridPane.getChildren().get(action.posIn().x() * GRID_SIZE + action.posIn().y());
                if (tile instanceof PrimitiveTile primitiveTile) {
                    onAcceptTile.accept(primitiveTile);
                }
            }
        }
    }


}
