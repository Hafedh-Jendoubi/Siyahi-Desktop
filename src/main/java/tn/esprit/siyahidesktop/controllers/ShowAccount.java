package tn.esprit.siyahidesktop.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import tn.esprit.siyahidesktop.models.Compte;
import tn.esprit.siyahidesktop.models.Service;
import tn.esprit.siyahidesktop.services.CompteService;
import tn.esprit.siyahidesktop.services.ServicesService;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
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
    private TextField service;

    @FXML
    private TextField solde;

    @FXML
    private Button supprimer;

    @FXML
    private TextField user;


    private Compte selectedAccount;
    private final CompteService compteservice = new CompteService();
    public void setAccountDetails(Compte compte) {
        this.selectedAccount = compte;
        user.setText(compte.getUser().getFullName());
        service.setText(compte.getService().getNom());
        rib.setText(String.valueOf(compte.getSolde()));
        creation.setText(String.valueOf(compte.getDate_creation()));
        expiration.setText(String.valueOf(compte.getService().getExpiration_date()));
    }

    @FXML
    void updateAcc(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/tn/esprit/siyahidesktop/updateAccount.fxml"));
            Parent root = loader.load();

            AddAccount addAccountController = loader.getController();

            addAccountController.setExistingAccount(selectedAccount);

            Scene scene = new Scene(root);

            Stage stage = (Stage) modifier.getScene().getWindow();

            stage.setScene(scene);

            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
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

