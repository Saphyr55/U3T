package utours.ultimate.desktop.view;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.layout.VBox;
import utours.ultimate.core.steorotype.Component;
import utours.ultimate.ui.NavButtonContainer;

@Component
public class DesktopNavButtonContainer
        implements NavButtonContainer<DesktopNavButton> {

    private final VBox vbox;

    public DesktopNavButtonContainer() {
        this.vbox = new VBox();
        this.vbox.setSpacing(10);
        this.vbox.setPadding(new Insets(24));
        this.vbox.setAlignment(Pos.CENTER);
    }

    public VBox getVbox() {
        return vbox;
    }

    @Override
    public void add(DesktopNavButton navButton) {
        int pos = navButton.getPosition();
        if (pos < 0) {
            vbox.getChildren().add(navButton);
            return;
        }
        vbox.getChildren().add(pos, navButton);
    }

    @Override
    public void remove(DesktopNavButton navButton) {
        vbox.getChildren().remove(navButton);
    }

    @Override
    public void remove(int index) {
        vbox.getChildren().remove(index);
    }


}
