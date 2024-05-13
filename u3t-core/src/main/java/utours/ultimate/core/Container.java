package utours.ultimate.core;

public interface Container extends ContainerReadOnly {

    <T> void storeUniqueComponent(Class<T> tClass, T component);

    <T> void removeUniqueComponent(Class<T> tClass);

    <T> void storeAdditionalComponent(Class<T> tClass, T component);

    <T> void removeAdditionalComponents(Class<T> tClass);

    <T> void storeComponent(String id, T component);

}
