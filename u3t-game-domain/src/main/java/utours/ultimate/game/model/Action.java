package utours.ultimate.game.model;

import java.io.Serializable;

public record Action(
        Player player,
        Cell.Pos posOut,
        Cell.Pos posIn
) implements Serializable {

    public static Action of(Player player, Cell.Pos posOut, Cell.Pos posIn) {
        return new Action(player, posOut, posIn);
    }

}

