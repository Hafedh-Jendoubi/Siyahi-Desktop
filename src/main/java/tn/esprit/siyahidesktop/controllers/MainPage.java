package tn.esprit.siyahidesktop.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

public class MainPage {

    @FXML
    private Button Comptes_button;

    @FXML
    private Button afficher_liste_comptes_button;

    @FXML
    private Button afficher_liste_services_button;

    @FXML
    private Button ajouter_compte_button;

    @FXML
    private Button ajouter_service_button;

    @FXML
    private Button button_services;

    @FXML
    private AnchorPane comptes_anchorpane;

    @FXML
    private AnchorPane services_anchorpane;

    @FXML
    void show_comptes_buttons(ActionEvent event) {
        if(services_anchorpane.isVisible())
        {
            services_anchorpane.setVisible(false);
        }
        comptes_anchorpane.setVisible(true);
    }

    @FXML
    void show_services_buttons(ActionEvent event) {
        if(comptes_anchorpane.isVisible())
        {
            comptes_anchorpane.setVisible(false);
        }
        services_anchorpane.setVisible(true);

    }
//TODO bech nkamel l button switches mabin les pages

    @FXML
    void show_comptes_list(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/tn/esprit/siyahidesktop/AccountsList.fxml"));
            Parent root = loader.load();

            Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();

            stage.setScene(new Scene(root));

            // Show the stage
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void show_services_list(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/tn/esprit/siyahidesktop/ServicesList.fxml"));
            Parent root = loader.load();

            Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();

            stage.setScene(new Scene(root));

            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void add_compte(ActionEvent event){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/tn/esprit/siyahidesktop/AddAccount.fxml"));
            Parent root = loader.load();

            Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();

            stage.setScene(new Scene(root));

            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void Add_service(ActionEvent event){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/tn/esprit/siyahidesktop/AddService.fxml"));
            Parent root = loader.load();

            Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();

            stage.setScene(new Scene(root));

            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

