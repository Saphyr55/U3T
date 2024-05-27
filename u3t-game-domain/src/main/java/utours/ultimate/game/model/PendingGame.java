package utours.ultimate.game.model;

import java.io.Serializable;

public record PendingGame(
        Long gameID,
        Player currentPlayer,
        int size
) implements Serializable {

}
