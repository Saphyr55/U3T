package utours.ultimate.game.model;

import java.io.Serializable;
import java.util.Optional;

public record Game(Long gameID,
                   Player crossPlayer,
                   Player roundPlayer,
                   Player currentPlayer,
                   Board board,
                   GameState state,
                   Action lastAction,
                   int size
) implements Serializable {
    
    public static Game newDefaultGame() {
        return Builder.newDefaultBuilder().build();
    }
    
    public Game lastAction(Action action) {
        return Game.Builder.copyOf(this)
                .lastAction(action)
                .build();
    }

    public Optional<Action> lastActionOpt() {
        return Optional.ofNullable(lastAction);
    }

    private static long lastGameID = 0;

    public static class Builder {

        private long gameID;
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
                    .board(game.board)
                    .state(game.state)
                    .roundPlayer(game.roundPlayer)
                    .crossPlayer(game.crossPlayer)
                    .lastAction(game.lastAction)
                    .currentPlayer(game.currentPlayer)
                    .size(game.size);
        }

        public static Builder newDefaultBuilder() {
            Builder builder = newBuilder()
                    .roundPlayer(Player.Builder.newBuilder("1", "Player O").build())
                    .crossPlayer(Player.Builder.newBuilder("2", "Player X").build());
            builder.currentPlayer(builder.crossPlayer);
            return builder;

        }

        public static Builder newBuilder() {
            Builder builder = new Builder();
            builder.gameID = lastGameID++;
            return builder;
        }

        private Builder() { }

        public Builder crossPlayer(Player crossPlayer) {
            this.crossPlayer = crossPlayer;
            return this;
        }

        public Builder currentPlayer(Player currentPlayer) {
            this.currentPlayer = currentPlayer;
            return this;
        }

        public Builder roundPlayer(Player roundPlayer) {
            this.roundPlayer = roundPlayer;
            return this;
        }

        public Builder lastAction(Action lastAction) {
            this.lastAction = lastAction;
            return this;
        }

        public Builder board(Board board) {
            this.board = board;
            return this;
        }

        public Builder state(GameState state) {
            this.state = state;
            return this;
        }

        public Builder size(int size) {
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
