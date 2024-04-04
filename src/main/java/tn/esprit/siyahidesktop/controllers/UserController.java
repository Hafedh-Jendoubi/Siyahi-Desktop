package tn.esprit.siyahidesktop.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import tn.esprit.siyahidesktop.Main;
import tn.esprit.siyahidesktop.models.User;
import tn.esprit.siyahidesktop.services.UserService;

import java.io.IOException;
import java.net.URL;
import java.util.List;
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
    private TableColumn<User, String> ActiviteCol;

    @FXML
    private TableColumn<User, String> AdrCol;

    @FXML
    private TableColumn<User, Integer> CinCol;

    @FXML
    private TableColumn<User, String> GenreCol;

    @FXML
    private TableColumn<User, String> NomCol;

    @FXML
    private TableColumn<User, String> PrenomCol;

    @FXML
    private TableColumn<User, String> RoleCol;

    @FXML
    private TableColumn<User, Integer> TelCol;

    @FXML
    private TableView<User> TableUser;

    @FXML
    private TextField adresse;

    @FXML
    private TextField cin;

    @FXML
    private TextField email_input;

    @FXML
    private ComboBox<?> genre;

    @FXML
    private Button image;

    @FXML
    private TextField nom;

    @FXML
    private TextField num_tel;

    @FXML
    private TextField prenom;

    @FXML
    void navigateToHomePage(ActionEvent event) {
        try {
            Parent acceuil = FXMLLoader.load(getClass().getResource("/tn/esprit/siyahidesktop/HomePage.fxml"));
            Scene ajouterUserScene = new Scene(acceuil);
            Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
            window.setScene(ajouterUserScene);
            window.setMaxHeight(600); window.setMinHeight(600);
            window.setMaxWidth(1300); window.setMinWidth(1300);
            window.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void navigateToUserSection(ActionEvent event) {
        try {
            Parent users_section = FXMLLoader.load(getClass().getResource("/tn/esprit/siyahidesktop/Users.fxml"));
            Scene ajouterUserScene = new Scene(users_section);
            Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
            window.setScene(ajouterUserScene);
            window.setMaxHeight(600); window.setMinHeight(600);
            window.setMaxWidth(1300); window.setMinWidth(1300);
            window.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void NavigateToAddUser(ActionEvent event) {
        try {
            Parent users_section = FXMLLoader.load(getClass().getResource("/tn/esprit/siyahidesktop/AddUser.fxml"));
            Scene users_sectionSecene = new Scene(users_section);
            Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
            window.setScene(users_sectionSecene);
            window.setMaxHeight(550); window.setMinHeight(550);
            window.setMaxWidth(400); window.setMinWidth(400);
            window.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void authentification(ActionEvent event) {
        UserService us = new UserService();
        try {
            if(us.authentification(email.getText(), password.getText())) {
                try {
                    FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/tn/esprit/siyahidesktop/HomePage.fxml"));
                    Parent root = fxmlLoader.load();
                    Stage stage = (Stage) email.getScene().getWindow();
                    stage.setWidth(1300);
                    stage.setHeight(600);
                    stage.setMaxWidth(1300); stage.setMinWidth(1300);
                    stage.setMaxHeight(600); stage.setMinHeight(600);
                    email.getScene().setRoot(root);
                } catch (IOException ex) {
                    System.err.println(ex.getMessage());
                }
            } else {
                System.out.println("Wrong Credentials!");
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @FXML
    void AddUser(ActionEvent event) {

    }

    @FXML
    void CancelAddUser(ActionEvent event) {
        navigateToUserSection(event);
    }

    @FXML
    void initialize() {
        UserService us = new UserService();
        try {
            List<User> users = us.getAll();
            ObservableList<User> observableList = FXCollections.observableList(users);
            TableUser.setItems(observableList);

            ActiviteCol.setCellValueFactory(new PropertyValueFactory<>("activity"));
            NomCol.setCellValueFactory(new PropertyValueFactory<>("last_name"));
            PrenomCol.setCellValueFactory(new PropertyValueFactory<>("first_name"));
            GenreCol.setCellValueFactory(new PropertyValueFactory<>("gender"));
            AdrCol.setCellValueFactory(new PropertyValueFactory<>("address"));
            TelCol.setCellValueFactory(new PropertyValueFactory<>("phone_number"));
            CinCol.setCellValueFactory(new PropertyValueFactory<>("cin"));
            RoleCol.setCellValueFactory(new PropertyValueFactory<>("roles"));
        }catch (Exception e){
            System.err.println(e.getMessage());
        }
    }
}