package utours.ultimate.server.inventory;

import utours.ultimate.core.steorotype.Component;
import utours.ultimate.core.steorotype.Mapping;
import utours.ultimate.game.model.PendingGame;
import utours.ultimate.game.port.PendingGameInventory;
import utours.ultimate.common.MapHelper;
import utours.ultimate.server.handlers.OnChangedPendingGameInventory;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Mapping
@Component
public class PendingGameMemoryInventory implements PendingGameInventory  {

    private final Map<String, PendingGame> pendingGames = MapHelper.emptyMap();
    private final OnChangedPendingGameInventory onChangedPendingGameInventory;

    public PendingGameMemoryInventory(OnChangedPendingGameInventory onChangedPendingGameInventory) {

        this.onChangedPendingGameInventory = onChangedPendingGameInventory;
    }

    @Override
    public List<PendingGame> findAll() {
        return pendingGames.values().stream().toList();
    }

    @Override
    public Optional<PendingGame> findById(String id) {
        return Optional.of(pendingGames.get(id));
    }

    @Override
    public void add(PendingGame game) {
        pendingGames.putIfAbsent(game.gameID(), game);
        onChangedPendingGameInventory.update(findAll());
    }

    @Override
    public void update(PendingGame game) {
        pendingGames.put(game.gameID(), game);
        onChangedPendingGameInventory.update(findAll());
    }

    @Override
    public void remove(PendingGame game) {
        pendingGames.remove(game.gameID());
        onChangedPendingGameInventory.update(findAll());
    }
    
}