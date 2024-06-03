package utours.ultimate.client;

import utours.ultimate.core.steorotype.Component;
import utours.ultimate.game.model.Player;
import utours.ultimate.game.model.Game;
import utours.ultimate.game.model.PendingGame;
import utours.ultimate.game.port.OnChanged;
import utours.ultimate.game.port.OnJoinGame;
import utours.ultimate.net.Client;

import java.util.List;

@Component
public class ClientService {

    public static final String PENDING_GAME_CHANGED = "server.pending-game.%s.changed";
    public static final String PENDING_GAME_INVENTORY_CHANGED = "server.pending-game-inventory.changed";

    public static final String GAME_CHANGED = "server.game.%s.changed";

    private final Client client;
    private final AsyncPendingGameInventory inventory;

    private PendingGame currentPendingGame;

    public ClientService(Client client, AsyncPendingGameInventory inventory) {
        this.client = client;
        this.inventory = inventory;
    }

    public void onChangedGame(Game game, OnChanged<Game> onChangedGame) {

        String address = GAME_CHANGED.formatted(game.gameID());

        client.onReceiveMessage(address, message -> {
            if (message.isSuccess()) {
                onChangedGame.onChanged((Game) message.content());
            } else {
                System.err.printf("Error at '%s', Message: %s%n", address, message);
            }
        });

    }

    public void onChangedPendingGame(PendingGame pendingGame, OnChanged<PendingGame> onChangedPendingGame) {

        String address = PENDING_GAME_CHANGED.formatted(pendingGame.gameID());

        client.messageReceiver().receive(address, message -> {
            if (message.isSuccess()) {
                onChangedPendingGame.onChanged((PendingGame) message.content());
            } else {
                System.err.printf("Error at '%s', Message: %s%n", address, message);
            }
        });
    }

    public void onChangedPendingGames(OnChanged<List<PendingGame>> onChangedInventory) {
        client.messageReceiver().receive(PENDING_GAME_INVENTORY_CHANGED, message -> {
            if (message.isSuccess()) {
                onChangedInventory.onChanged((List<PendingGame>) message.content());
            } else {
                System.err.printf("Error at '%s', Message: %s%n", PENDING_GAME_INVENTORY_CHANGED, message);
            }
        });
    }

    public void joinPendingGame(PendingGame pendingGame, OnJoinGame onJoinGame) {

        if (pendingGame.isFull() || isInPendingGame()) return;

        PendingGame.Builder builder = PendingGame.Builder.copyOf(pendingGame);

        currentPendingGame = pendingGame.firstPlayer() == null
                ? builder.withFirstPlayer(Player.ofDefault()).build()
                : builder.withSecondPlayer(Player.ofDefault()).build();

        onChangedPendingGame(currentPendingGame, pg -> {

            currentPendingGame = pg;

            if (!pg.isFull()) return;

            joinGame(onJoinGame, currentGame());
        });

        inventory.update(currentPendingGame);
    }

    private boolean isInPendingGame() {
        return currentPendingGame != null;
    }

    public void joinGame(OnJoinGame onJoinGame, Game game) {

        client.messageReceiver().receive("server.game-inventory.add", message -> {
            onJoinGame.joinGame((Game) message.content());
        });

        client.messageSender().send("server.game-inventory.add", game);

    }

    public Game gameOf(PendingGame pendingGame) {
        return Game.Builder.defaultBuilder()
                .withGameID(pendingGame.gameID())
                .withCrossPlayer(pendingGame.firstPlayer())
                .withRoundPlayer(pendingGame.secondPlayer())
                .build();
    }

    public Game currentGame() {
        return gameOf(currentPendingGame);
    }

}
