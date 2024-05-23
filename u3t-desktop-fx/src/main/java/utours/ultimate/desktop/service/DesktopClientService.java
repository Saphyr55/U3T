package utours.ultimate.desktop.service;

import utours.ultimate.core.steorotype.Component;
import utours.ultimate.core.steorotype.Mapping;
import utours.ultimate.client.ClientService;
import utours.ultimate.game.model.Game;
import utours.ultimate.net.Client;

import java.util.function.Consumer;

@Component
@Mapping
public class DesktopClientService implements ClientService {

    @Override
    public void joinGame(Client client, Consumer<Game> onJoinGame) {
        onJoinGame.accept(Game.newDefaultGame());
    }

}
