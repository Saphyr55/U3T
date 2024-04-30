package utours.ultimate.desktop.view;

import javafx.scene.layout.Pane;
import utours.ultimate.desktop.ViewLoader;

public class DesktopChatView extends Pane {

    public DesktopChatView() {
        ViewLoader.load(this, "/views/chat.fxml");
    }

}
