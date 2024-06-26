package utours.ultimate.server.handlers;

import utours.ultimate.core.steorotype.Component;
import utours.ultimate.game.model.PendingGame;
import utours.ultimate.net.NetApplication;

import java.util.List;

@Component
public class OnChangedPendingGameInventory {

    public static final String PENDING_GAME_CHANGED_ADDRESS = "server.pending-game.%s.changed";
    public static final String PENDING_GAME_INVENTORY_CHANGED_ADDRESS = "server.pending-game-inventory.changed";

    private final NetApplication application;

    public OnChangedPendingGameInventory(NetApplication application) {
        this.application = application;
    }

    public void update(PendingGame game) {
        application.sendMessage(PENDING_GAME_CHANGED_ADDRESS.formatted(game.gameID()), game);
    }

    public void update(List<PendingGame> games) {
        application.sendMessage(PENDING_GAME_INVENTORY_CHANGED_ADDRESS, games);
    }

}
