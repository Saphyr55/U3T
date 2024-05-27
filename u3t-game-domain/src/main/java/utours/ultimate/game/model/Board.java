package utours.ultimate.game.model;

import java.io.Serializable;
import java.util.Arrays;

public record Board(
    Cell[][] cells
) implements Serializable {

    public static Board newEmptyBoard() {
        return newEmptyBoard(3);
    }

    public static Board newEmptyBoard(int size) {

        Cell[][] primitiveCells = new Cell[size][size];
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                primitiveCells[i][j] = new Cell.Empty();
            }
        }

        Cell[][] cells = new Cell[size][size];
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {

                var newPrimitiveCells = Arrays.stream(primitiveCells)
                        .map(Cell[]::clone)
                        .toArray(Cell[][]::new);

                cells[i][j] = new Cell.Board(newPrimitiveCells);
            }
        }

        return new Board(cells);
    }

}
