package utours.ultimate.desktop.view;

import javafx.scene.layout.Pane;
import utours.ultimate.desktop.ViewLoader;
import utours.ultimate.game.model.Game;

public class U3TGameView extends Pane {

    public U3TGameView() {
        ViewLoader.load(this, "/views/u3t-game.fxml");
    }

}
