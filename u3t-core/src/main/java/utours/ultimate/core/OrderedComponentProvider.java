package utours.ultimate.core;

import java.util.List;

@FunctionalInterface
public interface OrderedComponentProvider {

    List<ComponentId> getOrderedComponents();

}
