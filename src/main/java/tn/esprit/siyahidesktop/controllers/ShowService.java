package tn.esprit.siyahidesktop.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import tn.esprit.siyahidesktop.models.Service;
import tn.esprit.siyahidesktop.services.ServicesService;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class ShowService {

    @FXML
    private Button delete_button;

    @FXML
    private TextArea desc_service;

    @FXML
    private Button menu_button;

    @FXML
    private TextField nom_service;
    @FXML
    private TextField pricing;
    @FXML
    private TextField expiration;
    @FXML
    private boolean isActive=true;

    @FXML
    private Button update_button;

    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setContentText(content);
        alert.showAndWait();
    }

    @FXML
    void DeleteS(ActionEvent event) {
        if (selectedService != null) {
            try {
                serviceService.delete(selectedService);
                showAlert("Success", "Service deleted successfully.");
                backToMainPage(null);
            } catch (Exception e) {
                showAlert("Error", "Failed to delete service: " + e.getMessage());
            }
        } else {
            showAlert("Error", "No service selected!");
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

    private Service selectedService;
    private final ServicesService serviceService = new ServicesService();
    public void setServiceDetails(Service service) {
        this.selectedService = service;
        if (service != null) {
            nom_service.setText(service.getNom() != null ? service.getNom() : "");
            desc_service.setText(service.getDescription() != null ? service.getDescription() : "");
            pricing.setText(String.valueOf(service.getPricing()) != null ? String.valueOf(service.getPricing()) : "0.0");

            LocalDate creationDate = service.getExpiration_date();
            if (creationDate != null) {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                expiration.setText(creationDate.format(formatter));
            } else {
                expiration.setText("No expiration date");
            }
        } else {
            nom_service.setText("");
            desc_service.setText("");
            pricing.setText("0.0");
            expiration.setText("No expiration date");
        }
    }


    @FXML
    void updateS() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/tn/esprit/siyahidesktop/updateService.fxml"));
            Parent root = loader.load();

            AddService addServiceController = loader.getController();

            addServiceController.setExistingService(selectedService);

            Scene scene = new Scene(root);

            Stage stage = (Stage) update_button.getScene().getWindow();

            stage.setScene(scene);

            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}

