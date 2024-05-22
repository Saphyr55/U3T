package utours.ultimate.server.handlers;

import utours.ultimate.core.steorotype.Component;
import utours.ultimate.game.model.Game;
import utours.ultimate.game.port.GameInventory;
import utours.ultimate.net.Context;
import utours.ultimate.net.Handler;
import utours.ultimate.net.NetApplication;

import java.util.List;

@Component
public class FindAllGameHandler implements Handler<Context> {

    public static final String ADDRESS = "server.game.find-all";

    private final NetApplication application;
    private final GameInventory gameMemoryInventory;

    public FindAllGameHandler(NetApplication application,
                              GameInventory gameInventory) {

        this.application = application;
        this.gameMemoryInventory = gameInventory;
    }

    @Override
    public void handle(Context context) {
        List<Game> games = gameMemoryInventory.findAll();
        application.sendMessage(ADDRESS, games);
    }

    private void handlingApplication() {
        application.handler(ADDRESS, this);
    }

}
