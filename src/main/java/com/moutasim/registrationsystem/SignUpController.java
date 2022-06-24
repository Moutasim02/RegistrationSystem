package com.moutasim.registrationsystem;

import com.moutasim.registrationsystem.DBConnect.DatabaseConnector;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.regex.Pattern;


/* Steps:
 * Check for blank fields
 * Check values validity
 * check Existance in Database
 * Sign Up and Write to Database */

public class SignUpController {
    @FXML
    private TextField txtUsername;
    @FXML
    private TextField txtEmail;
    @FXML
    private PasswordField secretField;
    @FXML
    private Label feedbackMessage;
    @FXML
    private RadioButton rbtnAgreement;
    @FXML
    private Label lblEmailError;
    @FXML
    private Label lblUsernameError;
    @FXML
    private Label lblPasswordError;
    @FXML
    private Label lblAgreementError;
    private Parent root;

    public void signUp(ActionEvent e) {
        Connection connection = DatabaseConnector.connect();
        String username = txtUsername.getText();
        String email = txtEmail.getText();
        String pass = secretField.getText();
        PreparedStatement insertion;

        boolean allChecksSucceed = allChecksSucceed(connection, username, email, pass);
        if (allChecksSucceed == true) {
            try {
                insertion = connection.prepareStatement("INSERT INTO registered_users (username, email, password) VALUES ("
                        + "'" + username + "','" + email + "','" + pass + "')");
                insertion.execute();
                cleanErrors();
                cleanFields();
                toLogIn(e);
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }

    private void cleanFields() {
        txtUsername.setText("");
        txtEmail.setText("");
        secretField.setText("");
        rbtnAgreement.setSelected(false);
    }

    private boolean allChecksSucceed(Connection connection, String username, String email, String pass) {
        cleanErrors();
        boolean haveBlankValues = haveBlankValues(username, email, pass, rbtnAgreement);
        if (haveBlankValues == false) {
            boolean valuesValidated = valuesValidated(username, email, pass);
            if (valuesValidated == true) {
                boolean existInRecords = existInRecords(connection, username, email);
                if (existInRecords == false) {
                    return true;
                }
            } else {
                feedbackMessage.setText("Please check errors!");
                return false;
            }
        } else {
            feedbackMessage.setText("Check Blank fields!");
            return false;
        }
        return false;
    }

    private boolean existInRecords(Connection connection, String username, String email) {
        cleanErrors();
        PreparedStatement userCheck, emailCheck;
        try {
            userCheck = connection.prepareStatement("SELECT username FROM registered_users WHERE username = " + "'" + username + "'");
            userCheck.execute();
            ResultSet userCheckResultSet = userCheck.getResultSet();
            if (userCheckResultSet.next()) {
                lblUsernameError.setText("Please choose another username!");
                return true;
            }

            emailCheck = connection.prepareStatement("SELECT username FROM registered_users WHERE email = " + "'" + email + "'");
            emailCheck.execute();
            ResultSet emailCheckResultSet = emailCheck.getResultSet();
            if (emailCheckResultSet.next()) {
                lblEmailError.setText("Seems you are already registered!");
                return true;
            }
        } catch (SQLException e) {
            feedbackMessage.setTextFill(Color.RED);
            feedbackMessage.setText("Something went wrong!");
            e.printStackTrace();
        }
        return false;
    }

    // Validate 4 conditions
    private boolean valuesValidated(String username, String email, String pass) {
        cleanErrors();
        if (Pattern.matches("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$", email)) {
            if (pass.length() >= 8) {
                if (username.length() >= 3) {
                    return true;
                } else {
                    lblUsernameError.setText("Username too short");
                    return false;
                }
            } else {
                lblPasswordError.setText("Please enter a password 8 characters or more!");
                return false;
            }
        } else {
            lblEmailError.setText("Invalid Email!");
            return false;
        }
    }

    private boolean haveBlankValues(String username, String email, String pass, RadioButton radioButtonSelected) {
        cleanErrors();
        if (username.isBlank()) {
            lblUsernameError.setText("Please type your username!");
            return true;
        }
        if (email.isBlank()) {
            lblEmailError.setText("Please type your email!");
            return true;
        }
        if (pass.isBlank()) {
            lblPasswordError.setText("Please type your password!");
            return true;
        }
        if (!radioButtonSelected.isSelected()) {
            lblAgreementError.setText("Please confirm agreement to T&C");
            return true;
        }
        return false;
    }

    private void cleanErrors() {
        lblUsernameError.setText("");
        lblEmailError.setText("");
        lblPasswordError.setText("");
        lblAgreementError.setText("");
        feedbackMessage.setText("");
    }

    public void toLogIn(ActionEvent e) {
        try {
            root = FXMLLoader.load(getClass().getResource("LogIn.fxml"));
        } catch (IOException ex) {
            feedbackMessage.setTextFill(Color.RED);
            feedbackMessage.setText("Something went wrong!");
            ex.printStackTrace();
        }
        Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}
