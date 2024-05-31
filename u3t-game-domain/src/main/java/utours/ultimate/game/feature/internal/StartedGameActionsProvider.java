package utours.ultimate.game.feature.internal;

import utours.ultimate.game.feature.GameActionsProvider;
import utours.ultimate.game.model.Action;
import utours.ultimate.game.model.Game;

import java.util.ArrayList;
import java.util.List;

public class StartedGameActionsProvider implements GameActionsProvider {

    public final Game game;
    private final List<Action> actions;

    public StartedGameActionsProvider(Game game) {
        this.game = game;
        this.actions = new ArrayList<>();
    }

    @Override
    public List<Action> actions() {
        return actions;
    }

    @Override
    public Game game() {
        return game;
    }
}
