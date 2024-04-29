package utours.ultimate.desktop.view;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.layout.VBox;
import utours.ultimate.ui.NavButtonContainer;

import java.util.List;

public class DesktopNavButtonContainer implements NavButtonContainer<DesktopNavButton> {

    private VBox vbox;

    public DesktopNavButtonContainer() {
        this.vbox = new VBox();
        this.vbox.setSpacing(10);
        this.vbox.setPadding(new Insets(24));
        this.vbox.setAlignment(Pos.CENTER);
    }

    public VBox getVbox() {
        return vbox;
    }

    public void setVbox(VBox vbox) {
        this.vbox = vbox;
    }

    @Override
    public void add(DesktopNavButton navButton) {
        if (navButton.position() < 0)
            vbox.getChildren().add(navButton);
        else
            vbox.getChildren().add(navButton.position(), navButton);
    }

    @Override
    public void remove(DesktopNavButton navButton) {

    }

    @Override
    public void remove(int index) {

    }


}
