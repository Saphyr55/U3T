package utours.ultimate.desktop.factory.impl;

import utours.ultimate.core.steorotype.Component;
import utours.ultimate.desktop.controller.ChatController;
import utours.ultimate.desktop.controller.PolymorphicController;
import utours.ultimate.desktop.controller.U3TGameController;
import utours.ultimate.desktop.factory.ControllerFactory;
import utours.ultimate.game.feature.GameService;
import utours.ultimate.game.model.Game;
import utours.ultimate.net.Client;

import java.util.function.Function;

@Component
public class ControllerFactoryImpl implements ControllerFactory {

    private final Function<Game, U3TGameController> u3tGameController;

    public ControllerFactoryImpl(GameService service, Client client) {
        u3tGameController = game -> new U3TGameController(service, game, client);
    }

    @Override
    public U3TGameController createU3TGameController() {
        return u3tGameController.apply(Game.Builder.newDefaultBuilder().build());
    }

    @Override
    public PolymorphicController createPolymorphicController() {
        return new PolymorphicController();
    }

    @Override
    public ChatController createChatController() {
        return new ChatController();
    }

}
