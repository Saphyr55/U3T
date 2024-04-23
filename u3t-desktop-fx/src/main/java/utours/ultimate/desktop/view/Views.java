package utours.ultimate.desktop.view;

import javafx.fxml.FXMLLoader;
import utours.ultimate.core.ClassPathResource;
import utours.ultimate.desktop.controller.U3TGameController;
import utours.ultimate.game.feature.internal.U3TGameServiceInternal;
import utours.ultimate.game.model.U3TGame;

import java.io.IOException;

public class Views {

    public static final String MAIN_FXML = "/views/main.fxml";

    public static final String U3T_GAME_FXML = "/views/u3t-game.fxml";

    public static <T> T loadU3TGamePane() {
        try {
            var loader = new FXMLLoader(Views.class.getResource(Views.U3T_GAME_FXML));
            var view = loader.<T>load();

            U3TGameController controller = loader.getController();
            controller.setGameService(new U3TGameServiceInternal());
            controller.setGame(U3TGame.Builder.newBuilder().build());

            return view;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }


}
