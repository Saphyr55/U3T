package utours.ultimate.core;

public interface ComponentFactory {

    <T> T createComponent(ContainerReadOnly container, Class<T> t, Object... args);

}
