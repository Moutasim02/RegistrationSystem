package com.moutasim.registrationsystem;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class LandingController {


    public void signOut(ActionEvent e) {
        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource("LogIn.fxml"));
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void exit(ActionEvent e) {
        System.exit(0);
    }

}
