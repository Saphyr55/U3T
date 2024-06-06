package utours.ultimate.desktop.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import utours.ultimate.desktop.view.PolymorphicView;

import java.net.URL;
import java.util.ResourceBundle;

public final class MainController implements Initializable {

    private @FXML PolymorphicView mainLeftPolymorphic;
    private @FXML PolymorphicView mainRightPolymorphic;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public PolymorphicView getMainLeftPolymorphic() {
        return mainLeftPolymorphic;
    }

    public PolymorphicView getMainRightPolymorphic() {
        return mainRightPolymorphic;
    }



}