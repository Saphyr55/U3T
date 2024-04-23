package utours.ultimate.game.model;

public record U3TGame(Long gameID,
                      Player crossPlayer,
                      Player roundPlayer,
                      Board board,
                      U3TGameState state
) {

    private static long lastGameID = 0;

    public static class Builder {

        private long gameID;
        private Player crossPlayer;
        private Player roundPlayer;
        private Board board = Board.newEmptyBoard();
        private U3TGameState state = U3TGameState.Ready;

        public static Builder copyOf(U3TGame game) {
            Builder builder = new Builder();
            builder.gameID = game.gameID;
            return builder
                    .board(game.board)
                    .state(game.state)
                    .roundPlayer(game.roundPlayer)
                    .crossPlayer(game.crossPlayer);
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

        public Builder board(Board board) {
            this.board = board;
            return this;
        }

        public Builder state(U3TGameState state) {
            this.state = state;
            return this;
        }

        public U3TGame build() {
            return new U3TGame(gameID, crossPlayer, roundPlayer, board, state);
        }

    }

}
