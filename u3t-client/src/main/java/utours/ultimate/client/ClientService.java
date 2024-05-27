package utours.ultimate.client;

import utours.ultimate.core.steorotype.Component;
import utours.ultimate.game.model.Game;
import utours.ultimate.net.Client;

import java.util.function.Consumer;

@Component
public class ClientService {

    public void joinGame(Client client, Consumer<Game> onJoinGame) {

        Game game = Game.newDefaultGame();

        client.messageSender().send("server.game.add-game", game, message -> {
            if (message.isSuccess()) {
                onJoinGame.accept(game);
            }
        });
    }

}
