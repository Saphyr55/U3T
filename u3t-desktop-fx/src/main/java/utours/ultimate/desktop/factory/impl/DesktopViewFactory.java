package utours.ultimate.desktop.factory.impl;

import utours.ultimate.core.steorotype.Component;
import utours.ultimate.desktop.view.DesktopNavButton;
import utours.ultimate.desktop.view.DesktopNavButtonContainer;
import utours.ultimate.ui.NavButtonContainer;
import utours.ultimate.ui.ViewFactory;

@Component
public class DesktopViewFactory implements ViewFactory<DesktopNavButton> {

    @Override
    @Component
    public NavButtonContainer<DesktopNavButton> createNavButtonContainer() {
        return new DesktopNavButtonContainer();
    }

}
