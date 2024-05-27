package utours.ultimate.server.internal;

import utours.ultimate.common.MapHelper;
import utours.ultimate.core.steorotype.Component;
import utours.ultimate.core.steorotype.Mapping;
import utours.ultimate.game.model.Game;
import utours.ultimate.game.port.GameInventory;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Mapping
@Component
public class GameMemoryInventory implements GameInventory {

    private final Map<Long, Game> games = MapHelper.emptyMap();

    public GameMemoryInventory() { }

    @Override
    public List<Game> findAll() {
        return games.values().stream().toList();
    }

    @Override
    public Optional<Game> findById(Long id) {
        return Optional.ofNullable(games.get(id));
    }

    @Override
    public void add(Game party) {
        games.putIfAbsent(party.gameID(), party);
    }
    
    @Override
    public void update(Game party) {
        games.put(party.gameID(), party);
    }

    @Override
    public void remove(Game party) {
        games.remove(party.gameID());
    }

}
