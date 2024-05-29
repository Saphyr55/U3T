package utours.ultimate.client;

import utours.ultimate.core.steorotype.Component;
import utours.ultimate.game.port.OnChangedPendingGames;
import utours.ultimate.game.model.Game;
import utours.ultimate.game.model.PendingGame;
import utours.ultimate.net.Client;

import java.util.List;
import java.util.function.Consumer;

@Component
public class ClientService {

    private final Client client;

    public ClientService(Client client) {
        this.client = client;
    }

    public void onChangedPendingGames(OnChangedPendingGames onChangedPendingGames) {

        client.messageReceiver().receive("server.pending-game-inventory.changed", message -> {
            if (message.isSuccess()) {
                onChangedPendingGames.onChanged((List<PendingGame>) message.content());
            }
        });

    }

    public void joinGame(Consumer<Game> onJoinGame) {

        client.messageReceiver().receive("server.game.add-game", message -> {
            onJoinGame.accept((Game) message.content());
        });

        client.messageSender().send("server.game.add-game", Game.defaultGame());

    }

}
