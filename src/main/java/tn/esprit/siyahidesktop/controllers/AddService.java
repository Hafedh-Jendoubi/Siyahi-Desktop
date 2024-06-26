package tn.esprit.siyahidesktop.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import tn.esprit.siyahidesktop.models.Service;
import tn.esprit.siyahidesktop.util.MaConnexion;

import java.io.IOException;
import java.sql.*;
import java.time.LocalDate;
import java.util.Arrays;

public class AddService {

    @FXML
    private TextArea desc_service;

    @FXML
    private TextField nom_service;

    @FXML
    private TextField frais_service;

    @FXML
    private DatePicker expiration;

    @FXML
    private Button save_button;

    @FXML
    private Button reset_button;

    @FXML
    private Button menu_button_add;
    private Service existingService;


    Connection cnx = MaConnexion.getInstance().getCnx();

    @FXML
    void AddS(ActionEvent event) {
        try {
            String serviceName = nom_service.getText().trim();
            String serviceDescription = desc_service.getText().trim();
            String fraisInput = frais_service.getText().trim();

            if (serviceName.isEmpty() || serviceDescription.isEmpty() || fraisInput.isEmpty()) {
                showAlert("Input Error", "Tous les champs doivent être remplis.");
                return;
            }

            if (!(serviceName.matches("[a-zA-Z]+") && serviceName.length() <= 15)) {
                showAlert("Input Error", "Nom du service ne doit pas dépasser 15 caractères et ne contient pas de chiffres ou symboles.");
                return;
            }

            Double fraisService = Double.parseDouble(fraisInput);
            if (fraisService < 0) {
                showAlert("Input Error", "Frais du service ne deoit pas être négatif");
                return;
            }

            LocalDate expirationService = expiration.getValue();
            if (expirationService != null && expirationService.isBefore(LocalDate.now())) {
                showAlert("Input Error", "Date d'expiration ne peut pas être dans le passé.");
                return;
            }

            Service service = new Service(serviceName, serviceDescription, fraisService, expirationService);
            addToDatabase(service);
            showSuccessAlert("Success", "Service added successfully!");
            Reinitialize_Page(null); // Reset form on successful save
        } catch (NumberFormatException e) {
            showAlert("Input Error", "Please enter a valid number for the service fee.");
        } catch (Exception e) {
            showAlert("Error", "An error occurred while adding the service.");
        }
    }

    private void addToDatabase(Service service) {
        String sql = "INSERT INTO service(name, description, frais, expiration) VALUES (?, ?, ?, ?)";
        try {
            PreparedStatement statement = cnx.prepareStatement(sql);
            statement.setString(1, service.getNom());
            statement.setString(2, service.getDescription());
            statement.setDouble(3, service.getPricing());
            statement.setDate(4, service.getExpiration_date() != null ? Date.valueOf(service.getExpiration_date()) : null);
            statement.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error inserting service: " + e.getMessage());
            System.err.println(e.getSQLState());
            System.err.println(Arrays.toString(e.getStackTrace()));
        }
    }

    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setContentText(content);
        alert.showAndWait();
    }

    private void showSuccessAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setContentText(content);
        alert.showAndWait();
    }

    @FXML
    void Reinitialize_Page(ActionEvent event) {
        nom_service.clear();
        desc_service.clear();
        frais_service.clear();
        expiration.setValue(null);
    }

    @FXML
    void backToMainPage(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/tn/esprit/siyahidesktop/MainPage.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            Stage stage = (Stage) menu_button_add.getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setExistingService(Service service) {
        this.existingService = service;
        updateFormFields();
    }

    private void updateFormFields() {
        if (existingService != null) {
            nom_service.setText(existingService.getNom());
            desc_service.setText(existingService.getDescription());
            frais_service.setText(String.valueOf(existingService.getPricing()));

            if (existingService.getExpiration_date() != null) {
                expiration.setValue(existingService.getExpiration_date());
            } else {
                expiration.setValue(null); // Clear the field if no date
            }
        }
    }
}
