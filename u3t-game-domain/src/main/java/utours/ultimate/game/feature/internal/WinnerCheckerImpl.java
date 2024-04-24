package utours.ultimate.game.feature.internal;

import utours.ultimate.game.model.Board;
import utours.ultimate.game.model.Cell;
import utours.ultimate.game.model.U3TGame;
import utours.ultimate.game.feature.WinnerChecker;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class WinnerCheckerImpl implements WinnerChecker {

    private enum MarkType {
        CROSS, ROUND, EMPTY;

        static MarkType fromCell(Cell cell) {
            return switch (cell) {
                case Cell.Cross ignored -> MarkType.CROSS;
                case Cell.Round ignored -> MarkType.ROUND;
                case Cell.Empty ignored ->  MarkType.EMPTY;
                default -> throw new IllegalStateException("Unexpected value: " + cell);
            };
        }

        static MarkType fromGame(U3TGame game, Cell.Pos posOut, Cell.Pos posIn) {
            Cell[][] cellsOut = game.board().cells();
            Cell cell = cellsOut[posOut.x()][posOut.y()];
            if (cell instanceof Cell.Board(Cell[][] cells)) {
                cell = cells[posIn.x()][posIn.y()];
                return fromCell(cell);
            }
            throw new IllegalStateException("Unexpected value: " + cell);
        }

    }

    private Map<Integer, Set<Cell.Pos>> leftDiagonalCache = new HashMap<>();
    private Map<Integer, Set<Cell.Pos>> rightDiagonalCache = new HashMap<>();

    @Override
    public boolean checkInnerWinner(U3TGame game, Cell.Pos posOut, Cell.Pos posIn) {

        Cell[][] cellsOut = game.board().cells();
        Cell cellOut = cellsOut[posOut.x()][posOut.y()];

        if (cellOut instanceof Cell.Board board) {
            Cell[][] cellsIn = board.cells();
            Cell cellIn = cellsIn[posIn.x()][posIn.y()];

            MarkType m = MarkType.fromCell(cellIn);

            Set<Cell.Pos> leftDiagonal = leftDiagonal(game.size());
            Set<Cell.Pos> rightDiagonal = rightDiagonal(game.size());

            List<Cell.Pos> column = IntStream.range(0, game.size())
                    .boxed()
                    .map(i -> Cell.pos(posIn.x(), i))
                    .toList();

            List<Cell.Pos> line = IntStream.range(0, game.size())
                    .boxed()
                    .map(i -> Cell.pos(i, posIn.y()))
                    .toList();

            if (checkDiagonal(game, posOut, posIn, m, leftDiagonal)) return true;

            if (checkDiagonal(game, posOut, posIn, m, rightDiagonal)) return true;

            int i = 0;
            for (Cell.Pos pos : line) {
                if (MarkType.fromGame(game, posOut, pos).equals(m)) {
                    i++;
                }
            }
            if (i == game.size()) {
                return true;
            }

            i = 0;
            for (Cell.Pos pos : column) {
                if (MarkType.fromGame(game, posOut, pos).equals(m)) {
                    i++;
                }
            }

            return i == game.size();
        }

        return false;
    }

    private boolean checkDiagonal(U3TGame game, Cell.Pos posOut, Cell.Pos posIn, MarkType m, Set<Cell.Pos> leftDiagonal) {
        if (leftDiagonal.contains(posIn)) {
            int i = 0;
            for (Cell.Pos pos : leftDiagonal) {
                if (MarkType.fromGame(game, posOut, pos).equals(m)) {
                    i++;
                }
            }
            return i == game.size();
        }
        return false;
    }


    private Set<Cell.Pos> leftDiagonal(int n) {

        if (leftDiagonalCache.containsKey(n)) {
            return leftDiagonalCache.get(n);
        }

        Set<Cell.Pos> diagonal = new HashSet<>();
        for (int k = 0; k < n; k++) {
            diagonal.add(Cell.pos(k, k));
        }

        leftDiagonalCache.put(n, diagonal);

        return diagonal;
    }

    private Set<Cell.Pos> rightDiagonal(int n) {

        if (rightDiagonalCache.containsKey(n)) {
            return rightDiagonalCache.get(n);
        }

        var xs = IntStream.range(0, n)
                .boxed()
                .toList();

        var ys = IntStream.range(-n + 1, 1)
                .boxed()
                .toList();

        Set<Cell.Pos> diagonal = new HashSet<>();

        for (int k = 0; k < n; k++) {
            int i = xs.get(k);
            int j = ys.get(k);
            diagonal.add(Cell.pos(i, -j));
        }

        rightDiagonalCache.put(n, diagonal);

        return diagonal;
    }


}
