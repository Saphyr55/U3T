package utours.ultimate.desktop.factory.impl;

import utours.ultimate.core.steorotype.Component;
import utours.ultimate.core.steorotype.Mapping;
import utours.ultimate.desktop.controller.ChatController;
import utours.ultimate.desktop.controller.PartiesController;
import utours.ultimate.desktop.controller.PolymorphicController;
import utours.ultimate.desktop.controller.U3TGameController;
import utours.ultimate.desktop.factory.ControllerFactory;
import utours.ultimate.desktop.service.ClientService;
import utours.ultimate.game.feature.GameService;
import utours.ultimate.game.model.Game;
import utours.ultimate.net.Client;

import java.util.function.Function;

@Mapping(clazz = ControllerFactory.class, type = Mapping.Type.Unique)
public class ControllerFactoryImpl implements ControllerFactory {

    private final Function<Game, U3TGameController> u3tGameControllerFactory;
    private final Client client;
    private final ClientService clientService;

    public ControllerFactoryImpl(GameService service,
                                 ClientService clientService,
                                 Client client) {

        this.clientService = clientService;
        this.client = client;
        u3tGameControllerFactory = game -> new U3TGameController(service, game, client);
    }

    @Override
    public PartiesController createPartiesController() {
        return new PartiesController(clientService, client);
    }

    @Override
    public U3TGameController createU3TGameController() {
        return u3tGameControllerFactory.apply(Game.Builder.newDefaultBuilder().build());
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
