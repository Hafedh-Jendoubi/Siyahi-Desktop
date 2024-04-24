package tn.esprit.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import tn.esprit.models.Credit;
import tn.esprit.models.ReponseCredit;
import tn.esprit.services.CreditService;
import tn.esprit.services.ReponseCreditService;

import java.io.IOException;
import java.sql.Date;
import java.util.List;
import java.util.stream.Collectors;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;

public class AjouterReponseCredit {
    @FXML
    private DatePicker DateConfirm;

    @FXML
    private TextField DescriptionconfirmTF;

    @FXML
    private TextField SoldeàpTF;

    @FXML
    private TextField nbrconfirmTF;

    @FXML
    ComboBox<Credit> ReferenceCredit; // Ajout de ComboBox pour la référence au crédit

    private final ReponseCreditService reponseCreditService = new ReponseCreditService();
    private final CreditService creditService = new CreditService();
    private int creditId;

    // Configuration des identifiants Twilio
    private static final String ACCOUNT_SID = "ACd4df0fc05c27caa57a1852fe00965381";
    private static final String AUTH_TOKEN = "5fac94566b5277862bb218422aff5bf1";
    private static final String TWILIO_NUMBER = "+12672824271";

    // Initialisation de Twilio
    static {
        Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
    }

    public void initData(int creditId) {
        this.creditId = creditId;
    }


    @FXML
    public void initialize() {
        loadCredits(); // Charger la liste des crédits dans la ComboBox lors de l'initialisation du contrôleur
    }

    private void loadCredits() {
        List<Credit> credits = creditService.getAll();

        // Filtrer les crédits déjà traités
        List<Credit> creditsNonTraites = credits.stream()
                .filter(credit -> !reponseCreditService.isTraite(credit.getId()))
                .collect(Collectors.toList());

        ReferenceCredit.getItems().addAll(creditsNonTraites);
    }


    @FXML
    void AjouterRC(ActionEvent event) {
        try {
            Credit selectedCredit = ReferenceCredit.getValue();
            if (selectedCredit == null) {
                showAlert(Alert.AlertType.ERROR, "Error", "Veuillez sélectionner un crédit.");
                return;
            }

            // Ajout de la réponse de crédit
            reponseCreditService.Add(new ReponseCredit(
                    Integer.parseInt(nbrconfirmTF.getText()),
                    DescriptionconfirmTF.getText(),
                    Float.parseFloat(SoldeàpTF.getText()),
                    Date.valueOf(DateConfirm.getValue()),
                    selectedCredit.getId()
            ));
            showAlert(Alert.AlertType.INFORMATION, "Success", "Réponse de crédit ajoutée avec succès.");
            clearFields();

            // Envoi du SMS via Twilio
            sendSMS(selectedCredit);

            // Redirection vers l'interface ListReponseCredit
            RetourReponseLV(event);
        } catch (Exception e) {
            showAlert(Alert.AlertType.ERROR, "Error", e.getMessage());
        }
    }

    private void sendSMS(Credit selectedCredit) {
        String message = "Votre demande de crédit a été traitée avec succès. Veuillez consulter notre site. Notez que vous ne pouvez pas modifier ni supprimer votre demande actuellement.";
        Message.creator(
                new PhoneNumber("+21658405717"), // Numéro du destinataire new PhoneNumber("votre_numero_destinataire"),
                new PhoneNumber(TWILIO_NUMBER), // Numéro Twilio
                message
        ).create();
    }


    @FXML
    void RetourReponseLV(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ListReponseCredit.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            showAlert(Alert.AlertType.ERROR, "Error", e.getMessage());
        }
    }

    private void showAlert(Alert.AlertType alertType, String title, String content) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }

    private void clearFields() {
        DateConfirm.setValue(null);
        DescriptionconfirmTF.clear();
        nbrconfirmTF.clear();
        SoldeàpTF.clear();
        ReferenceCredit.getSelectionModel().clearSelection();
    }
}
