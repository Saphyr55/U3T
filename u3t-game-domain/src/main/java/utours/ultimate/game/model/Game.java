package utours.ultimate.game.model;

import utours.ultimate.common.ListHelper;

import java.io.Serializable;
import java.util.*;

public record Game(String gameID,
                   Player crossPlayer,
                   Player roundPlayer,
                   Player currentPlayer,
                   Board board,
                   GameState state,
                   List<Action> actions,
                   int size
) implements Serializable {

    public static Builder builder() {
        return new Builder();
    }

    public Game addAction(Action action) {
        return Game.Builder.copyOf(this)
                .withActions(ListHelper.append(actions, action))
                .build();
    }

    public Action lastAction() {
        return actions.getLast();
    }

    public Optional<Action> lastActionOpt() {
        if (actions.isEmpty()) return Optional.empty();
        return Optional.of(lastAction());
    }

    public static class Builder {

        private String gameID = UUID.randomUUID().toString();
        private Player crossPlayer;
        private Player roundPlayer;
        private Player currentPlayer;
        private List<Action> actions = new ArrayList<>();
        private Board board = Board.newEmptyBoard();
        private GameState state = GameState.Ready;
        private int size = 3;

        public static Builder copyOf(Game game) {
            return builder()
                    .withGameID(game.gameID)
                    .withBoard(game.board)
                    .withState(game.state)
                    .withRoundPlayer(game.roundPlayer)
                    .withCrossPlayer(game.crossPlayer)
                    .withActions(game.actions)
                    .withCurrentPlayer(game.currentPlayer)
                    .withSize(game.size);
        }

        private Builder() { }

        public Builder withGameID(String gameID) {
            this.gameID = gameID;
            return this;
        }

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

        public Builder withActions(List<Action> actions) {
            this.actions = actions;
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

            return new Game(gameID, crossPlayer, roundPlayer, currentPlayer, board, state, actions, size);
        }

    }

}
