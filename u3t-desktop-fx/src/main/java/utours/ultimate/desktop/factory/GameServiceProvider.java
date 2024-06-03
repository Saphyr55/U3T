package utours.ultimate.desktop.factory;

import utours.ultimate.client.AsyncGameInventory;
import utours.ultimate.client.ClientGameService;
import utours.ultimate.core.steorotype.Component;
import utours.ultimate.game.feature.GameService;
import utours.ultimate.game.feature.internal.GameServiceBase;
import utours.ultimate.net.Client;

@Component
public final class GameServiceProvider {
    
    public GameService service;

    public GameServiceProvider(AsyncGameInventory asyncInventory) {
        this.service = new ClientGameService(asyncInventory);
    }

    @Component
    public GameService getGameService() {
        return service;
    }

}
