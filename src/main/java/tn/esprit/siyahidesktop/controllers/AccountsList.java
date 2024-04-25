package tn.esprit.siyahidesktop.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.stage.Stage;
import tn.esprit.siyahidesktop.models.Compte;
import tn.esprit.siyahidesktop.models.Service;
import tn.esprit.siyahidesktop.services.CompteService;
import java.util.*;
import tn.esprit.siyahidesktop.services.ServicesService;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class AccountsList implements Initializable {

    @FXML
    private ListView<Compte> liste_comptes;

    @FXML
    private Button load_button;

    @FXML
    private Button menu_button;

    private final CompteService compteservice = new CompteService();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        liste_comptes.getItems().addAll(compteservice.getAll());

        liste_comptes.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                loadAccountDetails(newValue);
            }
        });
    }

    private void loadAccountDetails(Compte selectedAccount) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/tn/esprit/siyahidesktop/ShowAccount.fxml"));
            Parent root = loader.load();

            ShowAccount showAccountController = loader.getController();

            showAccountController.setAccountDetails(selectedAccount);

            Scene scene = new Scene(root);

            Stage stage = (Stage) liste_comptes.getScene().getWindow();

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
            Stage stage = (Stage) menu_button.getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
/*
    private class ServiceListCell extends ListCell<Service> {
        private final HBox content;
        private final Text idText;
        private final Text nomText;
        private final Text descriptionText;

        public ServiceListCell() {
            super();
            content = new HBox();
            idText = new Text();
            nomText = new Text();
            descriptionText = new Text();
            HBox.setHgrow(nomText, Priority.ALWAYS);
            HBox.setHgrow(descriptionText, Priority.ALWAYS);
            content.getChildren().addAll(idText, nomText, descriptionText);
        }

        @Override
        protected void updateItem(Service item, boolean empty) {
            super.updateItem(item, empty);
            if (item != null && !empty) {
                idText.setText(String.valueOf(item.getId()));
                nomText.setText(item.getNom());
                descriptionText.setText(item.getDescription());
                setGraphic(content);
            } else {
                setGraphic(null);
            }
        }
    }*/
}
