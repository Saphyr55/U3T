package utours.ultimate.game.model;

public record Action(
        Player player,
        Cell.Pos posOut,
        Cell.Pos posIn
) {

    public static Action of(Player player, Cell.Pos posOut, Cell.Pos posIn) {
        return new Action(player, posOut, posIn);
    }

}

