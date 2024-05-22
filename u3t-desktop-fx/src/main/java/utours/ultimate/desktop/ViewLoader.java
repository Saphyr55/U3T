package utours.ultimate.desktop;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;

import java.net.URL;
import java.util.Optional;

public final class ViewLoader {

    public static <T extends Node> T load(T root, String path) {
        return load(root, path, "");
    }

    public static <T extends Node> T load(T root, String path, String controllerIdentifier) {
        return load(root, ViewLoader.class.getResource(path), controllerIdentifier);
    }

    public static <T extends Node> T load(T root, URL url, String id) {
        try {
            FXMLLoader loader = new FXMLLoader();

            if (id.isBlank()) {
                loader.setControllerFactory(ViewLoader::getController);
            } else {
                loader.setControllerFactory(ignored -> getComponent(id));
            }

            loader.setLocation(url);
            loader.setRoot(root);

            return loader.load();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private static <T> T getComponent(String name) {
        var context = MainApplication.getContext();
        var container = context.getContainerReadOnly();
        return container.getComponent(name);
    }

    private static Object getController(Class<?> clazz) {
        return Optional.ofNullable(getComponent(clazz.getName()))
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
