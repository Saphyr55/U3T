package utours.ultimate.game.model;

import java.io.Serializable;
import java.util.Optional;
import java.util.UUID;

public record Game(String gameID,
                   Player crossPlayer,
                   Player roundPlayer,
                   Player currentPlayer,
                   Board board,
                   GameState state,
                   Action lastAction,
                   int size
) implements Serializable {

    public static Builder builder() {
        return new Builder();
    }

    public static Game defaultGame() {
        return Builder.defaultBuilder().build();
    }
    
    public Game lastAction(Action action) {
        return Game.Builder.copyOf(this)
                .withLastAction(action)
                .build();
    }

    public Optional<Action> lastActionOpt() {
        return Optional.ofNullable(lastAction);
    }

    private static long lastGameID = 0;

    public static class Builder {

        private String gameID = UUID.randomUUID().toString();
        private Player crossPlayer;
        private Player roundPlayer;
        private Player currentPlayer;
        private Action lastAction;
        private Board board = Board.newEmptyBoard();
        private GameState state = GameState.Ready;
        private int size = 3;

        public static Builder copyOf(Game game) {

            Builder builder = new Builder();
            builder.gameID = game.gameID;

            return builder
                    .withBoard(game.board)
                    .withState(game.state)
                    .withRoundPlayer(game.roundPlayer)
                    .withCrossPlayer(game.crossPlayer)
                    .withLastAction(game.lastAction)
                    .withCurrentPlayer(game.currentPlayer)
                    .withSize(game.size);

        }

        public static Builder defaultBuilder() {
            Builder builder = builder()
                    .withRoundPlayer(Player.Builder.newBuilder("1", "Player O").build())
                    .withCrossPlayer(Player.Builder.newBuilder("2", "Player X").build());
            builder.withCurrentPlayer(builder.crossPlayer);
            return builder;

        }

        private Builder() { }

        public Builder withCrossPlayer(Player crossPlayer) {
            this.crossPlayer = crossPlayer;
            return this;
        }

        public Builder withCurrentPlayer(Player currentPlayer) {
            this.currentPlayer = currentPlayer;
            return this;
        }

        public Builder withRoundPlayer(Player roundPlayer) {
            this.roundPlayer = roundPlayer;
            return this;
        }

        public Builder withLastAction(Action lastAction) {
            this.lastAction = lastAction;
            return this;
        }

        public Builder withBoard(Board board) {
            this.board = board;
            return this;
        }

        public Builder withState(GameState state) {
            this.state = state;
            return this;
        }

        public Builder withSize(int size) {
            this.size = size;
            return this;
        }

        public Game build() {

            if (currentPlayer == null)
                currentPlayer = crossPlayer;

            return new Game(gameID, crossPlayer, roundPlayer, currentPlayer, board, state, lastAction, size);
        }

    }

}
