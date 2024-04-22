package utours.ultimate.desktop.view;

import javafx.scene.control.Button;
import javafx.scene.layout.VBox;

public class NavButton extends Button {

    public static final int SIZE = 70;

    private final VBox container;

    public NavButton(int index, VBox container) {
        this.container = container;

        container.getChildren().add(index, this);

        setPrefWidth(SIZE);
        setPrefHeight(SIZE);
    }

}
