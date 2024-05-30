package utours.ultimate.game.feature.internal;

import utours.ultimate.game.feature.GameActionsProvider;
import utours.ultimate.game.model.Action;
import utours.ultimate.game.model.Cell;
import utours.ultimate.game.model.Game;

import java.util.List;

public class GameAlmostFinishActionsProvider implements GameActionsProvider {

    private final List<Action> actions;
    private final Game game;

    public GameAlmostFinishActionsProvider(Game game) {
        this.game = game;
        this.actions = List.of(
                Action.of(game.crossPlayer(), Cell.pos(0, 0), Cell.pos(0, 0)),
                Action.of(game.roundPlayer(), Cell.pos(0, 0), Cell.pos(1, 1)),
                Action.of(game.crossPlayer(), Cell.pos(1, 1), Cell.pos(1, 1)),
                Action.of(game.roundPlayer(), Cell.pos(1, 1), Cell.pos(0, 0)),
                Action.of(game.crossPlayer(), Cell.pos(0, 0), Cell.pos(2, 0)),
                Action.of(game.roundPlayer(), Cell.pos(2, 0), Cell.pos(1, 0)),
                Action.of(game.crossPlayer(), Cell.pos(1, 0), Cell.pos(2, 1)),
                Action.of(game.roundPlayer(), Cell.pos(2, 1), Cell.pos(1, 1)),
                Action.of(game.crossPlayer(), Cell.pos(1, 1), Cell.pos(2, 0)),
                Action.of(game.roundPlayer(), Cell.pos(2, 0), Cell.pos(1, 1)),
                Action.of(game.crossPlayer(), Cell.pos(1, 1), Cell.pos(0, 2)),
                Action.of(game.roundPlayer(), Cell.pos(0, 2), Cell.pos(2, 0)),
                Action.of(game.crossPlayer(), Cell.pos(2, 0), Cell.pos(1, 2)),
                Action.of(game.roundPlayer(), Cell.pos(1, 2), Cell.pos(1, 1)),
                Action.of(game.crossPlayer(), Cell.pos(1, 2), Cell.pos(2, 0)),
                Action.of(game.roundPlayer(), Cell.pos(2, 0), Cell.pos(2, 2)),
                Action.of(game.crossPlayer(), Cell.pos(2, 2), Cell.pos(0, 0)),
                Action.of(game.roundPlayer(), Cell.pos(0, 0), Cell.pos(1, 0)),
                Action.of(game.crossPlayer(), Cell.pos(1, 0), Cell.pos(0, 2)),
                Action.of(game.roundPlayer(), Cell.pos(0, 2), Cell.pos(1, 1)),
                Action.of(game.crossPlayer(), Cell.pos(1, 0), Cell.pos(1, 1)),
                Action.of(game.roundPlayer(), Cell.pos(0, 1), Cell.pos(1, 0)),
                Action.of(game.crossPlayer(), Cell.pos(1, 0), Cell.pos(2, 0)),
                Action.of(game.roundPlayer(), Cell.pos(2, 0), Cell.pos(0, 1)),
                Action.of(game.crossPlayer(), Cell.pos(0, 1), Cell.pos(1, 2)),
                Action.of(game.roundPlayer(), Cell.pos(1, 2), Cell.pos(0, 2)),
                Action.of(game.crossPlayer(), Cell.pos(0, 2), Cell.pos(0, 1)),
                Action.of(game.roundPlayer(), Cell.pos(0, 1), Cell.pos(0, 2)),
                Action.of(game.crossPlayer(), Cell.pos(0, 2), Cell.pos(1, 2)),
                Action.of(game.roundPlayer(), Cell.pos(1, 2), Cell.pos(2, 2)),
                Action.of(game.crossPlayer(), Cell.pos(2, 2), Cell.pos(1, 1)),
                Action.of(game.roundPlayer(), Cell.pos(2, 2), Cell.pos(0, 1)),
                Action.of(game.crossPlayer(), Cell.pos(0, 1), Cell.pos(2, 2)),
                Action.of(game.roundPlayer(), Cell.pos(2, 2), Cell.pos(2, 2)),
                Action.of(game.crossPlayer(), Cell.pos(2, 2), Cell.pos(1, 0))
        );
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
