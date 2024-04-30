package utours.ultimate.desktop.service;

import utours.ultimate.game.model.Game;
import utours.ultimate.net.Client;

import java.util.function.Consumer;

public interface ClientService {

    void joinGame(Client client, Consumer<Game> onJoinGame);

}
