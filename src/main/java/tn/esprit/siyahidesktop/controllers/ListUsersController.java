package tn.esprit.siyahidesktop.controllers;

import com.sun.javafx.application.HostServicesDelegate;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import tn.esprit.siyahidesktop.Main;
import tn.esprit.siyahidesktop.models.User;
import tn.esprit.siyahidesktop.services.UserService;

import java.awt.*;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.List;
import java.util.Optional;

public class ListUsersController {
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
            System.err.println(e.getMessage());
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
            window.setTitle("Siyahi Bank | La Gestion des Utilisateurs");
            window.show();
        } catch (IOException e) {
            System.err.println(e.getMessage());
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
            window.setTitle("Siyahi Bank | Ajouter Utilisateur");
            window.show();
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }

    @FXML
    void NavigateToUserAuth(ActionEvent event) {
        try {
            Parent users_section = FXMLLoader.load(getClass().getResource("/tn/esprit/siyahidesktop/UserAuth.fxml"));
            Scene users_sectionSecene = new Scene(users_section);
            Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
            window.setScene(users_sectionSecene);
            window.setHeight(400); window.setMaxHeight(400); window.setMinHeight(400);
            window.setWidth(606); window.setMaxWidth(600); window.setMinWidth(600);
            window.setTitle("Siyahi Bank | Connexion");
            window.show();
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }

    @FXML
    void authentification(ActionEvent event) {
        UserService us = new UserService();
        try {
            if(us.authentification(email.getText(), password.getText())) { //Login Success
                User user = us.getOneByEMAIL(email.getText());
                if(user.getActivity().equals("F")){
                    Alert alert = showFailedMessage("Votre compte a été désactivé.");
                    ButtonType confirmButton = new ButtonType("Ok");
                    alert.getButtonTypes().setAll(confirmButton);
                    Optional<ButtonType> result = alert.showAndWait();
                    if (result.isPresent() && result.get() == confirmButton) {
                        alert.close();
                    }
                }else {
                    try {
                        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/tn/esprit/siyahidesktop/HomePage.fxml"));
                        Parent root = fxmlLoader.load();
                        Stage stage = (Stage) email.getScene().getWindow();
                        stage.setWidth(1300); stage.setMaxWidth(1300); stage.setMinWidth(1300);
                        stage.setHeight(600); stage.setMaxHeight(600); stage.setMinHeight(600);
                        email.getScene().setRoot(root);
                    } catch (IOException ex) {
                        System.err.println(ex.getMessage());
                    }
                }
            } else {
                Alert alert = showFailedMessage("Veuillez vérifier vos identifiants.");
                ButtonType confirmButton = new ButtonType("Ok");
                alert.getButtonTypes().setAll(confirmButton);
                Optional<ButtonType> result = alert.showAndWait();
                if (result.isPresent() && result.get() == confirmButton) {
                    alert.close();
                }
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @FXML
    void ForgotPassword(ActionEvent event) {
        try {
            Desktop.getDesktop().browse(new URL("http://127.0.0.1:8000/reset-password/").toURI());
        } catch (IOException | URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void Logout(ActionEvent event){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation");
        alert.setHeaderText("Voulez-vous vraiment déconnecter?");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            NavigateToUserAuth(event);
        } else {
            alert.close();
        }
    }

    private Alert showFailedMessage(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Erreur de connexion");
        alert.setHeaderText("Une erreur de connexion est survenue.");
        alert.setContentText(message);

        return alert;
    }

    @FXML
    void DeleteUser(ActionEvent event) {
        UserService us = new UserService();
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation");
        alert.setHeaderText("Voulez-vous vraiment supprimer l'utilisateur suivant ?");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            us.delete(TableUser.getSelectionModel().getSelectedItem());
            TableUser.getItems().remove(TableUser.getSelectionModel().getSelectedItem());
            showSuccessMessage("Utilisateur supprimé avec succès!");
        } else {
            alert.close();
        }
    }

    @FXML
    void ActivateDesactivateUser(ActionEvent event){
        UserService us = new UserService();
        User user = TableUser.getSelectionModel().getSelectedItem();
        User user1 = us.getOneByCIN(user.getCin()); //I created this user1 because the user variable got gender as "Male" not "M" and role "Client" instead of "["ROLE_USER"]"
        if(user1.getActivity().equals("F")){
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmation");
            alert.setHeaderText("Voulez-vous vraiment activer l'utilisateur suivant?");
            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                us.enableUser(user1);
                showSuccessMessage("Utilisateur a été activé avec succès!");
            } else {
                alert.close();
            }
        }else{
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmation");
            alert.setHeaderText("Voulez-vous vraiment désactiver l'utilisateur suivant?");
            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                us.disableUser(user1);
                showSuccessMessage("Utilisateur a été désactivé avec succès!");
            } else {
                alert.close();
            }
        }
        initialize();
    }

    private void showSuccessMessage(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Succès");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    @FXML
    void initialize() {
        UserService us = new UserService();
        try {
            List<User> users = us.getAll();
            ObservableList<User> observableList = FXCollections.observableList(users);

            ActiviteCol.setCellValueFactory(new PropertyValueFactory<>("activity"));
            NomCol.setCellValueFactory(new PropertyValueFactory<>("last_name"));
            PrenomCol.setCellValueFactory(new PropertyValueFactory<>("first_name"));
            GenreCol.setCellValueFactory(new PropertyValueFactory<>("gender"));
            AdrCol.setCellValueFactory(new PropertyValueFactory<>("address"));
            TelCol.setCellValueFactory(new PropertyValueFactory<>("phone_number"));
            CinCol.setCellValueFactory(new PropertyValueFactory<>("cin"));
            RoleCol.setCellValueFactory(new PropertyValueFactory<>("roles"));

            TableUser.setItems(observableList);
        }catch (Exception e){
            System.err.println(e.getMessage());
        }
    }
}