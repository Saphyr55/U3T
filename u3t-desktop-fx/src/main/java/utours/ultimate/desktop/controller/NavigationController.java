package utours.ultimate.desktop.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import utours.ultimate.core.steorotype.Component;
import utours.ultimate.desktop.MainApplication;
import utours.ultimate.desktop.view.DesktopNavButton;
import utours.ultimate.desktop.view.DesktopNavButtonContainer;
import utours.ultimate.desktop.view.DesktopNavigationView;
import utours.ultimate.ui.NavButton;

import java.net.URL;
import java.util.Comparator;
import java.util.ResourceBundle;

@Component
public final class NavigationController implements Initializable {

    private final DesktopNavButtonContainer navButtonContainer;

    private @FXML DesktopNavigationView nav;

    public NavigationController(DesktopNavButtonContainer desktopNavButtonContainer) {
        this.navButtonContainer = desktopNavButtonContainer;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        initContainer();

        nav.setContent(navButtonContainer.getVbox());
    }

    private void initContainer() {
        MainApplication.getContext()
                .getContainerReadOnly()
                .getAdditionalComponent(NavButton.class).stream()
                .sorted(Comparator.comparing(NavButton::getPosition))
                .toList()
                .forEach(this::addContainer);
    }

    private void addContainer(NavButton navButton) {
        processDesktopNavButton(navButton);
    }

    private void processDesktopNavButton(NavButton button) {
        navButtonContainer.add(button);
    }

}
