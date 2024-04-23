package utours.ultimate.game.feature.internal;

import utours.ultimate.game.feature.U3TGameService;
import utours.ultimate.game.model.Board;
import utours.ultimate.game.model.Cell;
import utours.ultimate.game.model.Player;
import utours.ultimate.game.model.U3TGame;

public class U3TGameServiceInternal implements U3TGameService {

    @Override
    public boolean isPlayableCell(Cell cell) {
        return switch (cell) {
            case Cell.Board board -> false;
            case Cell.Cross cross -> false;
            case Cell.Empty empty -> true;
            case Cell.Round round -> false;
        };
    }

    @Override
    public Cell newCell(U3TGame game, Player currentPlayer) {
        if (game.crossPlayer().equals(currentPlayer)) {
            return new Cell.Cross();
        } else if (game.roundPlayer().equals(currentPlayer)) {
            return new Cell.Round();
        }
        return new Cell.Empty();
    }

    @Override
    public Cell cellAt(U3TGame game, Cell.Pos posOut, Cell.Pos posIn) {

        Cell[][] cellsOut = game.board().cells();
        Cell cellOut = cellsOut[posOut.x()][posOut.y()];

        if (cellOut instanceof Cell.Board board) {
            Cell[][] cellsIn = board.cells();
            return cellsIn[posIn.x()][posIn.y()];
        }

        return cellOut;
    }

    @Override
    public Player oppositePlayer(U3TGame game, Player player) {
        if (game.crossPlayer().equals(player)) {
            return game.roundPlayer();
        } else if (game.roundPlayer().equals(player)) {
            return game.crossPlayer();
        }
        throw new IllegalStateException();
    }

    @Override
    public U3TGame placeMark(U3TGame game, Player currentPlayer, Cell.Pos posOut, Cell.Pos posIn) {

        Cell[][] cellsOut = game.board().cells();
        Cell cellOut = cellsOut[posOut.x()][posOut.y()];

        if (cellOut instanceof Cell.Board board) {
            Cell[][] cellsIn = board.cells();
            Cell cellIn = cellsIn[posIn.x()][posIn.y()];
            if (isPlayableCell(cellIn)) {
                cellsIn[posIn.x()][posIn.y()] = newCell(game, currentPlayer);
            }
        }

        return U3TGame.Builder.copyOf(game)
                .board(new Board(cellsOut))
                .build();
    }

}
