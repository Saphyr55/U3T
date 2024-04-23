package utours.ultimate.game.model;

public enum U3TGameState {

    Ready {
        @Override
        public U3TGameState nextState() {
            return CrossToPlay;
        }
    },

    CrossToPlay {

        @Override
        public U3TGameState nextState() {
            return RoundToPlay;
        }
    },

    RoundToPlay {
        @Override
        public U3TGameState nextState() {
            return CrossToPlay;
        }
    },

    Finish {
        @Override
        public U3TGameState nextState() {
            return Finish;
        }
    };

    public abstract U3TGameState nextState();

}

