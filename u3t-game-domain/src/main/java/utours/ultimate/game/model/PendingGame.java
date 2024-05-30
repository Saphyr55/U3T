package utours.ultimate.game.model;

import java.io.Serializable;
import java.util.UUID;

public record PendingGame(String gameID,
                          Player currentPlayer,
                          Player secondPlayer,
                          int size) implements Serializable {

    public static final int DEFAULT_SIZE = 3;

    public static Builder builder() {
        return new Builder();
    }

    public int totalPlayer() {

        int totalPlayer = 0;

        if (currentPlayer != null)
            totalPlayer++;

        if (secondPlayer != null)
            totalPlayer++;

        return totalPlayer;
    }

    public static class Builder {

        private String gameID = UUID.randomUUID().toString();
        private Player currentPlayer;
        private Player secondPlayer = null;
        private int size = DEFAULT_SIZE;

        public Builder withSecondPlayer(Player secondPlayer) {
            this.secondPlayer = secondPlayer;
            return this;
        }

        public Builder withGameID(String gameID) {
            this.gameID = gameID;
            return this;
        }

        public Builder withCurrentPlayer(Player currentPlayer) {
            this.currentPlayer = currentPlayer;
            return this;
        }

        public Builder withSize(int size) {
            this.size = size;
            return this;
        }

        public PendingGame build() {
            return new PendingGame(
                    gameID,
                    currentPlayer,
                    secondPlayer,
                    size
            );
        }

    }


}
