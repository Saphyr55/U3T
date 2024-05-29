package utours.ultimate.game.feature;

import utours.ultimate.game.model.Game;
import utours.ultimate.game.model.GameState;

public class GameStatePressedTile implements GameStateHandler {

    private Game game;

    public GameStatePressedTile(Game game) {
        this.game = game;
    }

    public void handle() {
        handle(game.state());
    }

    @Override
    public void handle(GameState state) {
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
        game = Game.Builder.copyOf(game)
                .withState(state.nextState())
                .build();
    }
}
