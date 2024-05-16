package utours.ultimate.desktop.factory.impl;

import utours.ultimate.core.steorotype.Component;
import utours.ultimate.core.steorotype.Mapping;
import utours.ultimate.desktop.action.OnClickChatNavButton;
import utours.ultimate.desktop.action.OnClickPartiesNavButton;
import utours.ultimate.desktop.controller.PolymorphicController;
import utours.ultimate.desktop.view.DesktopChatView;
import utours.ultimate.desktop.view.DesktopNavButton;
import utours.ultimate.ui.NavButton;
import utours.ultimate.ui.NavButtonFactory;

import java.util.Optional;
import java.util.function.Supplier;

@Component
@Mapping
public class DesktopNavButtonFactory implements NavButtonFactory {

    private final Supplier<DesktopChatView> chatViewFactory;
    private final PolymorphicController controller;
    private DesktopChatView chatView;

    public DesktopNavButtonFactory(PolymorphicController polymorphicController) {
        this.controller = polymorphicController;
        this.chatViewFactory = () -> chatView = Optional.ofNullable(chatView)
                .orElseGet(DesktopChatView::new);
    }

    @Override
    @Component
    @Mapping(type = Mapping.Type.Additional)
    public NavButton createPartiesNavButton() {
        NavButton button = new DesktopNavButton("Parties", 0);
        button.setOnClick(new OnClickPartiesNavButton(controller));
        return button;
    }

    @Override
    @Component
    @Mapping(type = Mapping.Type.Additional)
    public NavButton createChatNavButton() {
        NavButton button = new DesktopNavButton("Chat", 1);
        button.setOnClick(new OnClickChatNavButton(chatViewFactory, controller));
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
