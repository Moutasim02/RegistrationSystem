package com.moutasim.registrationsystem;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;

public class LogInController {
    @FXML
    private TextField txtEmail;
    @FXML
    private TextField txtPassword;
    @FXML
    private RadioButton rbtnRemember;
    @FXML
    private Button btnLogIn;
    @FXML
    private Button btnForgotPassword;
    @FXML
    private Button btnToSignUp;
    @FXML
    private Label lblMailError;
    @FXML
    private Label lblPasswordError;


    public void login(ActionEvent e) {

    }
    public void toSignUp(ActionEvent e) {

    }
    public void resetPassword(ActionEvent e) {

    }
}
