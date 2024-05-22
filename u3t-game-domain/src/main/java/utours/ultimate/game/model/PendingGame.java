package utours.ultimate.game.model;

public record PendingGame(
        Long gameID,
        Player currentPlayer,
        int size
) {

}
