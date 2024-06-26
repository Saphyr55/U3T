package utours.ultimate.core;

import java.util.List;

/**
 *
 */
public interface ReadOnlyContainer {

    /**
     * Get a component list that are mapped with the current type passed in parameter.
     *
     * @param cClass Component type class.
     * @return A component list
     * @param <C> Component type.
     */
    <C> List<C> getAdditionalComponent(Class<C> cClass);

    /**
     * Get the unique component that is mapped with the current type passed in parameter.
     *
     * @param cClass Component type class.
     * @return A component.
     * @param <C> Component.
     */
    <C> C getUniqueComponent(Class<C> cClass);

    /**
     * Get the unique component that is mapped with the current identifier passed in parameter.
     *
     * @param identifier The component identifier.
     * @return A component.
     * @param <C> Component.
     */
    <C> C getComponent(String identifier);

}
