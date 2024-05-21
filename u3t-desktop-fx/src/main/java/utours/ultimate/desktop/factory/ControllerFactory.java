package utours.ultimate.desktop.factory;

import utours.ultimate.core.steorotype.Component;
import utours.ultimate.core.steorotype.Mapping;
import utours.ultimate.desktop.controller.ChatController;
import utours.ultimate.desktop.controller.PartiesController;
import utours.ultimate.desktop.controller.PolymorphicController;
import utours.ultimate.desktop.controller.U3TGameController;

@Component
public interface ControllerFactory {

    @Mapping
    @Component
    PolymorphicController createPolymorphicController();

    @Mapping
    @Component
    PartiesController createPartiesController();

    @Mapping
    @Component
    U3TGameController createU3TGameController();

    @Mapping
    @Component
    ChatController createChatController();

}
