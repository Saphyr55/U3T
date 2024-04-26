package utours.ultimate.desktop.view;

import javafx.scene.control.Button;

import utours.ultimate.ui.NavButton;

public class DesktopNavButton extends Button implements NavButton {
    
    public static final int SIZE = 70;

    private final String text;
    private final int position;

    public DesktopNavButton(String text, int index) {
        this.text = text;
        this.position = index;
        this.setPrefWidth(SIZE);
        this.setPrefHeight(SIZE);
        this.setText(text);
    }

    public DesktopNavButton(String text) {
        this(text, -1);
    }

    @Override
    public String name() {
        return text;
    }

    @Override
    public int position() {
        return position;
    }

}
