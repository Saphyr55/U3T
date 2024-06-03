package utours.ultimate.server.handlers;

import utours.ultimate.core.steorotype.Component;
import utours.ultimate.game.model.Game;
import utours.ultimate.game.model.PendingGame;
import utours.ultimate.game.port.GameInventory;
import utours.ultimate.game.port.PendingGameInventory;
import utours.ultimate.net.Context;
import utours.ultimate.net.Handler;
import utours.ultimate.net.NetApplication;
import utours.ultimate.server.exception.NotGoodFormatClassException;

import java.util.logging.Level;
import java.util.logging.Logger;

public class UpdateGameHandler implements Handler<Context> {

    private final static String ADDRESS = "server.game-inventory.update";
    private static final Logger LOGGER = Logger.getGlobal();

    private final GameInventory inventory;

    public UpdateGameHandler(NetApplication application,
                             GameInventory inventory) {

        this.inventory = inventory;

        application.handler(ADDRESS, this);
    }

    @Override
    public void handle(Context context) throws Exception {

        Object content = context.message().content();

        if (content instanceof Game game) {

            inventory.update(game);

            LOGGER.log(Level.INFO, () -> "Pending game #%s updated".formatted(game.gameID()));

        } else {

            String error = "At the address '%s', expected '%s' but receive %s"
                    .formatted(ADDRESS, Game.class, content.getClass());

            LOGGER.log(Level.SEVERE, error);

            throw new NotGoodFormatClassException(error);
        }

    }

}
