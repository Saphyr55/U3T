package utours.ultimate.game.feature;

import utours.ultimate.game.model.Cell;
import utours.ultimate.game.model.Game;

public interface WinnerChecker {

    boolean checkInnerWinner(Game game, Cell.Pos posOut, Cell.Pos posIn);

    boolean checkWinner(Game game, Cell.Pos pos);

}
