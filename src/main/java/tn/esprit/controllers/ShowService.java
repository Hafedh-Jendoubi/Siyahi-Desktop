package tn.esprit.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import tn.esprit.models.Service;
import tn.esprit.services.ServicesService;

import java.io.IOException;

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
    private Button update_button;

    @FXML
    void DeleteS(ActionEvent event) {

    }

    @FXML
    void UpdateS(ActionEvent event) {

    }

    @FXML
    void backToMainPage(ActionEvent event) {

    }

    private Service selectedService;
    private final ServicesService serviceService = new ServicesService();
    public void setServiceDetails(Service service) {
        this.selectedService = service;
        nom_service.setText(service.getNom());
        desc_service.setText(service.getDescription());
    }

    @FXML
    void updateS() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("updateService.fxml"));
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

