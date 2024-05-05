package View;

import Entity.Achat;
import Service.AchatService;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import Entity.Demande_achat;
import Service.Demande_achatService;
import Service.IService;
import javafx.stage.Stage;
import org.controlsfx.control.Notifications;

import java.sql.SQLException;



import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.*;
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;

public class AddDemandeAchatController {
    @FXML
    private TextField userIdTextField;

//    @FXML
//    private TextField achatIdTextField;

    @FXML
    private TextField nomTextField;

    @FXML
    private TextField prenomTextField;

    @FXML
    private TextField numTelTextField;
    @FXML
    private ComboBox<Integer> comboBox;

    @FXML
    private TextField typePaiementTextField;

    @FXML
    private TextField cinTextField;

    @FXML
    private TextField adresseTextField;
    @FXML
    private Label dateDemandeLabel;

    private IService<Demande_achat> demandeAchatService = new Demande_achatService();
    private IService<Achat> AchatService = new AchatService();

    private IndexDemandeAchatController indexDemandeAchatController = new IndexDemandeAchatController();

    public void setIndexDemandeAchatController(IndexDemandeAchatController controller) {
        this.indexDemandeAchatController = controller;
    }

    @FXML
    private void initialize() {
        List<Achat> achats = AchatService.readAll();
        List<Integer> ids = new ArrayList<>();
        for (Achat achat : achats) {
            ids.add(achat.getId());
        }
        comboBox.getItems().addAll(ids);
        comboBox.setValue(ids.get(0));

        dateDemandeLabel.setText(LocalDate.now().toString());
        setIndexDemandeAchatController(indexDemandeAchatController);
    }

    private boolean isInputValid() {
        String errorMessage = "";

        if (userIdTextField.getText().isEmpty() || nomTextField.getText().isEmpty() ||
                prenomTextField.getText().isEmpty() || numTelTextField.getText().isEmpty() ||
                typePaiementTextField.getText().isEmpty() || cinTextField.getText().isEmpty() || adresseTextField.getText().isEmpty()) {
            errorMessage += "Tous les champs doivent être remplis!\n";
        }


        String adresse = adresseTextField.getText();
        if (adresse.length() < 10 || adresse.length() > 25) {
            errorMessage += "La longueur de l'adresse doit être comprise entre 10 et 25 caractères!\n";
        }

        String typePaiement = typePaiementTextField.getText().toUpperCase();
        if (!typePaiement.equals("VIREMENT BANCAIRE") && !typePaiement.equals("CHEQUE") && !typePaiement.equals("PAIEMENT EN LIGNE")) {
            errorMessage += "Le type de paiement doit être 'Virement bancaire', 'Cheque' ou 'Paiement en ligne'!\n";
        }

        if (errorMessage.isEmpty()) {
            return true;
        } else {
            showAlertDialog(Alert.AlertType.ERROR, "Erreur", "Champs Invalides", errorMessage);
            return false;
        }
    }

    @FXML
    private void handleSave() {
        if (isInputValid()) {
            // Get data from fields
            Integer userId = Integer.parseInt(userIdTextField.getText());
            Integer achatId = comboBox.getValue();
            String nom = nomTextField.getText();
            String prenom = prenomTextField.getText();
            LocalDate dateDemande = LocalDate.now();
            String numTel = numTelTextField.getText();
            String typePaiement = typePaiementTextField.getText();
            Integer cin = Integer.parseInt(cinTextField.getText());
            String adresse = adresseTextField.getText();
            String etatdemande = "En attente"; // Default value for etatdemande

            // Create new Demande_achat object with 10 parameters
            Demande_achat newDemandeAchat = new Demande_achat(userId, achatId, nom, prenom, dateDemande, numTel, typePaiement, cin, adresse, etatdemande);

            try {
                // Insert new Demande_achat into database
                demandeAchatService.insert(newDemandeAchat);

                // Twilio credentials and number
                String accountSid = "AC8e67691f6f5c772705c3d1be23eb7fbe";
                String authToken = "73be092c6f48b74f863cc26b0fd32abb";
                String twilioNumber = "+18283944491";

                // Initialize Twilio
                Twilio.init(accountSid, authToken);

                // Send SMS
                Message message = Message.creator(
                                new PhoneNumber("+216" + numTel),  // Replace with recipient's phone number
                                new PhoneNumber(twilioNumber),               // Your Twilio phone number
                                "Votre demande d'achat a été enregistrée avec succès. Merci!")
                        .create();

                System.out.println("Message SID: " + message.getSid()); // Print SID for debugging purposes

                // Show a success message
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Success");
                alert.setContentText("Votre demande d'achat a été enregistrée avec succès.");
                alert.showAndWait();

                // Update table view if applicable
                if (indexDemandeAchatController != null) {
                    indexDemandeAchatController.updateTableView();
                }

                // Close the window
                Stage stage = (Stage) userIdTextField.getScene().getWindow();
                stage.close();

            } catch (Exception e) {
                // Display an error message if any exception occurs
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setContentText("Une erreur est survenue : " + e.getMessage());
                alert.showAndWait();
            }
        }
    }
    private void showAlertDialog(Alert.AlertType type, String title, String header, String content) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
