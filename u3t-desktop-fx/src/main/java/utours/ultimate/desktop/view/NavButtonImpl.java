package utours.ultimate.desktop.view;

import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import utours.ultimate.ui.NavButton;

import java.util.Optional;

public class NavButtonImpl extends Button implements NavButton {

    public static final int SIZE = 70;

    private final VBox container;

    public NavButtonImpl(Integer index, VBox container) {
        this.container = container;
        setPrefWidth(SIZE);
        setPrefHeight(SIZE);

        if (Optional.ofNullable(index).isPresent()) {
            container.getChildren().add(index, this);
        } else {
            container.getChildren().add(this);
        }

    }

    public NavButtonImpl(VBox container) {
        this(null, container);
    }


}
