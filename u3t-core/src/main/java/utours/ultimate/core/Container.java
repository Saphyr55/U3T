package utours.ultimate.core;

public interface Container {

    <T> T getComponent(Class<T> tClass);

    <T> void storeComponent(Class<T> tClass, T component);

}
