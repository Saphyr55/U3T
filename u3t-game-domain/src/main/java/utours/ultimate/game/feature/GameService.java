package utours.ultimate.game.feature;

import utours.ultimate.game.model.*;

public interface GameService {

    /**
     * 
     * @param game
     * @param action
     * @return
     */
    boolean isPlayableAction(Game game, Action action);

    /**
     *
     * @param cell
     * @return
     */
    boolean isPlayableCell(Cell cell);

    /**
     *
     * @param game
     * @param currentPlayer
     * @return
     */
    Cell cellOfPlayer(Game game, Player currentPlayer);

    /**
     *
     * @param game
     * @param posOut
     * @return
     */
    Cell cellAt(Game game, Cell.Pos posOut);

    /**
     *
     * @param game
     * @param posOut
     * @param posIn
     * @return
     */
    Cell cellAt(Game game, Cell.Pos posOut, Cell.Pos posIn);

    /**
     *
     * @param game
     * @return
     */
    Game turnPlayer(Game game);

    /**
     *
     * @param game
     * @param player
     * @return
     */
    Player oppositePlayer(Game game, Player player);

    /**
     *
     * @param game
     * @param action
     * @return
     */
    Game placeMark(Game game, Action action);

    /**
     *
     * @param game
     * @param action
     * @return
     */
    IsWinGame checkInnerWinner(Game game, Action action);

    /**
     *
     * @param game
     * @param lastPos
     * @return
     */
    boolean checkOuterWinner(Game game, Cell.Pos lastPos);

    /**
     *
     * @param game
     * @param action
     * @return
     */
    default Game performAction(Game game, Action action) {

        if (!isPlayableAction(game, action)) {
            return game;
        }

        game = placeMark(game, action);
        IsWinGame isWinGameInner = checkInnerWinner(game, action);
        game = isWinGameInner.game();
        game = game.addAction(action);

        return turnPlayer(game);
    }


}
