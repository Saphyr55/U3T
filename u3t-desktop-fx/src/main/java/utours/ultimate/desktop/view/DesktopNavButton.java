package utours.ultimate.desktop.view;

import javafx.scene.control.Button;

import utours.ultimate.ui.NavButton;
import utours.ultimate.ui.OnClickButton;
import utours.ultimate.ui.event.MouseEvent;

public class DesktopNavButton extends Button implements NavButton {

    public static final int SIZE = 70;

    private String name;
    private OnClickButton onClickButton;
    private int position;

    public DesktopNavButton(String name, int position) {
        setPosition(position);
        setOnClick(e -> { });
        setPrefWidth(SIZE);
        setPrefHeight(SIZE);
        setName(name);
    }

    public DesktopNavButton() {
        this("");
    }

    public DesktopNavButton(String name) {
        this(name, -1);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
        setText(name);
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public void setOnClick(OnClickButton onClickButton) {
        this.onClickButton = onClickButton;
        setOnMouseClicked(event -> getOnClick().performClick(new MouseEvent()));
    }

    public OnClickButton getOnClick() {
        return onClickButton;
    }

}
