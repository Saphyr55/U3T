package utours.ultimate.server.inventory;

import utours.ultimate.common.MapHelper;
import utours.ultimate.core.steorotype.Component;
import utours.ultimate.core.steorotype.Mapping;
import utours.ultimate.game.model.Game;
import utours.ultimate.game.port.GameInventory;
import utours.ultimate.server.handlers.OnChangedGameInventory;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Mapping
@Component
public class GameMemoryInventory implements GameInventory {

    private final Map<String, Game> games = MapHelper.emptyMap();
    private final OnChangedGameInventory onChangedGameInventory;

    public GameMemoryInventory(OnChangedGameInventory onChangedGameInventory) {
        this.onChangedGameInventory = onChangedGameInventory;
    }

    @Override
    public List<Game> findAll() {
        return games.values().stream().toList();
    }

    @Override
    public Optional<Game> findById(String id) {
        return Optional.ofNullable(games.get(id));
    }

    @Override
    public void add(Game party) {
        games.putIfAbsent(party.gameID(), party);
        onChangedGameInventory.update(findAll());
    }
    
    @Override
    public void update(Game party) {
        games.put(party.gameID(), party);
        onChangedGameInventory.update(findAll());
    }

    @Override
    public void remove(Game party) {
        games.remove(party.gameID());
        onChangedGameInventory.update(findAll());
    }

}
