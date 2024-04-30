package utours.ultimate.game.feature;

import utours.ultimate.game.model.*;

public interface GameService {

    boolean isPlayableAction(Game game, Action action);

    boolean isPlayableCell(Cell cell);

    Cell cellOfPlayer(Game game, Player currentPlayer);

    Cell cellAt(Game game, Cell.Pos posOut);

    Cell cellAt(Game game, Cell.Pos posOut, Cell.Pos posIn);

    Game oppositePlayer(Game game);

    Player oppositePlayer(Game game, Player player);

    Game placeMark(Game game, Action action);

    IsWinGame checkInnerWinner(Game game, Action action);

    boolean checkOuterWinner(Game game, Cell.Pos lastPos);

}
