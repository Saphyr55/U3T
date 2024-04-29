package utours.ultimate.server.internal;

import utours.ultimate.game.model.Game;
import utours.ultimate.game.port.Inventory;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class GameMemoryInventory implements Inventory<Game, Long> {

    private final Map<Long, Game> parties = new HashMap<>();

    @Override
    public Optional<Game> findById(Long id) {
        return Optional.ofNullable(parties.get(id));
    }

    @Override
    public void add(Game party) {
        parties.put(party.gameID(), party);
    }

    @Override
    public void update(Game party) {
        parties.put(party.gameID(), party);
    }

    @Override
    public void remove(Game party) {
        parties.remove(party.gameID());
    }

}
