package tn.esprit.siyahidesktop.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.event.ActionEvent;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.stage.Stage;
import javafx.util.StringConverter;

import tn.esprit.siyahidesktop.models.Service;
import tn.esprit.siyahidesktop.models.Compte;

import tn.esprit.siyahidesktop.models.SharedService;
import tn.esprit.siyahidesktop.models.User;
import tn.esprit.siyahidesktop.services.ServicesService;
import tn.esprit.siyahidesktop.services.CompteService;
import tn.esprit.siyahidesktop.services.UserService;
import tn.esprit.siyahidesktop.util.MaConnexion;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;


public class AddAccount {
    @FXML private ComboBox<User> userComboBox;
    @FXML private ChoiceBox<Service> services;
    @FXML private TextField rib, solde;
    @FXML private Button generate_button, add_button, reset_button,menu_button;
    @FXML private TextField userSearchField;


    private ServicesService servicesService = new ServicesService();
    private UserService userService = new UserService();
    private ObservableList<User> allUsers;
    private Connection cnx = MaConnexion.getInstance().getCnx();

    @FXML
    public void initialize() {
        loadServices();
        loadUsers();
        setupServiceSelection();
        setupUserFiltering();
        updateSelectedService();
    }

    private void updateSelectedService() {
        Service selectedService = SharedService.getInstance().getSelectedService();
        if (selectedService != null) {
            services.getSelectionModel().select(selectedService);
        } else {
            services.getSelectionModel().clearSelection();
        }
    }

/*    private void loadServices() {
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

        // Listen for changes to the selected service from SharedService
        SharedService.getInstance().selectedServiceProperty().addListener((obs, oldService, newService) -> {
            if (newService != null) {
                services.getSelectionModel().select(newService);
            } else {
                services.getSelectionModel().clearSelection();
            }
        });
    }*/

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

        updateSelectedService();
    }


    private Service findServiceByName(String serviceName) {
        return services.getItems().stream()
                .filter(service -> service.getNom().equalsIgnoreCase(serviceName))
                .findFirst().orElse(null);
    }



    private void loadUsers() {
        try {
            allUsers = FXCollections.observableArrayList(userService.getAll());
            userComboBox.setItems(allUsers);
            userComboBox.setConverter(new StringConverter<User>() {
                @Override
                public String toString(User user) {
                    return user != null ? user.getFullName() + " - " + user.getCin() : "";
                }

                @Override
                public User fromString(String string) {
                    return userComboBox.getItems().stream()
                            .filter(item -> (item.getFullName() + " - " + item.getCin()).equals(string))
                            .findFirst().orElse(null);
                }
            });
        } catch (Exception e) {
            e.printStackTrace();        }
    }

    private void setupUserFiltering() {
        userSearchField.textProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal == null || newVal.isEmpty()) {
                userComboBox.setItems(allUsers);
            } else {
                ObservableList<User> filteredUsers = allUsers.filtered(user ->
                        user.getFullName().toLowerCase().contains(newVal.toLowerCase()) ||
                                String.valueOf(user.getCin()).contains(newVal)
                );
                userComboBox.setItems(filteredUsers);
            }
        });
    }

    private void setupServiceSelection() {
        // This function could be used to react to service selection changes
        services.getSelectionModel().selectedItemProperty().addListener((obs, oldService, newService) -> {
            // Handle changes if needed
        });
    }

    @FXML
    void addCompte(ActionEvent event) {
        if (rib.getText().isEmpty() || services.getValue() == null || userComboBox.getValue() == null || solde.getText().isEmpty()) {
            showAlert("Error", "All fields must be filled, and a service and user must be selected.", Alert.AlertType.ERROR);
            return;
        }

        try {
            long ribValue = Long.parseLong(rib.getText());
            double soldeValue = Double.parseDouble(solde.getText());
            Timestamp currentTimestamp = new Timestamp(System.currentTimeMillis());
            Service selectedService = services.getValue();
            User selectedUser = userComboBox.getValue();

            Compte newCompte = new Compte(ribValue, currentTimestamp, selectedService, soldeValue, selectedUser);
            CompteService compteService = new CompteService();
            compteService.add(newCompte);

            showAlert("Success", "Account successfully added!", Alert.AlertType.INFORMATION);
            reset(null);
        } catch (NumberFormatException e) {
            showAlert("Error", "Invalid number format in RIB or Solde fields.", Alert.AlertType.ERROR);
        } catch (SQLException e) {
            showAlert("Database Error", "Failed to add account: " + e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    @FXML
    void generate_rib(ActionEvent event) {
        long uniqueNumber = (long) (Math.random() * 10000000000000000L) + 1000000000000000L;
        rib.setText(String.valueOf(uniqueNumber));
    }

    @FXML
    void reset(ActionEvent event) {
        userComboBox.getSelectionModel().clearSelection();
        services.getSelectionModel().clearSelection();
        rib.clear();
        solde.clear();
        userSearchField.clear();
    }

    private void showAlert(String title, String content, Alert.AlertType type) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
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

    @FXML
    void goto_service_proposition(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/tn/esprit/siyahidesktop/service_proposition.fxml"));
            Parent root = loader.load();

            Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();

            stage.setScene(new Scene(root));

            // Show the stage
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
