package utours.ultimate.server.handlers;

import utours.ultimate.core.steorotype.Component;
import utours.ultimate.core.steorotype.Ref;
import utours.ultimate.game.model.PendingGame;
import utours.ultimate.game.port.PendingGameInventory;
import utours.ultimate.net.Context;
import utours.ultimate.net.Handler;
import utours.ultimate.net.NetApplication;

import java.util.List;

@Component
public class FindAllPendingGameHandler implements Handler<Context> {

    public static final String ADDRESS = "server.pending-game.find-all";

    private final NetApplication application;
    private final PendingGameInventory pendingGameInventory;

    public FindAllPendingGameHandler(NetApplication application,
                                     PendingGameInventory pendingGameInventory) {

        this.application = application;
        this.pendingGameInventory = pendingGameInventory;
        this.application.handler(ADDRESS, this);
    }

    @Override
    public void handle(Context context) {
        List<PendingGame> pendingGames = pendingGameInventory.findAll();
        application.sendMessage(ADDRESS, pendingGames);
    }

}
