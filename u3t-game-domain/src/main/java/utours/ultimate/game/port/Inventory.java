package utours.ultimate.game.port;

import java.util.List;
import java.util.Optional;

public interface Inventory<T, ID> {

    List<T> findAll();

    Optional<T> findById(ID id);

    void add(T game);

    void update(T game);

    void remove(T game);

}
