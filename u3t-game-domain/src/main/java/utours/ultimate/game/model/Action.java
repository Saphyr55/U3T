package utours.ultimate.game.model;

public record Action(
        Player player,
        Cell.Pos posOut,
        Cell.Pos posIn
) {
    
}

