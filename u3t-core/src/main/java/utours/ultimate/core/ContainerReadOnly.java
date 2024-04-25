package utours.ultimate.core;

import java.util.List;

public interface ContainerReadOnly {

    <T> List<T> getAdditionalComponent(Class<T> componentClass);

    <T> T getComponent(Class<T> componentClass);

}
