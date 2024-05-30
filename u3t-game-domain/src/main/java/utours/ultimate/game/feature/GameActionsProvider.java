package utours.ultimate.game.feature;

import utours.ultimate.game.model.*;

import java.util.List;

public interface GameActionsProvider {

    List<Action> actions();

    Game game();

    default Board board(GameService gameService) {

        Board board = Board.newEmptyBoard();

        actions().forEach(action -> {

            int outX = action.posOut().x();
            int outY = action.posOut().y();

            Cell cell = board.cells()[outX][outY];

            if (cell instanceof Cell.Board(Cell[][] cells)) {

                int inX = action.posIn().x();
                int inY = action.posIn().y();

                Cell newCell = gameService.cellOfPlayer(game(), action.player());

                cells[inX][inY] = newCell;
            }

        });

        return board;

    }

}
