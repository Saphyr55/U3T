package utours.ultimate.desktop.factory;

import utours.ultimate.core.steorotype.Component;
import utours.ultimate.core.steorotype.FactoryMethod;
import utours.ultimate.desktop.controller.ChatController;
import utours.ultimate.desktop.controller.PartiesController;
import utours.ultimate.desktop.controller.PolymorphicController;
import utours.ultimate.desktop.controller.U3TGameController;

@Component
public interface ControllerFactory {

    @FactoryMethod
    PartiesController createPartiesController();

    @FactoryMethod
    U3TGameController createU3TGameController();

    @FactoryMethod
    PolymorphicController createPolymorphicController();

    @FactoryMethod
    ChatController createChatController();

}
