package utours.ultimate.server.handlers;

import utours.ultimate.core.steorotype.Component;
import utours.ultimate.game.model.Game;
import utours.ultimate.game.port.GameInventory;
import utours.ultimate.net.Context;
import utours.ultimate.net.Handler;
import utours.ultimate.net.NetApplication;

@Component
public class AddGameHandler implements Handler<Context> {

    private final static String ADDRESS = "server.game.add-game";

    private final NetApplication application;
    private final GameInventory gameInventory;

    public AddGameHandler(NetApplication application, GameInventory gameInventory) {
        this.application = application;
        this.gameInventory = gameInventory;
    }

    @Override
    public void handle(Context context) {
        gameInventory.add(Game.newDefaultGame());
    }

}
