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

    Connection cnx = MaConnexion.getInstance().getCnx();

    @FXML
    void AddS(ActionEvent event) {
        try {
            String serviceName = nom_service.getText().trim();
            String serviceDescription = desc_service.getText().trim();
            String fraisInput = frais_service.getText().trim();

            if (serviceName.isEmpty() || serviceDescription.isEmpty() || fraisInput.isEmpty()) {
                showAlert("Input Error", "All fields must be filled out.");
                return;
            }

            Double fraisService = Double.parseDouble(fraisInput);
            if (fraisService < 0) {
                showAlert("Input Error", "Service fee must be non-negative.");
                return;
            }

            LocalDate expirationService = expiration.getValue();
            if (expirationService != null && expirationService.isBefore(LocalDate.now())) {
                showAlert("Input Error", "Expiration date cannot be in the past.");
                return;
            }

            Service service = new Service(serviceName, serviceDescription, fraisService, expirationService);
            addToDatabase(service);
            showAlert("Success", "Service added successfully!");
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

    public void setExistingService(Service selectedService) {
        // Optional: Set an existing service to edit
    }
}
