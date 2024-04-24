package utours.ultimate.game.feature;

import utours.ultimate.game.model.U3TGame;
import utours.ultimate.game.model.U3TGameState;

public class U3TGameStatePressedTile implements U3TGameStateHandler {

    private U3TGame game;

    public U3TGameStatePressedTile(U3TGame game) {
        this.game = game;
    }

    public void handle() {
        handle(game.state());
    }

    @Override
    public void handle(U3TGameState state) {
        switch (state) {
            case Ready -> {

            }
            case CrossToPlay -> {
                
            }
            case RoundToPlay -> {

            }
            case Finish -> {

            }
        }
        game = U3TGame.Builder.copyOf(game)
                .state(state.nextState())
                .build();
    }
}
