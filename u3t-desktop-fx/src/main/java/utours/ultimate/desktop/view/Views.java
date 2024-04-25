package utours.ultimate.desktop.view;

import javafx.fxml.FXMLLoader;

import utours.ultimate.desktop.controller.U3TGameController;
import utours.ultimate.game.feature.internal.U3TGameServiceInternal;
import utours.ultimate.game.model.Player;
import utours.ultimate.game.model.U3TGame;

import java.io.IOException;

public class Views {

    public static final String MAIN_FXML = "/views/main.fxml";

    public static final String U3T_GAME_FXML = "/views/u3t-game.fxml";

    public static final String U3T_GAME_WON_FXML = "/views/u3t-game-won.fxml";

    public static <T> T loadU3TGamePane() {
        try {
            var loader = new FXMLLoader(Views.class.getResource(Views.U3T_GAME_FXML));

            U3TGameController controller = new U3TGameController();
            controller.setGameService(new U3TGameServiceInternal());
            controller.setGame(U3TGame.Builder.newBuilder()
                    .roundPlayer(Player.Builder.newBuilder("1", "Player O").build())
                    .crossPlayer(Player.Builder.newBuilder("2", "Player X").build())
                    .build());

            loader.setController(controller);

            return loader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }


}
