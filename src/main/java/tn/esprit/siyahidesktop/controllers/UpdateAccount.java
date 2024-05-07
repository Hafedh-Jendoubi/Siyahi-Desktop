package tn.esprit.siyahidesktop.controllers;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.util.StringConverter;
import tn.esprit.siyahidesktop.models.Compte;
import tn.esprit.siyahidesktop.models.Service;
import tn.esprit.siyahidesktop.services.CompteService;
import tn.esprit.siyahidesktop.services.ServicesService;
import tn.esprit.siyahidesktop.util.MaConnexion;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

public class UpdateAccount {

    @FXML
    private Button back_button;

    @FXML
    private ChoiceBox<Service> services;

    @FXML
    private TextField solde;

    @FXML
    private Button update_button;

    @FXML
    private TextField user;

    private Compte currentAccount;
    private ServicesService servicesService = new ServicesService();

    @FXML
    public void initialize() {
        loadServices();
    }

    private void loadServices() {
        List<Service> allServices = servicesService.getAll();
        services.setItems(FXCollections.observableArrayList(allServices));
        services.setConverter(new StringConverter<Service>() {
            @Override
            public String toString(Service service) {
                return service != null ? service.getNom() : "";
            }
            @Override
            public Service fromString(String string) {
                return services.getItems().stream().filter(service ->
                        service.getNom().equals(string)).findFirst().orElse(null);
            }
        });
    }

    public void setExistingAccount(Compte compte) {
        System.out.println("Called setExistingAccount with: " + (compte != null ? compte.toString() : "null"));
        currentAccount = compte;
        if (compte != null) {
            solde.setText(String.format("%.2f", compte.getSolde()));
            services.getSelectionModel().select(compte.getService());
        } else {
            System.out.println("Received a null account in setExistingAccount");
        }
    }


    @FXML
    void updateAccount(ActionEvent event) {
        if (currentAccount.getId() <0) {
            showAlert("Update Error", "No account selected for update.", Alert.AlertType.ERROR);
            return;
        }
        else showAlert(String.valueOf(currentAccount.getId()),"", Alert.AlertType.INFORMATION);

        try {
            double newSolde = Double.parseDouble(solde.getText().trim());
            if (newSolde < 0) {
                showAlert("Validation Error", "Balance cannot be negative.", Alert.AlertType.ERROR);
                return;
            }

            currentAccount.setSolde(newSolde);
            Service selectedService = services.getSelectionModel().getSelectedItem();
            if (selectedService != null) {
                currentAccount.setService(selectedService);
            }

            updateDatabase(currentAccount);

            /*CompteService compteService = new CompteService();
            compteService.update(currentAccount);*/

            showAlert("Success", "Account updated successfully!", Alert.AlertType.INFORMATION);
            backToMainMenu(null);
        } catch (NumberFormatException e) {
            showAlert("Invalid Input", "Please enter a valid number for balance.", Alert.AlertType.ERROR);
        } catch (Exception e) {
            showAlert("Update Error", "An error occurred while updating the account: " + e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    private void updateDatabase(Compte compte) {
        try (Connection cnx = MaConnexion.getInstance().getCnx();
        ) {

            String sql = "UPDATE `compte_client` SET " +
            "`service_id`=?, `solde`=?, /*`user_id`=?*/ WHERE `id`=?";
            PreparedStatement ps = cnx.prepareStatement(sql);
            ps.setInt(1, compte.getService().getId());
            ps.setDouble(2, compte.getSolde());
            ps.setInt(3, compte.getUser().getId());
            System.out.println("fl sql"+compte.getUser().getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void showAlert(String title, String content, Alert.AlertType type) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }

    @FXML
    void backToMainMenu(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/tn/esprit/siyahidesktop/MainPage.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            Stage stage = (Stage) back_button.getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
