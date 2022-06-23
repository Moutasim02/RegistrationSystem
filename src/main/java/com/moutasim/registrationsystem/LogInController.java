package com.moutasim.registrationsystem;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;

public class LogInController {
    @FXML
    private TextField txtEmail;
    @FXML
    private PasswordField secretField;
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
    private Stage stage;
    private Scene scene;
    private Parent root;

    public void login(ActionEvent e) {

    }
    public void toSignUp(ActionEvent e) throws IOException {
            root = FXMLLoader.load(getClass().getResource("SignUp.fxml"));
            stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
    }
    public void resetPassword(ActionEvent e) {

    }
}
