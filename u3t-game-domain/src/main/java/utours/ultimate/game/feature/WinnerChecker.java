package utours.ultimate.game.feature;

import utours.ultimate.game.model.Cell;
import utours.ultimate.game.model.U3TGame;

public interface WinnerChecker {

    boolean checkInnerWinner(U3TGame game, Cell.Pos posOut, Cell.Pos posIn);

    boolean checkWinner(U3TGame game, Cell.Pos pos);

}
