package utours.ultimate.game.feature.internal;

import utours.ultimate.game.feature.GameService;
import utours.ultimate.game.feature.WinnerChecker;
import utours.ultimate.game.model.*;

public class GameServiceInternal implements GameService {

    @Override
    public boolean isPlayableAction(Game game, Action action) {

        if (game.lastActionOpt().isEmpty()) return true;

        Action lastAction = game.lastAction();

        Cell cellOut = cellAt(game, action.posOut());
        Cell cellLastOut = cellAt(game, lastAction.posIn());

        if (cellOut instanceof Cell.Board(Cell[][] cellsIn)) {
            var isCellPlayable = isPlayableCell(cellsIn[action.posIn().x()][action.posIn().y()]);
            var isWonCell = cellLastOut instanceof Cell.Cross || cellLastOut instanceof Cell.Round;
            if (!action.posOut().equals(lastAction.posIn()) && isWonCell) {
                return isCellPlayable;
            }
            return isCellPlayable && action.posOut().equals(lastAction.posIn());
        }

        return false;
    }

    @Override
    public boolean isPlayableCell(Cell cell) {
        return cell instanceof Cell.Empty;
    }

    @Override
    public Cell newCell(Game game, Player currentPlayer) {
        if (game.crossPlayer().equals(currentPlayer)) {
            return new Cell.Cross();
        } else if (game.roundPlayer().equals(currentPlayer)) {
            return new Cell.Round();
        }
        return new Cell.Empty();
    }

    @Override
    public Cell cellAt(Game game, Cell.Pos posOut) {
        Cell[][] cellsOut = game.board().cells();
        return cellsOut[posOut.x()][posOut.y()];
    }

    @Override
    public Cell cellAt(Game game, Cell.Pos posOut, Cell.Pos posIn) {

        Cell cellOut = cellAt(game, posOut);

        if (cellOut instanceof Cell.Board board) {
            Cell[][] cellsIn = board.cells();
            return cellsIn[posIn.x()][posIn.y()];
        }

        return cellOut;
    }

    @Override
    public Game oppositePlayer(Game game) {
        Player currentPlayer = oppositePlayer(game, game.currentPlayer());
        return Game.Builder.copyOf(game)
                .currentPlayer(currentPlayer)
                .build();
    }

    @Override
    public Player oppositePlayer(Game game, Player player) {
        if (game.crossPlayer().equals(player)) {
            return game.roundPlayer();
        } else if (game.roundPlayer().equals(player)) {
            return game.crossPlayer();
        }
        throw new IllegalStateException();
    }

    @Override
    public Game placeMark(Game game, Action action) {

        Cell[][] cellsOut = game.board().cells();
        Cell cellOut = cellAt(game, action.posOut());

        if (cellOut instanceof Cell.Board board) {
            Cell[][] cellsIn = board.cells();
            Cell cellIn = cellsIn[action.posIn().x()][action.posIn().y()];
            if (isPlayableCell(cellIn)) {
                cellsIn[action.posIn().x()][action.posIn().y()] = newCell(game, action.player());
            }
        }

        return Game.Builder.copyOf(game)
                .board(new Board(cellsOut))
                .build();
    }

    @Override
    public IsWinGame checkInnerWinner(Game game, Action action) {
        WinnerChecker checker = new WinnerCheckerImpl(this);
        boolean isWin = checker.checkInnerWinner(game, action.posOut(), action.posIn());
        if (isWin) {
            Cell[][] cellsOut = game.board().cells();
            cellsOut[action.posOut().x()][action.posOut().y()] = newCell(game, action.player());
        }
        return new IsWinGame(game, isWin);
    }

    @Override
    public boolean checkOuterWinner(Game game, Cell.Pos lastPos) {
        WinnerChecker checker = new WinnerCheckerImpl(this);
        return checker.checkWinner(game, lastPos);
    }

}
