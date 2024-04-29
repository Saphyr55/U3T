package utours.ultimate.desktop.factory;

import utours.ultimate.desktop.view.DesktopNavButton;
import utours.ultimate.desktop.view.DesktopNavButtonContainer;
import utours.ultimate.ui.NavButtonContainer;
import utours.ultimate.ui.ViewFactory;


public class DesktopViewFactory implements ViewFactory<DesktopNavButton> {

    @Override
    public NavButtonContainer<DesktopNavButton> createNavButtonContainer() {
        return new DesktopNavButtonContainer();
    }

}
