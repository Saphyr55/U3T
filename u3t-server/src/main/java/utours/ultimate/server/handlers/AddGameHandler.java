package utours.ultimate.server.handlers;

import utours.ultimate.core.steorotype.Component;
import utours.ultimate.game.model.Game;
import utours.ultimate.game.port.GameInventory;
import utours.ultimate.net.Context;
import utours.ultimate.net.Handler;
import utours.ultimate.net.NetApplication;

import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;

@Component
public class AddGameHandler implements Handler<Context> {

    private final static String ADDRESS = "server.game.add-game";
    private static final Logger LOGGER = Logger.getGlobal();

    private final GameInventory gameInventory;
    private final NetApplication application;

    public AddGameHandler(NetApplication application, GameInventory gameInventory) {
        this.application = application;
        this.gameInventory = gameInventory;

        application.handler(ADDRESS, this);
    }

    @Override
    public void handle(Context context) {

        Object content = context.message().content();

        if (Objects.requireNonNull(content) instanceof Game game) {

            gameInventory.add(game);

            application.sendMessage(ADDRESS, game);

            LOGGER.log(Level.INFO, "Game added");

        } else {

            LOGGER.log(Level.SEVERE, () -> "At the address %s, expected '%s' but receive %s"
                    .formatted(ADDRESS, Game.class, content.getClass()));
        }
    }

}
