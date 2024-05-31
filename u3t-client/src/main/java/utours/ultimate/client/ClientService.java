package utours.ultimate.client;

import utours.ultimate.core.steorotype.Component;
import utours.ultimate.game.port.OnChangedPendingGame;
import utours.ultimate.game.port.OnChangedPendingGameInventory;
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

    public void onChangedPendingGame(PendingGame pendingGame, OnChangedPendingGame onChangedPendingGame) {

        String address = "server.pending-game.%s.changed".formatted(
                pendingGame.gameID());

        client.messageReceiver().receive(address, message -> {
            if (message.isSuccess()) {
                onChangedPendingGame.onChanged((PendingGame) message.content());
            } else {
                System.err.printf("Error at '%s', Message: %s%n", address, message);
            }
        });
    }

    public void onChangedPendingGames(OnChangedPendingGameInventory onChangedPendingGameInventory) {
        client.messageReceiver().receive("server.pending-game-inventory.changed", message -> {
            if (message.isSuccess()) {
                onChangedPendingGameInventory.onChanged((List<PendingGame>) message.content());
            }
        });
    }

    public void joinGame(Consumer<Game> onJoinGame) {

        client.messageReceiver().receive("server.game-inventory.add", message -> {
            onJoinGame.accept((Game) message.content());
        });

        client.messageSender().send("server.game-inventory.add", Game.defaultGame());

    }

}
