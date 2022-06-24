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
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LogInController {
    @FXML
    private TextField txtEmail;
    @FXML
    private PasswordField secretField;
    @FXML
    private Label feedbackMessage;
    @FXML
    private Label lblMailError;
    @FXML
    private Label lblPasswordError;
    private Stage stage;
    private Scene scene;
    private Parent root;

    public void login(ActionEvent e) {
        Connection connection = DatabaseConnector.connect();
        String email = txtEmail.getText();
        String pass = secretField.getText();
        PreparedStatement emailExistanceCheck, passwordCheck;

        try {
            emailExistanceCheck = connection.prepareStatement("SELECT email FROM registered_users WHERE email = '" + email + "'");
            passwordCheck = connection.prepareStatement("SELECT password FROM registered_users WHERE email = " +
                                                        "'" + email + "' AND password = " + "'" + pass + "'");
            boolean userExist = checkExistanceInRecords(emailExistanceCheck);
            boolean passwordMatch = checkPassword(passwordCheck);
            boolean authenticationCheck = verifyUserCredentials(userExist, passwordMatch);

            if (authenticationCheck == true) {
                toLogIn(e);
            }

        } catch (SQLException ex) {
            feedbackMessage.setTextFill(Color.RED);
            feedbackMessage.setText("Something went wrong!");
            ex.printStackTrace();
        }
    }

    private void cleanErrors() {
        lblMailError.setText("");
        lblPasswordError.setText("");
        feedbackMessage.setText("");
    }

    private boolean verifyUserCredentials(boolean userExist, boolean passwordMatch)  {
        if (userExist == true) {
            if (passwordMatch == true) {
                cleanErrors();
                feedbackMessage.setText("Successfully Authenticated!");
                return true;
            }
            else {
                lblPasswordError.setText("Wrong password!");
            }
        } else {
            lblMailError.setText("Email does not exist!");
        }
        return false;
    }
    private boolean checkPassword(PreparedStatement passwordCheck) {
        try {
            passwordCheck.execute();
            ResultSet passwordCheckResult = passwordCheck.getResultSet();
            if (passwordCheckResult.next()) {
                return true;
            } else {
                lblPasswordError.setText("Wrong password!");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    private boolean checkExistanceInRecords(PreparedStatement emailExistanceCheck) {
        try {
            emailExistanceCheck.execute();
            ResultSet emailCheckResult = emailExistanceCheck.getResultSet();
            if (emailCheckResult.next()) {
               return true;
            } else {
                lblMailError.setText("Email does not exist!");
            }
        } catch (SQLException e) {
            feedbackMessage.setTextFill(Color.RED);
            feedbackMessage.setText("Something went wrong!");
            e.printStackTrace();
        }
        return false;
    }

    public void toSignUp(ActionEvent e)  {
        try {
            root = FXMLLoader.load(getClass().getResource("SignUp.fxml"));
        } catch (IOException event) {
            System.out.println("Couldn't find the sign up screen");
            event.printStackTrace();
        }
        stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    private void toLogIn(ActionEvent e) {
        try {
            root = FXMLLoader.load(getClass().getResource("Landing.fxml"));
        } catch (IOException ex) {
            feedbackMessage.setTextFill(Color.RED);
            feedbackMessage.setText("Something went wrong!");
        }
        stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}
