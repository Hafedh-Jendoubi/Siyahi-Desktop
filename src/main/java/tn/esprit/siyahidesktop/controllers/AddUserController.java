package tn.esprit.siyahidesktop.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import tn.esprit.siyahidesktop.models.User;
import tn.esprit.siyahidesktop.services.UserService;

import java.util.Optional;

public class AddUserController {
    @FXML
    private TextField adresse;

    @FXML
    private TextField cin;

    @FXML
    private TextField email_input;

    @FXML
    private ComboBox<String> genre;

    @FXML
    private Button image;

    @FXML
    private TextField nom;

    @FXML
    private TextField num_tel;

    @FXML
    private TextField prenom;


    @FXML
    void AddUser(ActionEvent event) {
        //Controle de saisie
        int i = 0;
        UserService us = new UserService();
        if (prenom.getText().matches("[a-zA-Z]{4,14}")) {
            prenom.setStyle("-fx-border-color: transparent;"); i++;
        } else {
            prenom.setStyle("-fx-border-color: red;");
        }
        if (nom.getText().matches("[a-zA-Z]{4,19}")) {
            nom.setStyle("-fx-border-color: transparent;"); i++;
        } else {
            nom.setStyle("-fx-border-color: red;");
        }
        if (email_input.getText().matches("[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}")) {
            email_input.setStyle("-fx-border-color: transparent;"); i++;
        } else {
            email_input.setStyle("-fx-border-color: red;");
        }
        if (cin.getText().matches("\\d{8}")) {
            cin.setStyle("-fx-border-color: transparent;"); i++;
        } else {
            cin.setStyle("-fx-border-color: red;");
        }
        if(i == 4) { //Tous les inputs sont valides:
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmation");
            alert.setHeaderText("Voulez-vous vraiment ajouter l'utilisateur suivant?");
            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                if (genre.getValue().equals("Male"))
                    us.add(new User(prenom.getText(), nom.getText(), "M", adresse.getText(), Integer.parseInt(num_tel.getText()), Integer.parseInt(cin.getText()), email_input.getText()));
                else
                    us.add(new User(prenom.getText(), nom.getText(), "F", adresse.getText(), Integer.parseInt(num_tel.getText()), Integer.parseInt(cin.getText()), email_input.getText()));
            } else {
                alert.close();
            }
            CancelAddUser(event);
        }
    }

    @FXML
    void CancelAddUser(ActionEvent event) {
        ListUsersController lc = new ListUsersController();
        lc.navigateToUserSection(event);
    }

    @FXML
    void initialize() {
        ObservableList<String> gender = FXCollections.observableArrayList("Male", "Femelle");
        genre.setValue("Male");
        genre.setItems(gender);
    }
}
