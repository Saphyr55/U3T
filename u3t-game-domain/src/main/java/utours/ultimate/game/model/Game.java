package utours.ultimate.game.model;

public record Game(Long gameID,
                   Player crossPlayer,
                   Player roundPlayer,
                   Board board
) {

    private static long lastGameID = 0;

    public static class Builder {

        private long gameID;
        private Player crossPlayer;
        private Player roundPlayer;
        private Board board;

        public static Builder copyOf(Game game) {
            Builder builder = new Builder();
            builder.gameID = game.gameID;
            return builder
                    .board(game.board)
                    .roundPlayer(game.roundPlayer)
                    .crossPlayer(game.crossPlayer);
        }

        public static Builder newBuilder() {
            Builder builder = new Builder();
            builder.gameID = lastGameID++;
            return builder;
        }

        private Builder() {

        }

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

        public Game build() {
            return new Game(gameID, crossPlayer, roundPlayer, board);
        }

    }

}
