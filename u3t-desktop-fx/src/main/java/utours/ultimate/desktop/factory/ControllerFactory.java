package utours.ultimate.desktop.factory;

import utours.ultimate.core.steorotype.Component;
import utours.ultimate.desktop.controller.U3TGameController;

@Component
public interface ControllerFactory {

    U3TGameController createU3TGameController();

}
