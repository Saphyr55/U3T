package utours.ultimate.server.handlers;

import utours.ultimate.core.steorotype.Component;
import utours.ultimate.game.model.Game;
import utours.ultimate.net.NetApplication;

import java.util.List;

@Component
public class OnChangedGameInventory {

    public static final String SERVER_GAME_INVENTORY_CHANGED_ADDRESS = "server.game-inventory.changed";
    private final NetApplication application;

    public OnChangedGameInventory(NetApplication application) {
        this.application = application;
    }

    public void update(List<Game> games) {
        application.sendMessage(SERVER_GAME_INVENTORY_CHANGED_ADDRESS, games);
    }

}
