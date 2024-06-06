package utours.ultimate.game.model;

import java.io.Serializable;

public record IsWinGame(Game game, boolean isWin)
        implements Serializable {

}
