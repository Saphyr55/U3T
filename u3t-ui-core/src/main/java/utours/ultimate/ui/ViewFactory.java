package utours.ultimate.ui;

import java.util.List;

public interface ViewFactory<T extends NavButton> {

    NavButtonContainer<T> createNavButtonContainer();

}
