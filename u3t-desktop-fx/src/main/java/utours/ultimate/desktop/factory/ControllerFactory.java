package utours.ultimate.desktop.factory;

import utours.ultimate.core.steorotype.Component;
import utours.ultimate.desktop.controller.ChatController;
import utours.ultimate.desktop.controller.PartiesController;
import utours.ultimate.desktop.controller.PolymorphicController;
import utours.ultimate.desktop.controller.U3TGameController;

@Component
public interface ControllerFactory {

    PartiesController createPartiesController();

    U3TGameController createU3TGameController();

    PolymorphicController createPolymorphicController();

    ChatController createChatController();

}
