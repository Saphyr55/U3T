package utours.ultimate.game.model;

import java.io.Serializable;
import java.util.UUID;

public record PendingGame(String gameID,
                          Player firstPlayer,
                          Player secondPlayer,
                          int size) implements Serializable {

    public static final int DEFAULT_SIZE = 3;

    public static PendingGame ofDefault() {
        return PendingGame.builder().build();
    }

    public static Builder builder() {
        return new Builder();
    }

    public int totalPlayer() {

        int totalPlayer = 0;

        if (firstPlayer != null)
            totalPlayer++;

        if (secondPlayer != null)
            totalPlayer++;

        return totalPlayer;
    }

    public boolean isFull() {
        return totalPlayer() == 2;
    }

    public static class Builder {

        private String gameID = UUID.randomUUID().toString();
        private Player firstPlayer = null;
        private Player secondPlayer = null;
        private int size = DEFAULT_SIZE;

        public static Builder copyOf(PendingGame pendingGame) {
            return builder()
                    .withSize(pendingGame.size)
                    .withGameID(pendingGame.gameID)
                    .withFirstPlayer(pendingGame.firstPlayer)
                    .withSecondPlayer(pendingGame.secondPlayer);
        }

        public Builder withSecondPlayer(Player secondPlayer) {
            this.secondPlayer = secondPlayer;
            return this;
        }

        public Builder withGameID(String gameID) {
            this.gameID = gameID;
            return this;
        }

        public Builder withFirstPlayer(Player firstPlayer) {
            this.firstPlayer = firstPlayer;
            return this;
        }

        public Builder withSize(int size) {
            this.size = size;
            return this;
        }

        public PendingGame build() {
            return new PendingGame(
                    gameID,
                    firstPlayer,
                    secondPlayer,
                    size
            );
        }

    }


}
