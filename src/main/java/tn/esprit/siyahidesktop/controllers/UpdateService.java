package tn.esprit.siyahidesktop.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import tn.esprit.siyahidesktop.models.Service;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;

public class UpdateService {

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
    public void SaveChanges(ActionEvent actionEvent) {
        String serviceName = nom_service.getText();
        String serviceDescription = desc_service.getText();
        Double servicePrice = Double.parseDouble(desc_service.getText());
        LocalDate expirationService= expiration.getValue();


        Service service = new Service(serviceName, serviceDescription,servicePrice,expirationService);

        updateDatabase(service);
    }
    private void updateDatabase(Service service) {
        try (Connection connection = DriverManager.getConnection("")) {

            String sql = "UPDATE service SET name = ?, description = ?, frais = ?, expiration = ? WHERE id = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, service.getNom());
            statement.setString(2, service.getDescription());
            statement.setDouble(3,service.getPricing());
            LocalDate expirationService = service.getExpiration_date();
            if (expirationService != null) {
                statement.setDate(4, java.sql.Date.valueOf(expirationService));
            } else {
                statement.setNull(4, java.sql.Types.DATE); // Set NULL if there is no date
            }            statement.setInt(5, service.getId());
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
}
