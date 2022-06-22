package com.moutasim.registrationsystem;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class LandingController {
    @FXML
    private Button btnSignOut;
    @FXML
    private Button btnExit;

    public void signOut(ActionEvent e) {

    }

    public void exit(ActionEvent e) {
        System.exit(0);
    }

}
