package utours.ultimate.desktop.factory.impl;

import utours.ultimate.client.AsyncPendingGameInventory;
import utours.ultimate.core.steorotype.Component;
import utours.ultimate.core.steorotype.Mapping;
import utours.ultimate.desktop.controller.*;
import utours.ultimate.desktop.factory.ControllerProvider;
import utours.ultimate.client.ClientService;
import utours.ultimate.game.feature.GameService;

@Component
@Mapping
public class ControllerProviderImpl implements ControllerProvider {

    private final MainController mainController;
    private final PartiesController partiesController;
    private final U3TGameController u3TGameController;
    private final ChatController chatController;

    public ControllerProviderImpl(GameService service,
                                  AsyncPendingGameInventory pendingGameInventory,
                                  ClientService clientService) {

        mainController = new MainController();
        partiesController = new PartiesController(mainController, clientService, pendingGameInventory);
        u3TGameController = new U3TGameController(clientService, service);
        chatController = new ChatController();
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
