package tn.esprit.siyahidesktop.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import tn.esprit.siyahidesktop.models.Compte;
import tn.esprit.siyahidesktop.models.Service;

import tn.esprit.siyahidesktop.services.CompteService;
import tn.esprit.siyahidesktop.services.ServicesService;


import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ShowAccount {

    @FXML
    private Button backToMenu;

    @FXML
    private TextField creation;

    @FXML
    private TextField expiration;

    @FXML
    private Button modifier;

    @FXML
    private TextField rib;

    @FXML
    private TextField Service;

    @FXML
    private TextField solde;

    @FXML
    private Button supprimer;

    @FXML
    private TextField user;


    private Compte selectedAccount;
    private final CompteService compteservice = new CompteService();
    public void setAccountDetails(Compte compte) {
        if (compte != null) {
            selectedAccount = compte;

            if (compte.getUser() != null) {
                user.setText(compte.getUser().getFullName());
            } else {
                user.setText("User details not available");
            }

            if (selectedAccount.getService() != null) {
                Service.setText(selectedAccount.getService().getNom());
                LocalDateTime expirationDate = selectedAccount.getDate_creation().toLocalDateTime().plusYears(4);
                expirationDate = expirationDate.minusDays(1);

                expiration.setText(expirationDate.toLocalDate().toString());
            } else {
                Service.setText("Service details not available");
                expiration.setText("N/A");
            }

            rib.setText(String.valueOf(selectedAccount.getRib()));
            solde.setText(String.format("%.2f", selectedAccount.getSolde())); // Assuming solde is a double
            LocalDate creationDate = selectedAccount.getDate_creation().toLocalDateTime().toLocalDate();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            creation.setText(creationDate.format(formatter));
        } else {
            clearFields();
            showAlert("Error", "No account data available.", Alert.AlertType.ERROR);
        }
    }

    private void clearFields() {
        user.clear();
        Service.clear();
        rib.clear();
        solde.clear();
        creation.clear();
        expiration.clear();
    }

    private void showAlert(String title, String content, Alert.AlertType type) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }


    @FXML
    void updateAcc(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/tn/esprit/siyahidesktop/updateAccount.fxml"));
            Parent root = loader.load();
            UpdateAccount updateAccountController = loader.getController();
            updateAccountController.setExistingAccount(selectedAccount);

            Scene scene = new Scene(root);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    @FXML
    void delete(ActionEvent event) {
        if (selectedAccount != null) {
            compteservice.delete(selectedAccount);
            showAlert("Success", "Compte deleted successfully.", Alert.AlertType.INFORMATION);
            backToMainPage(event);  // Optionally redirect user back to the main page or refresh the list
        } else {
            showAlert("Error", "No selected compte to delete.", Alert.AlertType.ERROR);
        }
    }
    @FXML
    void backToMainPage(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/tn/esprit/siyahidesktop/MainPage.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            Stage stage = (Stage) backToMenu.getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



}

