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

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class UpdateDemandeAchatController {
    @FXML
    private TextField userIdTextField;

//    @FXML
//    private TextField achatIdTextField;

    @FXML
    private ComboBox<Integer> comboBox;

    @FXML
    private TextField nomTextField;

    @FXML
    private TextField prenomTextField;

    @FXML
    private TextField numTelTextField;

    @FXML
    private TextField typePaiementTextField;

    @FXML
    private TextField cinTextField;

    @FXML
    private TextField adresseTextField;
    @FXML
    private Label dateDemandeLabel;

    AchatService AchatService = new AchatService();

    private IService<Demande_achat> demandeAchatService = new Demande_achatService();

    private IndexDemandeAchatController indexDemandeAchatController = new IndexDemandeAchatController();

    private Demande_achat demandeAchatToUpdate;

    public void setIndexDemandeAchatController(IndexDemandeAchatController controller) {
        this.indexDemandeAchatController = controller;
    }

    public void setDemandeAchatToUpdate(Demande_achat demandeAchat) {
        this.demandeAchatToUpdate = demandeAchat;
        fillFields();
    }

    @FXML
    private void initialize() {

        List<Achat> achats = AchatService.readAll();
        List<Integer> ids = new ArrayList<>();
        for (Achat achat : achats) {
            ids.add(achat.getId());
        }
        comboBox.getItems().addAll(ids);
        
        
        dateDemandeLabel.setText(LocalDate.now().toString());
        setIndexDemandeAchatController(indexDemandeAchatController);
    }

    private void fillFields() {
        userIdTextField.setText(String.valueOf(demandeAchatToUpdate.getUser_id()));
        comboBox.setValue(demandeAchatToUpdate.getAchat_id());
        nomTextField.setText(demandeAchatToUpdate.getNom());
        prenomTextField.setText(demandeAchatToUpdate.getPrenom());
        numTelTextField.setText(demandeAchatToUpdate.getNum_tel());
        typePaiementTextField.setText(demandeAchatToUpdate.getType_paiement());
        cinTextField.setText(String.valueOf(demandeAchatToUpdate.getCin()));
        adresseTextField.setText(demandeAchatToUpdate.getAdresse());
    }

    private boolean isInputValid() {
        // Vérification des champs non vides
        if (userIdTextField.getText().isEmpty() || nomTextField.getText().isEmpty() ||
                prenomTextField.getText().isEmpty() || numTelTextField.getText().isEmpty() ||
                typePaiementTextField.getText().isEmpty() || cinTextField.getText().isEmpty() || adresseTextField.getText().isEmpty()) {
            showAlert("Tous les champs doivent être remplis!");
            return false;
        }

        // Vérification de la longueur de l'adresse
        String adresse = adresseTextField.getText();
        if (adresse.length() < 10 || adresse.length() > 25) {
            showAlert("La longueur de l'adresse doit être comprise entre 10 et 25 caractères!");
            return false;
        }

        // Vérification du type de paiement
        String typePaiement = typePaiementTextField.getText().toUpperCase();
        if (!typePaiement.equals("VIREMENT BANCAIRE") && !typePaiement.equals("CHEQUE") && !typePaiement.equals("PAIEMENT EN LIGNE")) {
            showAlert("Le type de paiement doit être 'Virement bancaire', 'Cheque' ou 'Paiement en ligne'!");
            return false;
        }

        return true;
    }

    @FXML
    private void handleUpdate() {
        if (isInputValid()) {
            // Récupération des données des champs
            Integer userId = Integer.parseInt(userIdTextField.getText());
            Integer achatId = comboBox.getValue();
            String nom = nomTextField.getText();
            String prenom = prenomTextField.getText();
            LocalDate dateDemande = LocalDate.now();
            String numTel = numTelTextField.getText();
            String typePaiement = typePaiementTextField.getText();
            Integer cin = Integer.parseInt(cinTextField.getText());
            String adresse = adresseTextField.getText();
            String etatdemande = "En attente"; // Valeur par défaut pour etatdemande

            // Création de l'objet Demande_achat avec 10 paramètres
            Demande_achat updatedDemandeAchat = new Demande_achat(demandeAchatToUpdate.getId(),userId, achatId, nom, prenom, dateDemande, numTel, typePaiement, cin, adresse, etatdemande);

            // Mise à jour de la demande d'achat dans la base de données
            demandeAchatService.update(updatedDemandeAchat);

            // Fermeture de la fenêtre de mise à jour
            Stage stage = (Stage) userIdTextField.getScene().getWindow();
            if (indexDemandeAchatController != null) {
                indexDemandeAchatController.updateTableView();
            }
            stage.close();
        }
    }

    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Erreur");
        alert.setHeaderText("Champs Invalides");
        alert.setContentText(message);
        alert.showAndWait();
    }
}
