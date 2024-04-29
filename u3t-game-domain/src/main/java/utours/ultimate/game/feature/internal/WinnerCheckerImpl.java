package utours.ultimate.game.feature.internal;

import utours.ultimate.game.feature.GameService;
import utours.ultimate.game.model.Cell;
import utours.ultimate.game.model.Game;
import utours.ultimate.game.feature.WinnerChecker;

import java.util.*;
import java.util.stream.IntStream;

public class WinnerCheckerImpl implements WinnerChecker {


    private enum MarkType {
        CROSS, ROUND, EMPTY, BOARD;

        static MarkType fromCell(Cell cell) {
            return switch (cell) {
                case Cell.Cross ignored -> MarkType.CROSS;
                case Cell.Round ignored -> MarkType.ROUND;
                case Cell.Empty ignored -> MarkType.EMPTY;
                case Cell.Board ignored -> MarkType.BOARD;
            };
        }

        static MarkType fromGame(Game game, Cell.Pos posOut) {
            Cell[][] cellsOut = game.board().cells();
            Cell cell = cellsOut[posOut.x()][posOut.y()];
            return fromCell(cell);
        }

        static MarkType fromGame(Game game, Cell.Pos posOut, Cell.Pos posIn) {
            Cell[][] cellsOut = game.board().cells();
            Cell cell = cellsOut[posOut.x()][posOut.y()];
            if (cell instanceof Cell.Board(Cell[][] cells)) {
                cell = cells[posIn.x()][posIn.y()];
                return fromCell(cell);
            }
            return fromCell(cell);
        }

    }

    private final GameService gameService;
    private Map<Integer, Set<Cell.Pos>> leftDiagonalCache = new HashMap<>();
    private Map<Integer, Set<Cell.Pos>> rightDiagonalCache = new HashMap<>();


    public WinnerCheckerImpl(GameService gameService) {
        this.gameService = gameService;
    }

    @Override
    public boolean checkInnerWinner(Game game, Cell.Pos posOut, Cell.Pos posIn) {

        Cell cellOut = gameService.cellAt(game, posOut);

        if (cellOut instanceof Cell.Board) {

            Cell cellIn = gameService.cellAt(game, posOut, posIn);

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

    @Override
    public boolean checkWinner(Game game, Cell.Pos pos) {

        Cell cell = gameService.cellAt(game, pos);

        if (cell instanceof Cell.Board) {
            return false;
        }

        MarkType m = MarkType.fromCell(cell);

        Set<Cell.Pos> leftDiagonal = leftDiagonal(game.size());
        Set<Cell.Pos> rightDiagonal = rightDiagonal(game.size());

        List<Cell.Pos> column = IntStream.range(0, game.size())
                .boxed()
                .map(i -> Cell.pos(pos.x(), i))
                .toList();

        List<Cell.Pos> line = IntStream.range(0, game.size())
                .boxed()
                .map(i -> Cell.pos(i, pos.y()))
                .toList();

        if (checkDiagonal(game, pos, m, leftDiagonal)) return true;

        if (checkDiagonal(game, pos, m, rightDiagonal)) return true;

        int i = 0;
        for (Cell.Pos posD : line) {
            if (MarkType.fromGame(game, posD, pos).equals(m)) {
                i++;
            }
        }
        if (i == game.size()) {
            return true;
        }

        i = 0;
        for (Cell.Pos posD : column) {
            if (MarkType.fromGame(game, posD, pos).equals(m)) {
                i++;
            }
        }

        return i == game.size();
    }

    private boolean checkDiagonal(Game game, Cell.Pos posOut, Cell.Pos posIn, MarkType m, Set<Cell.Pos> leftDiagonal) {
        if (leftDiagonal.contains(posIn)) {
            int i = 0;
            for (Cell.Pos pos : leftDiagonal) {
                MarkType mark = MarkType.fromGame(game, posOut, pos);
                if (mark.equals(m)) {
                    i++;
                }
            }
            return i == game.size();
        }
        return false;
    }

    private boolean checkDiagonal(Game game, Cell.Pos posOut, MarkType m, Set<Cell.Pos> leftDiagonal) {
        if (leftDiagonal.contains(posOut)) {
            int i = 0;
            for (Cell.Pos pos : leftDiagonal) {
                MarkType mark = MarkType.fromGame(game, pos);
                if (mark.equals(m)) {
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
