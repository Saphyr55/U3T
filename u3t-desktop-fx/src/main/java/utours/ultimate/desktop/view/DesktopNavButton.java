package utours.ultimate.desktop.view;

import javafx.scene.control.Button;

import utours.ultimate.ui.NavButton;
import utours.ultimate.ui.OnClickButton;
import utours.ultimate.ui.event.MouseEvent;

public class DesktopNavButton extends Button implements NavButton {

    public static final int SIZE = 70;

    private String text;
    private OnClickButton onClickButton;
    private int position;

    public DesktopNavButton(String text, int index) {
        setName(text);
        setPosition(index);
        setOnClick(e -> { });
        setPrefWidth(SIZE);
        setPrefHeight(SIZE);
        setText(text);
    }

    public DesktopNavButton() {
        this("");
    }

    public DesktopNavButton(String text) {
        this(text, -1);
    }

    @Override
    public String getName() {
        return text;
    }

    @Override
    public void setName(String name) {
        this.text = name;
    }

    @Override
    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    @Override
    public void setOnClick(OnClickButton onClickButton) {
        this.onClickButton = onClickButton;
        setOnMouseClicked(event -> onClick().performClick(new MouseEvent()));
    }

    @Override
    public OnClickButton onClick() {
        return onClickButton;
    }

}
