package com.moutasim.registrationsystem;

import com.moutasim.registrationsystem.DBConnect.DatabaseConnector;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.*;
import java.util.regex.Pattern;

public class SignUpController {
    @FXML
    private TextField txtUsername;
    @FXML
    private TextField txtEmail;
    @FXML
    private PasswordField secretField;
    @FXML
    private Label successMessage;
    @FXML
    private RadioButton rbtnAgreement;
    @FXML
    private Button btnToLogIn;
    @FXML
    private Label lblEmailError;
    @FXML
    private Label lblUsernameError;
    @FXML
    private Label lblPasswordError;
    @FXML
    private Label lblAgreementError;
    private Stage stage;
    private Scene scene;
    private Parent root;

    public void signUp(ActionEvent e) {
        Connection connection = DatabaseConnector.connect();
        String username = txtUsername.getText();
        String email = txtEmail.getText();
        String pass = secretField.getText();
        PreparedStatement insertion;

        try {
            insertion = connection.prepareStatement("INSERT INTO registered_users (username, email, password) VALUES ("
                                                        + "'" + username + "','" + email + "','" + pass + "')");
            boolean duplicateCheckResult = checkExistanceInRecords(username, email, connection);
            if (duplicateCheckResult == true) {
                insertValues(username, email, pass, insertion);
            }
        } catch (SQLException ex) {
            successMessage.setTextFill(Color.RED);
            successMessage.setText("Something went wrong!");
            ex.printStackTrace();
        }
    }

    // ToDO: Clean Errors after each sign up failure
    private void cleanErrors(Label lblUsernameError, Label lblEmailError, Label lblPasswordError, Label lblAgreementError, Label successMessage) {
        lblUsernameError.setText("");
        lblEmailError.setText("");
        lblPasswordError.setText("");
        lblAgreementError.setText("");
        successMessage.setText("");
    }

    private void insertValues(String username, String email, String pass, PreparedStatement insertion) {
        if (!username.isBlank() && !email.isBlank() && !pass.isBlank() && rbtnAgreement.isSelected()) {
            if (Pattern.matches("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$", email)) {
                if (pass.length() >= 8) {
                    try {
                        insertion.execute();
                        cleanErrors(lblUsernameError, lblEmailError, lblPasswordError, lblAgreementError, successMessage);
                        successMessage.setText("Signed up successfully!");
                    } catch (SQLException ex) {
                        successMessage.setTextFill(Color.RED);
                        successMessage.setText("Something went wrong!");
                        ex.printStackTrace();
                    }
                } else {
                    lblPasswordError.setText("Please enter a password 8 characters or more!");
                }
            } else {
                lblEmailError.setText("Invalid Email!");
            }
        }
        checkBlankValues(username, email, pass, rbtnAgreement);
    }

    private void checkBlankValues(String username, String email, String pass, RadioButton rbtnAgreement) {
        if (username.isBlank()) {
            lblUsernameError.setText("Please type your username!");
        }
        if (email.isBlank()) {
            lblEmailError.setText("Please type your email!");
        }
        if (pass.isBlank()) {
            lblPasswordError.setText("Please type your password!");
        }
        if (!rbtnAgreement.isSelected()) {
            lblAgreementError.setText("Please confirm agreement to T&C");
        }
    }

    private boolean checkExistanceInRecords(String username, String email, Connection connection) {
        PreparedStatement userCheck, emailCheck;
        try {
            userCheck = connection.prepareStatement("SELECT username FROM registered_users WHERE username = " + "'" + username + "'");
            userCheck.execute();
            ResultSet userCheckResultSet = userCheck.getResultSet();
            if (userCheckResultSet.next()) {
                lblUsernameError.setText("Please choose another username!");
                return false;
            }

            emailCheck = connection.prepareStatement("SELECT username FROM registered_users WHERE email = " + "'" + email + "'");
            emailCheck.execute();
            ResultSet emailCheckResultSet = emailCheck.getResultSet();
            if (emailCheckResultSet.next()) {
                lblEmailError.setText("Seems you are already registered!");
                return false;
            }
        } catch (SQLException e) {
            successMessage.setTextFill(Color.RED);
            successMessage.setText("Something went wrong!");
            e.printStackTrace();
        }
        return true;
    }

    public void toLogIn(ActionEvent e) throws IOException {
        root = FXMLLoader.load(getClass().getResource("LogIn.fxml"));
        stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}
