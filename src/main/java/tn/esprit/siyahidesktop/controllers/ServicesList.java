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
import tn.esprit.siyahidesktop.models.Service;
import tn.esprit.siyahidesktop.services.ServicesService;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.scene.control.ListCell;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.geometry.Pos;
import javafx.geometry.Insets;

public class ServicesList implements Initializable {

    @FXML
    private ListView<Service> liste_services;

    @FXML
    private Button load_button;

    @FXML
    private Button menu_button;

    private final ServicesService serviceService = new ServicesService();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        liste_services.getItems().addAll(serviceService.getAll());

        // Set a custom cell factory to display each service with detailed information
        liste_services.setCellFactory(param -> new ListCell<Service>() {
            @Override
            protected void updateItem(Service item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                    setGraphic(null);
                } else {
                    // Create labels for each piece of data you want to display
                    Label nameLabel = new Label("Name: " + item.getNom());
                    nameLabel.setStyle("-fx-font-weight: bold; -fx-text-fill: #387296; -fx-font-size: 14;");

                    Label descriptionLabel = new Label("Description: " + item.getDescription());
                    descriptionLabel.setStyle("-fx-font-weight: bold; -fx-text-fill: #387296; -fx-font-size: 14;");

                    Label statusLabel = new Label("Status: " + (item.isActive() ? "Active" : "Inactive"));
                    statusLabel.setStyle("-fx-font-weight: bold; -fx-text-fill: #387296; -fx-font-size: 14;");

                    // Create a VBox to hold all labels
                    VBox vbox = new VBox(10, nameLabel, descriptionLabel, statusLabel);
                    vbox.setPadding(new Insets(10));
                    vbox.setStyle("-fx-background-color: #f0f0f0; -fx-border-color: #387296; -fx-border-width: 2px;");

                    setGraphic(vbox);
                }
            }
        });

        liste_services.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                loadServiceDetails(newValue);
            }
        });
    }

    private void loadServiceDetails(Service selectedService) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/tn/esprit/siyahidesktop/ShowService.fxml"));
            Parent root = loader.load();

            ShowService showServiceController = loader.getController();

            showServiceController.setServiceDetails(selectedService);

            Scene scene = new Scene(root);

            Stage stage = (Stage) liste_services.getScene().getWindow();

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
