package tn.esprit.controllers;

import javafx.fxml.FXML;
import javafx.event.ActionEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import tn.esprit.models.Service;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class AddService {

    @FXML
    private TextArea desc_service;

    @FXML
    private TextField nom_service;

    @FXML
    private Button save_button;

    @FXML
    private Button reset_button;

    @FXML
    private Button menu_button_add;

    @FXML
    void AddS(ActionEvent event) {
        String serviceName = nom_service.getText();
        String serviceDescription = desc_service.getText();

        Service service = new Service(serviceName, serviceDescription);

        addToDatabase(service);
    }

    private void addToDatabase(Service service) {
        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/Siyahi")) {

            String sql = "INSERT INTO services (nom, description) VALUES (?, ?)";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, service.getNom());
            statement.setString(2, service.getDescription());

            statement.executeUpdate();

            statement.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void Reinitialize_Page(ActionEvent event) {
        nom_service.clear();
        desc_service.clear();
    }

    @FXML
    void backToMainPage(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("MainPage.fxml"));
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
    }
}
