package utours.ultimate.desktop.factory;

import utours.ultimate.core.steorotype.Component;
import utours.ultimate.game.feature.GameService;
import utours.ultimate.game.feature.internal.GameServiceInternal;

@Component
public final class GameServiceProvider {
    
    public GameService service;

    public GameServiceProvider() {
        this.service = new GameServiceInternal();
    }

    @Component
    public GameService getGameService() {
        return service;
    }

}
