package tn.esprit.siyahidesktop.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import tn.esprit.siyahidesktop.models.Service;
import tn.esprit.siyahidesktop.models.SharedService;
import tn.esprit.siyahidesktop.services.ServicesService;
import java.io.IOException;
import java.util.List;

public class Service_suggestion {
    @FXML private Button Resultat;
    @FXML private TextField age_field;
    @FXML private CheckBox en_ligne;
    @FXML private CheckBox epargne;
    @FXML private CheckBox international;
    @FXML private RadioButton morale;
    @FXML private RadioButton physique;
    @FXML private TextField result_field;
    @FXML private CheckBox retrait;
    @FXML private TextField salary;
    @FXML private AnchorPane salary_field;
    @FXML private RadioButton sect_prive;
    @FXML private RadioButton sect_public;
    @FXML private Button valider;

    private ToggleGroup personTypeGroup = new ToggleGroup();  // ToggleGroup for morale and physique
    private ToggleGroup EtatTypeGroup = new ToggleGroup();  // ToggleGroup for morale and physique

    private ServicesService serviceService = new ServicesService();
    Service bestService = null;

    @FXML
    public void initialize() {
        setupToggleGroups();
    }

    private void setupToggleGroups() {
        // Add both radio buttons to the same toggle group
        physique.setToggleGroup(personTypeGroup);
        morale.setToggleGroup(personTypeGroup);
        sect_prive.setToggleGroup(EtatTypeGroup);
        sect_public.setToggleGroup(EtatTypeGroup);
    }

    @FXML
    void show_salary_field(ActionEvent event) {
        // Toggle visibility based on the selection of 'physique'
        salary_field.setVisible(physique.isSelected());
    }

    @FXML
    void show_result() {
        try {
            int age = age_field.getText().isEmpty() ? 0 : Integer.parseInt(age_field.getText());
            double salaire = salary.getText().isEmpty() ? 0 : Double.parseDouble(salary.getText()); // Check if the salary field is empty
            boolean isPhysique = physique.isSelected();
            boolean isPublicSector = sect_public.isSelected();
            boolean isRetrait = retrait.isSelected();
            boolean isOnline = en_ligne.isSelected();
            boolean isInternational = international.isSelected();
            boolean isSavings = epargne.isSelected();

            List<Service> services = serviceService.getAll();

            if (age > 60) {
                bestService = findServiceByName(services, "Epargne");
            } else if (!isPhysique) {
                bestService = findServiceByName(services, "Business");
            } else if (salaire >= 3000) {
                bestService = findServiceByName(services, "VIP");
            } else if (isPublicSector && (isRetrait || isOnline)) {
                bestService = findServiceByName(services, "Courant");
            } else if (isInternational) {
                bestService = findServiceByName(services, "International");
            } else {
                bestService = findServiceByName(services, "Epargne");
            }

            if (bestService != null) {
                result_field.setText(bestService.getNom());
            } else {
                showAlert("Echec", "Aucun service est compatible à vos coordonnées.", Alert.AlertType.NONE);
            }
        } catch (NumberFormatException e) {
            showAlert("Input Error", "Please ensure all fields are correctly filled, including numeric values.", Alert.AlertType.ERROR);
        }
    }


    private Service findServiceByName(List<Service> services, String name) {
        for (Service service : services) {
            if (service.getNom().equalsIgnoreCase(name)) {
                return service;
            }
        }
        return null;
    }

    private void showAlert(String title, String content, Alert.AlertType type) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }

    @FXML
    void valider_choix(ActionEvent event) {
        if (bestService != null) {
            SharedService.getInstance().setSelectedService(bestService); // Save the selected service globally
            goBackToAccountPage();  // Navigate back to the AddAccount page
        } else {
            showAlert("Validation Error", "No service has been selected!", Alert.AlertType.ERROR);
        }
    }

    private void goBackToAccountPage() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/tn/esprit/siyahidesktop/AddAccount.fxml")); // Correct this path
            Parent root = loader.load();
            Scene scene = new Scene(root);
            Stage stage = (Stage) valider.getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            showAlert("Navigation Error", "Failed to load the Account page.", Alert.AlertType.ERROR);
        }
    }
}
