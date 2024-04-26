package utours.ultimate.desktop.factory;

import utours.ultimate.desktop.view.DesktopNavButtonContainer;
import utours.ultimate.ui.NavButton;
import utours.ultimate.ui.NavButtonContainer;
import utours.ultimate.ui.ViewFactory;

public class DesktopViewFactory implements ViewFactory {

    @Override
    public NavButtonContainer<? extends NavButton> createNavButtonContainer() {
        return new DesktopNavButtonContainer();
    }

}
