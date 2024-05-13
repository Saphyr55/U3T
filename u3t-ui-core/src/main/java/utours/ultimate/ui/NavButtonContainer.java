package utours.ultimate.ui;

public interface NavButtonContainer<T extends NavButton> {

    void add(T navButton);

    void remove(T navButton);

    void remove(int index);

}
