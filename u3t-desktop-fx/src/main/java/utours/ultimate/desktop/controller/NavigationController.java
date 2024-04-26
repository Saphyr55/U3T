package utours.ultimate.desktop.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import utours.ultimate.core.ContainerReadOnly;
import utours.ultimate.desktop.MainApplication;
import utours.ultimate.desktop.view.DesktopNavButton;
import utours.ultimate.desktop.view.DesktopNavButtonContainer;
import utours.ultimate.desktop.view.DesktopNavigationView;
import utours.ultimate.ui.NavButton;

import java.net.URL;
import java.util.ResourceBundle;

public class NavigationController implements Initializable {

    private final DesktopNavButtonContainer navButtonContainer;

    @FXML
    private DesktopNavigationView nav;

    public NavigationController(DesktopNavButtonContainer desktopNavButtonContainer) {
        this.navButtonContainer = desktopNavButtonContainer;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        ContainerReadOnly container = MainApplication.getContext().getContainerReadOnly();

        container.getAdditionalComponent(NavButton.class).forEach(this::addContainer);

        nav.setContent(navButtonContainer.getVbox());
    }

    private void addContainer(NavButton navButton) {
        if (navButton instanceof DesktopNavButton desktopNavButton) {
            navButtonContainer.add(desktopNavButton);
        } else {
            throw new IllegalStateException();
        }
    }

}
