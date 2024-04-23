package utours.ultimate.desktop.view;

import javafx.scene.control.Button;
import javafx.scene.layout.VBox;

import java.util.Optional;

public class NavButton extends Button {

    public static final int SIZE = 70;

    private final VBox container;

    public NavButton(Integer index, VBox container) {
        this.container = container;
        setPrefWidth(SIZE);
        setPrefHeight(SIZE);

        if (Optional.ofNullable(index).isPresent()) {
            container.getChildren().add(index, this);
        } else {
            container.getChildren().add(this);
        }

    }


    public NavButton(VBox container) {
        this(null, container);
    }


}
