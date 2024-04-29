package utours.ultimate.game.model;

import java.util.Optional;

public record U3TGame(Long gameID,
                      Player crossPlayer,
                      Player roundPlayer,
                      Board board,
                      U3TGameState state,
                      Action lastAction,
                      int size
) {

    public U3TGame lastAction(Action action) {
        return U3TGame.Builder.copyOf(this)
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
        private Action lastAction;
        private Board board = Board.newEmptyBoard();
        private U3TGameState state = U3TGameState.Ready;
        private int size = 3;

        public static Builder copyOf(U3TGame game) {
            Builder builder = new Builder();
            builder.gameID = game.gameID;
            return builder
                    .board(game.board)
                    .state(game.state)
                    .roundPlayer(game.roundPlayer)
                    .crossPlayer(game.crossPlayer);
        }

        public static Builder newDefaultBuilder() {
            return newBuilder()
                    .roundPlayer(Player.Builder.newBuilder("1", "Player O").build())
                    .crossPlayer(Player.Builder.newBuilder("2", "Player X").build());
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

        public Builder state(U3TGameState state) {
            this.state = state;
            return this;
        }

        public Builder size(int size) {
            this.size = size;
            return this;
        }

        public U3TGame build() {
            return new U3TGame(gameID, crossPlayer, roundPlayer, board, state, lastAction, size);
        }

    }

}
