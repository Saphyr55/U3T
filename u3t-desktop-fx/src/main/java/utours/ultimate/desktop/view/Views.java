package utours.ultimate.desktop.view;

import javafx.fxml.FXMLLoader;
import utours.ultimate.core.ClassPathResource;

import java.io.IOException;

public class Views {

    public static final String MAIN_FXML = "/views/main.fxml";

    public static final String U3T_GAME_FXML = "/views/u3t-game.fxml";

    public static <T> T loadU3TGamePane() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(Views.class.getResource(Views.U3T_GAME_FXML));
            return fxmlLoader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


}
