package utours.ultimate.game.port;


import utours.ultimate.game.model.Game;

import java.util.Optional;

public interface Inventory<T, ID> {

    Optional<T> findById(ID id);

    void add(T game);

    void update(T game);

    void remove(T game);

}
