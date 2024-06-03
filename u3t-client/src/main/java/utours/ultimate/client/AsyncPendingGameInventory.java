package utours.ultimate.client;

import utours.ultimate.core.steorotype.Component;
import utours.ultimate.game.model.PendingGame;
import utours.ultimate.game.port.PendingGameInventory;
import utours.ultimate.net.Client;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

@Component
public class AsyncPendingGameInventory {

    private static final String PENDING_GAME_FIND_ALL_ADDRESS = "server.pending-game-inventory.find-all";
    private static final String PENDING_GAME_ADD_ADDRESS = "server.pending-game-inventory.add";
    private static final String PENDING_GAME_UPDATE_ADDRESS = "server.pending-game-inventory.update";

    private final Client client;

    public AsyncPendingGameInventory(Client client) {
        this.client = client;
    }

    public CompletableFuture<List<PendingGame>> findAll() {

        CompletableFuture<List<PendingGame>> future = new CompletableFuture<>();

        client.messageReceiver().receive(PENDING_GAME_FIND_ALL_ADDRESS, message -> {
            
            if (message.isSuccess()) {
                future.complete((List<PendingGame>) message.content());
            }

        });

        client.messageSender().send(PENDING_GAME_FIND_ALL_ADDRESS, new ArrayList<PendingGame>());

        return future;
    }

    public void add(PendingGame pendingGame) {
        client.messageSender().send(PENDING_GAME_ADD_ADDRESS, pendingGame);
    }

    public void update(PendingGame pendingGame) {
        client.messageSender().send(PENDING_GAME_UPDATE_ADDRESS, pendingGame);
    }

}
