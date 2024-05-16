package utours.ultimate.core;

public interface Container extends ReadOnlyContainer {

    /**
     * Store the unique component that are mapped with the current type passed in parameter.
     *
     * @param cClass Component type class.
     * @param <C> Component type.
     */
    <C> void storeUniqueComponent(Class<C> cClass, C component);

    /**
     * Remove the unique component that are mapped with the current type passed in parameter.
     *
     * @param cClass Component type class.
     * @param <C> Component type.
     */
    <C> void removeUniqueComponent(Class<C> cClass);

    /**
     * Store the additional component that are mapped with the current type passed in parameter.
     *
     * @param cClass Component type class.
     * @param <C> Component type.
     */
    <C> void storeAdditionalComponent(Class<C> cClass, C component);

    /**
     * Remove the additional component that are mapped with the current type passed in parameter.
     *
     * @param cClass Component type class.
     * @param <C> Component type.
     */
    <C> void removeAdditionalComponents(Class<C> cClass);

    /**
     * Store the component that are mapped with the current identifier passed in parameter.
     *
     * @param identifier the component identifier².
     * @param <C> Component type.
     */
    <C> void storeComponent(String identifier, C component);

    /**
     * Remove the component that are mapped with the current identifier passed in parameter.
     *
     * @param identifier the component identifier².
     */
    void removeComponent(String identifier);

}
