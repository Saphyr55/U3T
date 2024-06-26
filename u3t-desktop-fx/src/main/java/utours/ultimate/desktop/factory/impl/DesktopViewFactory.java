package utours.ultimate.desktop.factory.impl;

import utours.ultimate.core.steorotype.Component;
import utours.ultimate.client.ClientService;
import utours.ultimate.desktop.view.DesktopNavButton;
import utours.ultimate.desktop.view.DesktopNavButtonContainer;
import utours.ultimate.net.Client;
import utours.ultimate.ui.NavButtonContainer;
import utours.ultimate.ui.ViewFactory;

@Component
public class DesktopViewFactory implements ViewFactory {

    public DesktopViewFactory() {
        
    }

    @Override
    public NavButtonContainer createNavButtonContainer() {
        return new DesktopNavButtonContainer();
    }

}
