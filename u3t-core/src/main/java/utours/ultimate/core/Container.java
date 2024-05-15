package utours.ultimate.core;

public interface Container extends ReadOnlyContainer {

    <C> void storeUniqueComponent(Class<C> tClass, C component);

    <C> void removeUniqueComponent(Class<C> tClass);

    <C> void storeAdditionalComponent(Class<C> tClass, C component);

    <C> void removeAdditionalComponents(Class<C> tClass);

    <C> void storeComponent(String id, C component);

}
