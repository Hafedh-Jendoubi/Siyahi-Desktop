package View;

import Entity.Achat;
import Service.AchatService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import Entity.Demande_achat;
import Service.Demande_achatService;
import Service.IService;
import javafx.stage.Stage;
import org.controlsfx.control.Notifications;

import java.util.*;
import java.io.IOException;
import java.sql.SQLException;

import static tn.esprit.services.UserService.connectedUser;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.*;
/*import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;*/

/*import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;*/

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
        nomTextField.setText(connectedUser.getFirst_name());
        prenomTextField.setText(connectedUser.getLast_name());
        numTelTextField.setText(String.valueOf(connectedUser.getPhone_number()));
        cinTextField.setText(String.valueOf(connectedUser.getCin()));
        adresseTextField.setText(connectedUser.getAddress());

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
    private void sendEmail(String toEmail, String msg) {
        final String username = "waves.esprit@gmail.com";
        final String password = "tgao tbqg wudl aluo";
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");
        /*Session session = Session.getInstance(props,
                new Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(username, password);
                    }
                });
        try {
            javax.mail.Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress("waves.esprit@gmail.com"));
            message.setRecipients(javax.mail.Message.RecipientType.TO, InternetAddress.parse(toEmail));
            message.setSubject("Rendez-Vous");
            message.setText(msg);
            Transport.send(message);
            System.out.println("OTP email sent successfully!");

        } catch (MessagingException e) {
            e.printStackTrace();
        }*/
    }
    @FXML
    private void handleSave(ActionEvent actionEvent) {

        if (isInputValid()) {
            try {
                // Get data from fields
                Integer achatId = comboBox.getValue(); // Assuming comboBox contains the correct value
                String nom = connectedUser.getFirst_name();
                String prenom = connectedUser.getLast_name();
                LocalDate dateDemande = LocalDate.now();
                String numTel = String.valueOf(connectedUser.getPhone_number());
                String typePaiement = typePaiementTextField.getText();
                Integer cin = connectedUser.getCin();
                String adresse = connectedUser.getAddress();
                String etatdemande = "En attente"; // Default value for etatdemande

                // Create new Demande_achat object
                Demande_achat newDemandeAchat = new Demande_achat(connectedUser.getId(), achatId, nom, prenom, dateDemande, numTel, typePaiement, cin, adresse, etatdemande);

                // Insert new Demande_achat into database
                demandeAchatService.insert(newDemandeAchat);

                // Send SMS using Twilio
                sendSMS(numTel);
                String msg = "Votre demande d'achat a été confirmé.\n\n" +
                        "Détails d'achat :\n" +
                        "nom : " + nom.toString() + "\n" +
                        "prenom : " + prenom.toString() + "\n\n" +
                        "numTel : " + numTel.toString() + "\n\n" +
                        "typePaiement : " + typePaiement.toString() + "\n\n" +
                        "adresse : " + adresse.toString() + "\n\n" +
                        "Merci pour votre achat !";

                // Envoi de l'email avec le message contenant les détails du rendez-vous
                sendEmail("imedkhlifi16@gmail.com", msg);

                // Show success message
                showSuccessAlert();

                // Update table view if applicable
                if (indexDemandeAchatController != null) {
                    indexDemandeAchatController.updateTableView();
                }

                // Navigate to FrontUser.fxml
                navigateToFrontUser(actionEvent);

            } catch (NumberFormatException e) {
                // Handle number format exception (e.g., parsing userId, achatId, cin)
                displayErrorAlert("Erreur de format de nombre : " + e.getMessage());
            } catch (Exception e) {
                // Handle other exceptions
                displayErrorAlert("Une erreur est survenue : " + e.getMessage());
            }
        }
    }

    private void sendSMS(String numTel) {
        // Twilio credentials and number
        String accountSid = "AC8e67691f6f5c772705c3d1be23eb7fbe";
        String authToken = "73be092c6f48b74f863cc26b0fd32abb";
        String twilioNumber = "+18283944491";

        /*// Initialize Twilio
        Twilio.init(accountSid, authToken);

        // Send SMS
        Message message = Message.creator(
                        new PhoneNumber("+216" + numTel),  // Recipient's phone number
                        new PhoneNumber(twilioNumber),     // Your Twilio phone number
                        "Votre demande d'achat a été enregistrée avec succès. Merci!")
                .create();

        System.out.println("Message SID: " + message.getSid()); // Print SID for debugging purposes*/
    }

    private void showSuccessAlert() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Succès");
        alert.setHeaderText(null);
        alert.setContentText("Votre demande d'achat a été enregistrée avec succès.");
        alert.showAndWait();
    }

    private void displayErrorAlert(String errorMessage) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Erreur");
        alert.setHeaderText(null);
        alert.setContentText(errorMessage);
        alert.showAndWait();
    }

    private void navigateToFrontUser(ActionEvent actionEvent) throws IOException {
        Stage window = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        window.close();
    }



    private void showAlertDialog(Alert.AlertType type, String title, String header, String content) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }

    @FXML

    private void showNotification(String title, String message) {
        Notifications.create()
                .title(title)
                .text(message)
                .darkStyle() // Vous pouvez personnaliser le style ici
                .show();
    }
}
