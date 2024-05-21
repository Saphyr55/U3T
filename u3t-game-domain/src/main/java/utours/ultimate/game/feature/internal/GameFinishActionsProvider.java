package utours.ultimate.game.feature.internal;

import utours.ultimate.game.feature.GameActionsProvider;
import utours.ultimate.game.model.*;

import java.util.List;

public class GameFinishActionsProvider implements GameActionsProvider {

    private final List<Action> actions;

    public GameFinishActionsProvider(Game game) {
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
                Action.of(game.crossPlayer(), Cell.pos(2, 2), Cell.pos(1, 0)),
                Action.of(game.roundPlayer(), Cell.pos(2, 2), Cell.pos(2, 1)),
                Action.of(game.crossPlayer(), Cell.pos(2, 1), Cell.pos(1, 2)),
                Action.of(game.roundPlayer(), Cell.pos(1, 2), Cell.pos(1, 2)),
                Action.of(game.crossPlayer(), Cell.pos(0, 1), Cell.pos(2, 1)),
                Action.of(game.roundPlayer(), Cell.pos(2, 1), Cell.pos(2, 2)),
                Action.of(game.crossPlayer(), Cell.pos(2, 2), Cell.pos(1, 2)),
                Action.of(game.roundPlayer(), Cell.pos(0, 0), Cell.pos(1, 2)),
                Action.of(game.crossPlayer(), Cell.pos(0, 1), Cell.pos(2, 0)),
                Action.of(game.roundPlayer(), Cell.pos(2, 0), Cell.pos(2, 0)),
                Action.of(game.crossPlayer(), Cell.pos(2, 0), Cell.pos(0, 2)),
                Action.of(game.roundPlayer(), Cell.pos(0, 2), Cell.pos(0, 2)),
                Action.of(game.crossPlayer(), Cell.pos(2, 1), Cell.pos(0, 1)),
                Action.of(game.roundPlayer(), Cell.pos(2, 1), Cell.pos(0, 2)),
                Action.of(game.crossPlayer(), Cell.pos(2, 1), Cell.pos(2, 1)),
                Action.of(game.roundPlayer(), Cell.pos(2, 1), Cell.pos(2, 0)),
                Action.of(game.crossPlayer(), Cell.pos(2, 0), Cell.pos(0, 0)),
                Action.of(game.roundPlayer(), Cell.pos(2, 0), Cell.pos(2, 1))
        );

    }

    @Override
    public List<Action> actions() {
        return actions;
    }

}
