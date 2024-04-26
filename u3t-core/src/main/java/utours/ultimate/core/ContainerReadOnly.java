package utours.ultimate.core;

import java.util.List;

public interface ContainerReadOnly {

    <T> List<T> getAdditionalComponent(Class<T> componentClass);

    <T> T getUniqueComponent(Class<T> componentClass);

    <T> T getComponent(String identifier);

}
