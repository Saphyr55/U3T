package utours.ultimate.desktop.action;

import utours.ultimate.core.steorotype.Component;
import utours.ultimate.desktop.controller.MainController;
import utours.ultimate.desktop.controller.PolymorphicController;
import utours.ultimate.desktop.view.DesktopChatView;
import utours.ultimate.desktop.view.PolymorphicView;
import utours.ultimate.ui.OnClickButton;
import utours.ultimate.ui.event.MouseEvent;

import java.util.function.Supplier;

public class OnClickChatNavButton implements OnClickButton {

    private final Supplier<DesktopChatView> factory;
    private final Supplier<PolymorphicView> polymorphicViewSupplier;

    public OnClickChatNavButton(Supplier<DesktopChatView> factory,
                                Supplier<PolymorphicView> polymorphicViewSupplier) {
        this.factory = factory;
        this.polymorphicViewSupplier = polymorphicViewSupplier;
    }

    @Override
    public void performClick(MouseEvent event) {
        polymorphicViewSupplier.get().replaceRegion(factory.get());
    }

}
