package utours.ultimate.game.feature;

import utours.ultimate.game.model.Cell;
import utours.ultimate.game.model.Player;
import utours.ultimate.game.model.U3TGame;

public interface U3TGameService {

    boolean isPlayableCell(Cell cell);

    Cell newCell(U3TGame game, Player currentPlayer);

    Cell cellAt(U3TGame game, Cell.Pos posOut, Cell.Pos posIn);

    Player oppositePlayer(U3TGame game, Player player);

    U3TGame placeMark(U3TGame game, Player currentPlayer, Cell.Pos posOut, Cell.Pos posIn);

}
