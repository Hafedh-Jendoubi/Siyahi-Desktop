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
import tn.esprit.models.TypeCredit;
import tn.esprit.services.CreditService;
import tn.esprit.services.ReponseCreditService;
import tn.esprit.services.TypeCreditService;

import java.io.IOException;
import java.sql.Date;
import java.util.List;
import java.util.stream.Collectors;

/*import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;*/

import static tn.esprit.services.UserService.connectedUser;

public class AjouterReponseCredit {
    @FXML
    private DatePicker DateConfirm;

    @FXML
    private TextField DescriptionconfirmTF;

    @FXML
    private TextField SoldeàpTF;

    @FXML
    private TextField autoFinancementTF;

    @FXML
    private TextField nbrconfirmTF;

    @FXML
    ComboBox<Credit> ReferenceCredit; // Ajout de ComboBox pour la référence au crédit

    private final ReponseCreditService reponseCreditService = new ReponseCreditService();
    private final CreditService creditService = new CreditService();
    private final TypeCreditService typeCreditService = new TypeCreditService();

    private int creditId;

    // Configuration des identifiants Twilio
/*    private static final String ACCOUNT_SID = "ACd4df0fc05c27caa57a1852fe00965381";
    private static final String AUTH_TOKEN = "4f7a4460e698a29f2225e01c05ad1227";
    private static final String TWILIO_NUMBER = "+12672824271";*/

    /*// Initialisation de Twilio
    static {
        Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
    }*/

    public void initData(int creditId) {
        this.creditId = creditId;
    }
    @FXML
    public void initialize() {
        loadCredits(); // Charger les crédits dans la ComboBox

        // Listner pour détecter les changements de sélection dans la liste déroulante
        ReferenceCredit.valueProperty().addListener((observable, oldValue, newValue) -> {
            // Vérifie s'il y a une nouvelle valeur sélectionnée
            if (newValue != null) {
                updateFields(newValue);
                updateAutoFinancement(newValue);
            } else {
                // Réinitialise les champs si aucune valeur n'est sélectionnée
                DateConfirm.setValue(null);
                nbrconfirmTF.clear();
                SoldeàpTF.clear();
                autoFinancementTF.clear();
            }
        });
    }

    private void updateAutoFinancement(Credit selectedCredit) {
        // Vérifie si le type de crédit est différent de "Crédit consommation"
        if (!"Credit consommation".equalsIgnoreCase(selectedCredit.getType_credit_nom())) {
            // Calcul de l'auto-financement comme 20% du solde à payer
            float soldeAPayer = Float.parseFloat(SoldeàpTF.getText().replace(",", "."));
            float autoFinancement = soldeAPayer * 0.20f; // 20% du solde à payer
            autoFinancementTF.setText(String.format("%.2f", autoFinancement));
        } else {
            // Si c'est un crédit consommation, l'auto-financement est toujours 0
            autoFinancementTF.setText("0.00");
        }
    }


    private void updateFields(Credit selectedCredit) {
        // Met à jour la date de confirmation avec la date de début de paiement du crédit sélectionné
        DateConfirm.setValue(selectedCredit.getDate_debut_paiement().toLocalDate());
        // Met à jour le champ de nombre de mois de confirmation avec le nombre de mois de paiement du crédit sélectionné
        nbrconfirmTF.setText(String.valueOf(selectedCredit.getNbr_mois_paiement()));

        // obtenir le type de crédit du crédit sélectionné d'après typeCreditService
        TypeCredit typeCredit = typeCreditService.getOne(selectedCredit.getType_credit_id());
        // Calculee taux % associé au type de crédit s'il existe.
        float tauxCreditDirect = typeCredit != null ? typeCredit.getTauxCreditDirect() : 0;
        // Calcule du soldeàpayer d'après le soldedemandé(jointure1)et du tauxCréditDirect(jointure2)
        float soldeDemande = selectedCredit.getSolde_demande();
        float soldeAPayer = (soldeDemande * (100 + tauxCreditDirect)) / 100;

        // Met à jour le solde à payer dans le champ correspondant avec deux décimales
        SoldeàpTF.setText(String.format("%.2f", soldeAPayer));
    }


    private void loadCredits() {
        List<Credit> credits = creditService.getAll();
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

            ReponseCredit response = new ReponseCredit(
                    Integer.parseInt(nbrconfirmTF.getText()),
                    DescriptionconfirmTF.getText(),
                    Float.parseFloat(SoldeàpTF.getText().replace(",", ".")),
                    Float.parseFloat(autoFinancementTF.getText().replace(",", ".")),
                    Date.valueOf(DateConfirm.getValue()),
                    selectedCredit.getId(),
                    connectedUser.getId()
            );

            reponseCreditService.Add(response);
            showAlert(Alert.AlertType.INFORMATION, "Success", "Réponse de crédit ajoutée avec succès.");
            clearFields();

            /*sendSMS(selectedCredit);*/

            RetourReponseLV(event);
        } catch (Exception e) {
            showAlert(Alert.AlertType.ERROR, "Error", e.getMessage());
        }
    }

    /*private void sendSMS(Credit selectedCredit) {
        String message = "Votre demande de crédit a été traitée avec succès. Veuillez consulter notre site. Notez que vous ne pouvez pas modifier ni supprimer votre demande actuellement.";
        Message.creator(
                new PhoneNumber("+21658405717"),
                new PhoneNumber(TWILIO_NUMBER),
                message
        ).create();
    }*/



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
        autoFinancementTF.clear();
        ReferenceCredit.getSelectionModel().clearSelection();
    }
    @FXML
    void RetourReponseLV(ActionEvent event) {
        try {
            // Chargement du fichier FXML
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ListReponseCredit.fxml"));
            Parent root = loader.load();

            // Fermeture de la fenêtre actuelle
            Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            currentStage.close();

            // Affichage de la nouvelle fenêtre avec le contenu de l'interface de réponse crédit
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            showAlert(Alert.AlertType.ERROR, "Error", e.getMessage());
        }
    }


}