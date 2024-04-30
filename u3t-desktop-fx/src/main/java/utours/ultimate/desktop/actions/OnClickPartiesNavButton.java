package utours.ultimate.desktop.actions;

import utours.ultimate.desktop.controller.PolymorphicController;
import utours.ultimate.desktop.view.U3TGameView;
import utours.ultimate.ui.OnClickButton;
import utours.ultimate.ui.event.MouseEvent;

public class OnClickPartiesNavButton implements OnClickButton {

    private final PolymorphicController controller;

    public OnClickPartiesNavButton(PolymorphicController controller) {
        this.controller = controller;
    }

    @Override
    public void performClick(MouseEvent event) {
        controller.replaceRegion(new U3TGameView());
    }

}
