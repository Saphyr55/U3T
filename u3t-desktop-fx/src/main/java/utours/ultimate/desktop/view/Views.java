package utours.ultimate.desktop.view;

import javafx.fxml.FXMLLoader;

import utours.ultimate.desktop.controller.MainController;
import utours.ultimate.desktop.controller.NavigationController;
import utours.ultimate.desktop.controller.U3TGameController;
import utours.ultimate.game.feature.U3TGameService;
import utours.ultimate.game.feature.internal.U3TGameServiceInternal;
import utours.ultimate.game.model.Player;
import utours.ultimate.game.model.U3TGame;

import java.io.IOException;

public class Views {

    public static final String NAVIGATION_FXML = "/views/navigation.fxml";

    public static final String MAIN_FXML = "/views/main.fxml";

    public static final String U3T_GAME_FXML = "/views/u3t-game.fxml";

    public static final String U3T_GAME_WON_FXML = "/views/u3t-game-won.fxml";

    public static <T> T loadU3TGamePane() {
        try {
            var loader = new FXMLLoader(Views.class.getResource(Views.U3T_GAME_FXML));

            U3TGame game = U3TGame.Builder.newBuilder()
                    .roundPlayer(Player.Builder.newBuilder("1", "Player O").build())
                    .crossPlayer(Player.Builder.newBuilder("2", "Player X").build())
                    .build();

            U3TGameService service = new U3TGameServiceInternal();
            U3TGameController controller = new U3TGameController(service, game, game.crossPlayer());

            loader.setController(controller);

            return loader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    public static void loadNavigationView(DesktopNavigationView view) {
        try {
            NavigationController controller = new NavigationController(new DesktopNavButtonContainer());
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Views.class.getResource(Views.NAVIGATION_FXML));
            loader.setControllerFactory(aClass -> {
                try {
                    if (aClass.equals(controller.getClass())) {
                        return controller;
                    }
                    return aClass.newInstance();
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            });
            loader.setRoot(view);
            loader.load();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static void loadMainView(DesktopMainView view) {
        try {
            MainController controller = new MainController();
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Views.class.getResource(Views.MAIN_FXML));
            loader.setController(controller);
            loader.setRoot(view);
            loader.load();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
