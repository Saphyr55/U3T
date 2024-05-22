package utours.ultimate.server.internal;

import utours.ultimate.core.steorotype.Component;
import utours.ultimate.core.steorotype.Mapping;
import utours.ultimate.game.model.PendingGame;
import utours.ultimate.game.port.PendingGameInventory;
import utours.ultimate.server.common.MapHelper;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Mapping
@Component
public class PendingGameMemoryInventory implements PendingGameInventory  {

    private final Map<Long, PendingGame> pendingGames = MapHelper.emptyMap();

    @Override
    public List<PendingGame> findAll() {
        return List.of();
    }

    @Override
    public Optional<PendingGame> findById(Long aLong) {
        return Optional.empty();
    }

    @Override
    public void add(PendingGame game) {

    }

    @Override
    public void update(PendingGame game) {

    }

    @Override
    public void remove(PendingGame game) {

    }
}
