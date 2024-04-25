package utours.ultimate.core;

public interface Container extends ContainerReadOnly {

    <T> void storeComponent(Class<T> tClass, T component);

    <T> void removeComponent(Class<T> tClass);

    <T> void storeAdditionalComponent(Class<T> tClass, T component);

    <T> void removeAdditionalComponents(Class<T> tClass);

}
