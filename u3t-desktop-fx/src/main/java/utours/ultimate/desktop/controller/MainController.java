package utours.ultimate.desktop.controller;


import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import utours.ultimate.desktop.view.DesktopMainView;

import java.net.URL;
import java.util.ResourceBundle;

public class MainController implements Initializable {

    @FXML
    private DesktopMainView root;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        System.out.println("Loading Main Controller");
    }

}