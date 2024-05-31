package utours.ultimate.server.handlers;

import utours.ultimate.core.steorotype.Component;
import utours.ultimate.game.model.Game;
import utours.ultimate.game.model.PendingGame;
import utours.ultimate.game.port.PendingGameInventory;
import utours.ultimate.net.Context;
import utours.ultimate.net.Handler;
import utours.ultimate.net.NetApplication;
import utours.ultimate.server.exception.NotGoodFormatClassException;

import java.util.logging.Level;
import java.util.logging.Logger;

@Component
public class AddPendingGameHandler implements Handler<Context> {

    private final static String ADDRESS = "server.pending-game-inventory.add";
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
    public void handle(Context context) throws Exception {

        Object content = context.message().content();

        if (content instanceof PendingGame pendingGame) {

            pendingGameInventory.add(pendingGame);
            application.sendMessage(ADDRESS, pendingGame);

            LOGGER.log(Level.INFO, () -> "Pending game added");

        } else {

            String error = "At the address '%s', expected '%s' but receive %s"
                    .formatted(ADDRESS, PendingGame.class, content.getClass());

            LOGGER.log(Level.SEVERE, error);

            throw new NotGoodFormatClassException(error);
        }
    }


}
