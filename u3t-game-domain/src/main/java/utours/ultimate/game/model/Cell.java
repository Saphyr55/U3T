package utours.ultimate.game.model;

import java.io.Serializable;

public sealed interface Cell extends Serializable {

    record Board(Cell[][] cells) implements Cell { }

    record Empty() implements Cell { }

    record Cross() implements Cell { }

    record Round() implements Cell { }

    record Pos(int x, int y) implements Serializable { }

    static Pos pos(int x, int y) {
        return new Pos(x, y);
    }

}
