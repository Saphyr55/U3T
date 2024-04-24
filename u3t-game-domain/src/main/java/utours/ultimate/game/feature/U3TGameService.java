package utours.ultimate.game.feature;

import utours.ultimate.game.model.*;

public interface U3TGameService {

    boolean isPlayableAction(U3TGame game, Action action);

    boolean isPlayableCell(Cell cell);

    Cell newCell(U3TGame game, Player currentPlayer);

    Cell cellAt(U3TGame game, Cell.Pos posOut);

    Cell cellAt(U3TGame game, Cell.Pos posOut, Cell.Pos posIn);

    Player oppositePlayer(U3TGame game, Player player);

    U3TGame placeMark(U3TGame game, Action action);

    IsWinGame checkInnerWinner(U3TGame game, Action action);

    boolean checkOuterWinner(U3TGame game, Cell.Pos lastPos);

}
