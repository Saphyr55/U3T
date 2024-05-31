package utours.ultimate.client;

import utours.ultimate.core.steorotype.Component;
import utours.ultimate.game.model.Player;
import utours.ultimate.game.port.OnChangedPendingGame;
import utours.ultimate.game.port.OnChangedPendingGameInventory;
import utours.ultimate.game.model.Game;
import utours.ultimate.game.model.PendingGame;
import utours.ultimate.game.port.OnJoinGame;
import utours.ultimate.net.Client;

import java.util.List;
import java.util.function.Consumer;

@Component
public class ClientService {

    public static final String PENDING_GAME_CHANGED = "server.pending-game.%s.changed";
    public static final String PENDING_GAME_INVENTORY_CHANGED = "server.pending-game-inventory.changed";


    private final Client client;
    private final AsyncPendingGameInventory inventory;

    private PendingGame currentPendingGame;
    private Player currentPlayer;

    public ClientService(Client client, AsyncPendingGameInventory inventory) {
        this.client = client;
        this.inventory = inventory;
    }

    public void onChangedPendingGame(PendingGame pendingGame, OnChangedPendingGame onChangedPendingGame) {

        String address = PENDING_GAME_CHANGED.formatted(pendingGame.gameID());

        client.messageReceiver().receive(address, message -> {
            if (message.isSuccess()) {
                onChangedPendingGame.onChanged((PendingGame) message.content());
            } else {
                System.err.printf("Error at '%s', Message: %s%n", address, message);
            }
        });
    }

    public void onChangedPendingGames(OnChangedPendingGameInventory onChangedPendingGameInventory) {
        client.messageReceiver().receive(PENDING_GAME_INVENTORY_CHANGED, message -> {
            if (message.isSuccess()) {
                onChangedPendingGameInventory.onChanged((List<PendingGame>) message.content());
            }
        });
    }

    public void joinPendingGame(PendingGame pendingGame, OnJoinGame onJoinGame) {

        if (pendingGame.isFull() || isInPendingGame()) return;

        PendingGame.Builder builder = PendingGame.Builder.copyOf(pendingGame);
        currentPlayer = Player.builder().build();

        currentPendingGame = pendingGame.firstPlayer() == null
                ? builder.withFirstPlayer(currentPlayer).build()
                : builder.withSecondPlayer(currentPlayer).build();

        onChangedPendingGame(currentPendingGame, pg -> {

            if (!pg.isFull()) return;

            currentPendingGame = pg;
            joinGame(onJoinGame);
        });

        inventory.update(currentPendingGame);
    }

    private boolean isInPendingGame() {
        return currentPendingGame != null;
    }

    public void joinGame(OnJoinGame onJoinGame) {

        client.messageReceiver().receive("server.game-inventory.add", message -> {
            onJoinGame.joinGame((Game) message.content());
        });

        client.messageSender().send("server.game-inventory.add", Game.defaultGame());

    }

}
