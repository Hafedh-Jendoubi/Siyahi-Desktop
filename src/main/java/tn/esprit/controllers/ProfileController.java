package tn.esprit.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import tn.esprit.models.User;

import java.io.IOException;
import java.util.Objects;

import static tn.esprit.services.UserService.connectedUser;

public class ProfileController {
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

    public static int profileCheck;

    public static User user;

    @FXML
    private void navigateToHomePage(ActionEvent event) {
        try {
            String pathTo = "";
            String nameTo = "";
            if(connectedUser.getRoles().equals("Client") || connectedUser.getRoles().equals("Employé(e)")) {
                pathTo = "/UserHomePage.fxml";
                nameTo = "Siyahi Bank | Home Page";
            } else{
                pathTo = "/AdminHomePage.fxml";
                nameTo = "Siyahi Bank | Dashboard";
            }
            Parent ajouterUserParent = FXMLLoader.load(getClass().getResource(pathTo));
            Scene ajouterUserScene = new Scene(ajouterUserParent);
            Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
            window.setScene(ajouterUserScene);
            window.setTitle(nameTo);
            window.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void navigateToUserSection(ActionEvent event) {
        try {
            Parent ajouterUserParent = FXMLLoader.load(getClass().getResource("/Users.fxml"));
            Scene ajouterUserScene = new Scene(ajouterUserParent);
            Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
            window.setScene(ajouterUserScene);
            window.setTitle("Siyahi Bank | Gestion des Utilisateurs");
            window.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void navigateToTransactions(ActionEvent event) {
        try {
            Parent ajouterUserParent = FXMLLoader.load(getClass().getResource("/Transactions.fxml"));
            Scene ajouterUserScene = new Scene(ajouterUserParent);
            Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
            window.setScene(ajouterUserScene);
            window.setTitle("Siyahi Bank | Gestion des Transactions");
            window.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void updateUser(ActionEvent event) {

    }

    private void setUserValues(User user){
        if(connectedUser.getRoles().equals("Admin") || connectedUser.getRoles().equals("Super Admin")){
            if (user.getActivity().equals("Active")) {
                activity.setText("[Activé]");
                activity.setStyle("-fx-text-fill: green;");
            } else {
                activity.setText("[Bloqué]");
                activity.setStyle("-fx-text-fill: red;");
            }
            date_creation.setText(user.getDate_creation_c().toString());
            role.setText(user.getRoles());
        }

        first_name.setText(user.getFirst_name());
        last_name.setText(user.getLast_name());
        gender.setText(user.getGender());
        cin.setText(String.valueOf(user.getCin()));
        adress.setText(user.getAddress());
        tel.setText(String.valueOf(user.getPhone_number()));
        email.setText(user.getEmail());

        //Setting the user picture
        String imageName = user.getImage();
        String imagePath = "/uploads/user/" + imageName;
        Image image = new Image(Objects.requireNonNull(getClass().getResource(imagePath)).toExternalForm());
        big_circle.setFill(new ImagePattern(image));
    }

    @FXML
    void initialize() {
        if (profileCheck == 1) { //Going into ProfileAdmin.fxml from "Profile" menuItem.
            setUserValues(connectedUser);
        } else { //Going into ProfileAdmin.fxml from "TableView" as an Admin or Super_Admin
            setUserValues(user);
        }
    }
}
