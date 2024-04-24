package utours.ultimate.game.feature;

import utours.ultimate.game.model.*;

import java.util.List;

public interface U3TGameProvider {

    List<Action> actions();

    default Board board(U3TGame game, U3TGameService gameService) {

        Board board = Board.newEmptyBoard();

        actions().forEach(action -> {
            var cell = board.cells()[action.posOut().x()][action.posOut().y()];
            if (cell instanceof Cell.Board(Cell[][] cells)) {
                Cell newCell = gameService.newCell(game, action.player());
                cells[action.posIn().x()][action.posIn().y()] = newCell;
            }
        });

        return board;

    }

}
