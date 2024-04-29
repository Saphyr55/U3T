package utours.ultimate.desktop;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;

import java.net.URL;
import java.util.Optional;

public class ViewLoader {

    public static <T> T  load(T node, String path) {
        return load(node, ViewLoader.class.getResource(path));
    }

    public static <T> T load(T node, URL url) {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(url);
            loader.setControllerFactory(ViewLoader::getController);
            loader.setRoot(node);
            return loader.load();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private static <T> T getComponent(Class<T> tClass) {
        return MainApplication
                .getContext()
                .getContainerReadOnly()
                .getUniqueComponent(tClass);
    }

    private static Object getController(Class<?> clazz) {
        return Optional.ofNullable(getComponent(clazz))
                .orElseGet(() -> instantiate(clazz));
    }

    @SuppressWarnings("unchecked")
    private static <T> T instantiate(Class<?> clazz) {
        try {
            return (T) clazz.newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

}
