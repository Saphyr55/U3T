package utours.ultimate.desktop.view;

import javafx.scene.layout.Pane;
import javafx.scene.text.Text;

public class MessageChatPane extends Pane {

    private Text text;
    private boolean isOwn = false;

    public MessageChatPane() {
        this.text = new Text();

        getChildren().add(text);
    }

    public Text getText() {
        return text;
    }

    public boolean isOwn() {
        return isOwn;
    }

    public void setOwn(boolean isOwn) {
        this.isOwn = isOwn;
    }

}
