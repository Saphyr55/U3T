package utours.ultimate.client;

import utours.ultimate.core.steorotype.Component;
import utours.ultimate.game.model.Game;
import utours.ultimate.net.Client;
import utours.ultimate.net.data.MessageData;

import java.util.function.Consumer;

@Component
public class ClientService {

    public void joinGame(Client client, Consumer<Game> onJoinGame) {

        client.messageReceiver().receive("server.game.add-game", message -> {
            onJoinGame.accept((Game) message.content());
        });

        client.messageSender().send("server.game.add-game", Game.newDefaultGame());


    }

}
