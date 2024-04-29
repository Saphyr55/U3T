package utours.ultimate.desktop.factory.impl;

import utours.ultimate.core.steorotype.Component;
import utours.ultimate.desktop.controller.U3TGameController;
import utours.ultimate.desktop.factory.ControllerFactory;
import utours.ultimate.game.feature.U3TGameService;
import utours.ultimate.game.model.U3TGame;
import utours.ultimate.net.Client;

import java.util.function.Function;

@Component
public class ControllerFactoryImpl implements ControllerFactory {

    private final Function<U3TGame, U3TGameController> u3tGameController;

    public ControllerFactoryImpl(U3TGameService service, Client client) {
        u3tGameController = game -> new U3TGameController(service, game, game.crossPlayer(), client);
    }

    @Override
    public U3TGameController createU3TGameController() {
        return u3tGameController.apply(U3TGame
                .Builder
                .newDefaultBuilder()
                .build());
    }

}
