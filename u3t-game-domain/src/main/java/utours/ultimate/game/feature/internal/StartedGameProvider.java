package utours.ultimate.game.feature.internal;

import utours.ultimate.game.feature.GameProvider;
import utours.ultimate.game.model.Action;
import utours.ultimate.game.model.Game;

import java.util.ArrayList;
import java.util.List;

public class StartedGameProvider implements GameProvider {

    public final Game game;

    public StartedGameProvider(Game game) {
        this.game = game;
    }

    @Override
    public Game game() {
        return game;
    }
}
