package utours.ultimate.game.model;

import java.io.Serializable;
import java.util.UUID;

public record PendingGame(String gameID,
                          Player currentPlayer,
                          int size)
        implements Serializable {

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {

        private String gameID = UUID.randomUUID().toString();
        private Player currentPlayer;
        private int size;

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
            return new PendingGame(gameID, currentPlayer, size);
        }

    }


}
