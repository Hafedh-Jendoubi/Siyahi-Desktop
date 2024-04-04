package tn.esprit.siyahidesktop.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import tn.esprit.siyahidesktop.services.UserService;

import java.net.URL;
import java.util.ResourceBundle;

public class UserController {
    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField email;

    @FXML
    private PasswordField password;

    @FXML
    void authentification(ActionEvent event) {
        UserService us = new UserService();
        try {
            System.out.println(us.authentification(email.getText(), password.getText()));
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }
}