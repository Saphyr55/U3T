package utours.ultimate.desktop.view;

import javafx.scene.control.Button;
import javafx.scene.layout.VBox;

public class PartyButton extends Button {

    public static final int PADDING_SIZE = 30;
    public static final int HEIGHT_FACTOR_SIZE = 13;

    private VBox container;

    public PartyButton(VBox container) {
        this.container = container;
        this.container
                .getChildren()
                .add(this);

        this.prefWidthProperty().bind(container.widthProperty().subtract(PADDING_SIZE));
        this.prefHeightProperty().bind(container.heightProperty().divide(HEIGHT_FACTOR_SIZE));
    }

}
