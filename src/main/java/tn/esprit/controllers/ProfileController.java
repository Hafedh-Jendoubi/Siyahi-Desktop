package tn.esprit.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Optional;

import static tn.esprit.services.UserService.connectedUser;

public class ProfileController {
    @FXML
    private Button ToHomePage;

    @FXML
    private Button ToUserSectionButton;

    @FXML
    private Label activity;

    @FXML
    private Label adress;

    @FXML
    private Circle big_circle;

    @FXML
    private Label cin;

    @FXML
    private Label date_creation;

    @FXML
    private Label email;

    @FXML
    private Label first_name;

    @FXML
    private Label gender;

    @FXML
    private Label last_name;

    @FXML
    private Label role;

    @FXML
    private Label tel;

    @FXML
    void navigateToHomePage(ActionEvent event) {
        try {
            Parent ajouterUserParent = FXMLLoader.load(getClass().getResource("/HomePage.fxml"));
            Scene ajouterUserScene = new Scene(ajouterUserParent);
            Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
            window.setScene(ajouterUserScene);
            window.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void navigateToUserSection(ActionEvent event) {
        try {
            Parent ajouterUserParent = FXMLLoader.load(getClass().getResource("/Users.fxml"));
            Scene ajouterUserScene = new Scene(ajouterUserParent);
            Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
            window.setScene(ajouterUserScene);
            window.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void updateUser(ActionEvent event) {
        //Will add update user here
    }

    @FXML
    void deleteUser(ActionEvent event) {
        //Delete User
    }

    @FXML
    void initialize() {
        try {
            String imageName = connectedUser.getImage();
            String imagePath = "/uploads/user/" + imageName;
            Image image = new Image(getClass().getResource(imagePath).toExternalForm());
            big_circle.setFill(new ImagePattern(image));
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }

        if(connectedUser.getActivity().equals("Active")) {
            activity.setText("[Activé]");
            activity.setStyle("-fx-text-fill: green;");
        }else{
            activity.setText("[Blocké]");
            activity.setStyle("-fx-text-fill: red;");
        }
        date_creation.setText(connectedUser.getDate_creation_c().toString());
        role.setText(connectedUser.getRoles());
        first_name.setText(connectedUser.getFirst_name());
        last_name.setText(connectedUser.getLast_name());
        gender.setText(connectedUser.getGender());
        cin.setText(String.valueOf(connectedUser.getCin()));
        adress.setText(connectedUser.getAddress());
        tel.setText(String.valueOf(connectedUser.getPhone_number()));
        email.setText(connectedUser.getEmail());
    }
}
