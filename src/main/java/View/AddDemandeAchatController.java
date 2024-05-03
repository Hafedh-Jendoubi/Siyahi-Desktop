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


import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

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

            // Insert new Demande_achat into database
            demandeAchatService.insert(newDemandeAchat);

            Stage stage = (Stage) userIdTextField.getScene().getWindow();
            if (indexDemandeAchatController != null) {
                indexDemandeAchatController.updateTableView();
            }
            stage.close();
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
