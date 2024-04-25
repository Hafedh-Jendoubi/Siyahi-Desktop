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

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class UpdateService {

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
    public void SaveChanges(ActionEvent actionEvent) {
        String serviceName = nom_service.getText();
        String serviceDescription = desc_service.getText();

        Service service = new Service(serviceName, serviceDescription);

        updateDatabase(service);
    }
    private void updateDatabase(Service service) {
        try (Connection connection = DriverManager.getConnection("")) {

            String sql = "UPDATE services SET nom = ?, description = ? WHERE id = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, service.getNom());
            statement.setString(2, service.getDescription());
            statement.setInt(3, service.getId());
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
}
