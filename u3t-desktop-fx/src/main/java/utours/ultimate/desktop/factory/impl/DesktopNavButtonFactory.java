package utours.ultimate.desktop.factory.impl;

import utours.ultimate.core.steorotype.Component;
import utours.ultimate.core.steorotype.Mapping;
import utours.ultimate.desktop.action.OnClickChatNavButton;
import utours.ultimate.desktop.action.OnClickPartiesNavButton;
import utours.ultimate.desktop.controller.MainController;
import utours.ultimate.desktop.view.DesktopChatView;
import utours.ultimate.desktop.view.DesktopNavButton;
import utours.ultimate.ui.NavButton;
import utours.ultimate.ui.NavButtonFactory;

import java.util.Optional;
import java.util.function.Supplier;

@Component
@Mapping
public final class DesktopNavButtonFactory implements NavButtonFactory {

    private final Supplier<DesktopChatView> chatViewFactory;
    private final MainController mainController;
    private DesktopChatView chatView;

    public DesktopNavButtonFactory(MainController mainController) {

        this.mainController = mainController;
        this.chatViewFactory = () -> chatView = Optional
                .ofNullable(chatView)
                .orElseGet(DesktopChatView::new);
    }

    @Override
    @Component
    @Mapping(type = Mapping.Type.Additional)
    public NavButton createPartiesNavButton() {

        DesktopNavButton button = new DesktopNavButton("Parties", 0);
        button.setOnClick(new OnClickPartiesNavButton(mainController::getMainLeftPolymorphic));

        return button;
    }

    @Override
    @Component
    @Mapping(type = Mapping.Type.Additional)
    public NavButton createChatNavButton() {

        DesktopNavButton button = new DesktopNavButton("Chat", 1);
        button.setOnClick(new OnClickChatNavButton(chatViewFactory, mainController::getMainLeftPolymorphic));

        return button;
    }

    @Override
    @Component
    @Mapping(type = Mapping.Type.Additional)
    public NavButton createHistoryNavButton() {
        return new DesktopNavButton("History", 2);
    }

    @Override
    @Component
    @Mapping(type = Mapping.Type.Additional)
    public NavButton createSettingsNavButton() {
        return new DesktopNavButton("Settings", 3);
    }

}
