package utours.ultimate.desktop.action;

import utours.ultimate.desktop.view.DesktopPartiesView;
import utours.ultimate.desktop.view.PolymorphicView;
import utours.ultimate.ui.OnClickButton;
import utours.ultimate.ui.event.MouseEvent;

import java.util.function.Supplier;

public class OnClickPartiesNavButton implements OnClickButton {

    private final Supplier<PolymorphicView> polymorphicViewSupplier;

    public OnClickPartiesNavButton(Supplier<PolymorphicView> polymorphicViewSupplier) {
        this.polymorphicViewSupplier = polymorphicViewSupplier;
    }

    @Override
    public void performClick(MouseEvent event) {
        polymorphicViewSupplier.get().replaceRegion(new DesktopPartiesView());
    }

}
