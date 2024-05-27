package utours.ultimate.game.model;

import java.io.Serializable;

public enum GameState implements Serializable {

    Ready {
        @Override
        public GameState nextState() {
            return CrossToPlay;
        }
    },

    CrossToPlay {

        @Override
        public GameState nextState() {
            return RoundToPlay;
        }
    },

    RoundToPlay {
        @Override
        public GameState nextState() {
            return CrossToPlay;
        }
    },

    Finish {
        @Override
        public GameState nextState() {
            return Finish;
        }
    };

    public abstract GameState nextState();

}

