package utours.ultimate.game.feature.internal;

import utours.ultimate.game.feature.U3TGameProvider;
import utours.ultimate.game.model.Action;
import utours.ultimate.game.model.Cell;
import utours.ultimate.game.model.U3TGame;

import java.util.List;

public class U3TGameAlmostFinishProvider implements U3TGameProvider {

    private final List<Action> actions;

    public U3TGameAlmostFinishProvider(U3TGame game) {
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

}
