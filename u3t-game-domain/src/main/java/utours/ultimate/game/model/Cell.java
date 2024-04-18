package utours.ultimate.game.model;

public sealed interface Cell {

    record Empty() implements Cell { }

    record Cross() implements Cell { }

    record Round() implements Cell { }

}
