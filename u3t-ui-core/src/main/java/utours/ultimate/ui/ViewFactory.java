package utours.ultimate.ui;

public interface ViewFactory<T extends NavButton> {

    NavButtonContainer<T> createNavButtonContainer();

}
