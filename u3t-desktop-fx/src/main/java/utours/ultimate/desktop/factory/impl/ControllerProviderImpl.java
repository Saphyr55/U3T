package utours.ultimate.desktop.factory.impl;

import utours.ultimate.core.steorotype.Component;
import utours.ultimate.core.steorotype.Mapping;
import utours.ultimate.desktop.controller.*;
import utours.ultimate.desktop.factory.ControllerProvider;
import utours.ultimate.desktop.service.ClientService;
import utours.ultimate.game.feature.GameService;
import utours.ultimate.game.model.Game;
import utours.ultimate.net.Client;

@Component
@Mapping
public class ControllerProviderImpl implements ControllerProvider {

    private final MainController mainController;
    private final PartiesController partiesController;
    private final U3TGameController u3TGameController;
    private final ChatController chatController;

    public ControllerProviderImpl(GameService service,
                                  ClientService clientService,
                                  Client client) {

        partiesController = new PartiesController(clientService, client);
        u3TGameController = new U3TGameController(service, Game.newDefaultGame(), client);
        chatController = new ChatController();
        mainController = new MainController();
    }

    @Override
    public PolymorphicController getPolymorphicController() {
        return new PolymorphicController();
    }

    @Override
    public PartiesController getPartiesController() {
        return partiesController;
    }

    @Override
    public U3TGameController getU3TGameController() {
        return u3TGameController;
    }

    @Override
    public ChatController getChatController() {
        return chatController;
    }

    @Override
    public MainController getMainController() {
        return mainController;
    }

}
