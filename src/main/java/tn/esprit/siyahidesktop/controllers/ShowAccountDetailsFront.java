package tn.esprit.siyahidesktop.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import tn.esprit.siyahidesktop.models.Compte;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class ShowAccountDetailsFront {

    @FXML
    private Text expiration;

    @FXML
    private Text nom;

    @FXML
    private ImageView return_button;

    @FXML
    private Text rib;


    @FXML
    private TextField solde;

    private Compte compte;

    @FXML
    private Button toMap;

    @FXML
    void toMap(MouseEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/tn/esprit/siyahidesktop/Localisation.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            Stage stage = (Stage) toMap.getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void initialize() {
        updateUI();
    }

    public void setCompte(Compte compte) {
        this.compte = compte;
        updateUI();
    }

    private void updateUI() {
        if (compte != null) {
            rib.setText(String.valueOf(compte.getRib()));
            expiration.setText(compte.getExpirationDate().toString());
            nom.setText(compte.getUser().getFullName());
            solde.setText(String.valueOf(compte.getSolde()));
        }
    }

    @FXML
    void backToMainPage(MouseEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/tn/esprit/siyahidesktop/MainPage.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            Stage stage = (Stage) return_button.getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setAccountDetails(Compte compte) {
            Compte selectedAccount = compte;

            if (compte.getUser() != null) {
                nom.setText(compte.getUser().getFullName());
            } else {
                nom.setText("User details not available");
            }


            rib.setText(String.valueOf(selectedAccount.getRib()));
            solde.setText(String.format("%.2f", selectedAccount.getSolde())); // Assuming solde is a double
            LocalDate creationDate = selectedAccount.getDate_creation().toLocalDateTime().toLocalDate();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM-yyyy");
            expiration.setText(creationDate.format(formatter));

    }


}
