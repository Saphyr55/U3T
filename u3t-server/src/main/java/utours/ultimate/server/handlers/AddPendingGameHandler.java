package utours.ultimate.server.handlers;

import utours.ultimate.core.steorotype.Component;
import utours.ultimate.game.model.Game;
import utours.ultimate.game.model.PendingGame;
import utours.ultimate.game.port.PendingGameInventory;
import utours.ultimate.net.Context;
import utours.ultimate.net.Handler;
import utours.ultimate.net.NetApplication;

import java.util.logging.Level;
import java.util.logging.Logger;

@Component
public class AddPendingGameHandler implements Handler<Context> {

    private final static String ADDRESS = "server.pending-game.add";
    private static final Logger LOGGER = Logger.getGlobal();

    private final PendingGameInventory pendingGameInventory;
    private final NetApplication application;

    public AddPendingGameHandler(NetApplication application,
                                 PendingGameInventory gameInventory) {

        this.application = application;
        this.pendingGameInventory = gameInventory;

        application.handler(ADDRESS, this);
    }

    @Override
    public void handle(Context context) {

        Object content = context.message().content();

        if (content instanceof PendingGame pendingGame) {

            pendingGameInventory.add(pendingGame);
            application.sendMessage(ADDRESS, pendingGame);

            LOGGER.log(Level.INFO, "Pending game added");
        } else {

            LOGGER.log(Level.SEVERE, () -> "At the address '%s', expected '%s' but receive %s"
                    .formatted(ADDRESS, PendingGame.class, content.getClass()));
        }
    }


}
