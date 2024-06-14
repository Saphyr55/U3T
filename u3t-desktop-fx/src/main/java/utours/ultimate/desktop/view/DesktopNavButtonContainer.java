package utours.ultimate.desktop.view;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import utours.ultimate.core.steorotype.Component;
import utours.ultimate.ui.NavButton;
import utours.ultimate.ui.NavButtonContainer;

import javax.print.attribute.standard.MediaSize;

@Component
public class DesktopNavButtonContainer implements NavButtonContainer {

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
    public void add(NavButton navButton) {

        int pos = navButton.getPosition();

        DesktopNavButton button = new DesktopNavButton(
                navButton.getName(),
                navButton.getPosition()
        );

        button.setOnClick(navButton.getOnClick());

        if (pos < 0) {
            vbox.getChildren().add(button);
            return;
        }

        vbox.getChildren().add(pos, button);
    }

    @Override
    public void remove(NavButton navButton) {
        remove(navButton.getPosition());
    }

    @Override
    public void remove(int index) {
        vbox.getChildren().remove(index);
    }


}
