package utours.ultimate.client;

import utours.ultimate.core.steorotype.Component;
import utours.ultimate.game.model.Game;
import utours.ultimate.net.Client;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@Component
public class AsyncGameInventory {

    private static final String GAME_FIND_ALL_ADDRESS = "server.game-inventory.find-all";
    private static final String GAME_ADD_ADDRESS = "server.game-inventory.add";
    private static final String GAME_UPDATE_ADDRESS = "server.game-inventory.update";

    private final Client client;

    public AsyncGameInventory(Client client) {
        this.client = client;
    }

    public CompletableFuture<List<Game>> findAll() {

        CompletableFuture<List<Game>> future = new CompletableFuture<>();

        client.messageReceiver().onReceive(GAME_FIND_ALL_ADDRESS, message -> {

            if (message.isSuccess()) {
                future.complete((List<Game>) message.content());
            }

        });

        client.messageSender().send(GAME_FIND_ALL_ADDRESS, new ArrayList<Game>());

        return future;
    }

    public void add(Game game) {
        client.messageSender().send(GAME_ADD_ADDRESS, game);
    }

    public void update(Game game) {
        client.messageSender().send(GAME_UPDATE_ADDRESS.formatted(game.gameID()), game);
    }


}
