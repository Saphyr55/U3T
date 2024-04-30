package utours.ultimate.desktop.actions;

import utours.ultimate.desktop.controller.PolymorphicController;
import utours.ultimate.desktop.view.DesktopChatView;
import utours.ultimate.ui.OnClickButton;
import utours.ultimate.ui.event.MouseEvent;

import java.util.function.Supplier;

public class OnClickChatNavButton implements OnClickButton {

    private final Supplier<DesktopChatView> factory;
    private final PolymorphicController controller;

    public OnClickChatNavButton(Supplier<DesktopChatView> factory,
                                PolymorphicController controller) {
        this.factory = factory;
        this.controller = controller;
    }

    @Override
    public void performClick(MouseEvent event) {
        controller.replaceRegion(factory.get());
    }

}
