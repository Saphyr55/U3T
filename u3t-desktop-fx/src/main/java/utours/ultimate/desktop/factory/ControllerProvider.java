package utours.ultimate.desktop.factory;

import utours.ultimate.core.steorotype.Component;
import utours.ultimate.core.steorotype.Mapping;
import utours.ultimate.desktop.controller.*;

@Component
public interface ControllerProvider {

    @Mapping
    @Component
    PartiesController getPartiesController();

    @Mapping
    @Component
    U3TGameController getU3TGameController();

    @Mapping
    @Component
    ChatController getChatController();

    @Mapping
    @Component
    MainController getMainController();

}
