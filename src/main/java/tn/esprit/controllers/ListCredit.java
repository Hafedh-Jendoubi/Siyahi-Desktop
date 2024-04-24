package tn.esprit.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ListView;
import javafx.stage.Stage;
import tn.esprit.models.Credit;
import tn.esprit.services.CreditService;
import tn.esprit.services.ReponseCreditService;


import java.io.IOException;
import java.util.Optional;

public class ListCredit {

    @FXML
    private ListView<Credit> ListCreditLV;

    private CreditService CreditService = new CreditService();


    @FXML
    public void initialize() {
        ObservableList<Credit> credits = FXCollections.observableArrayList(CreditService.getAll());
        ListCreditLV.setItems(credits);
    }

    @FXML
    void AjouterC(ActionEvent event) {
        try {
            Parent ajout = FXMLLoader.load(getClass().getResource("/AjouterCredit.fxml"));
            Scene ajoutSecene = new Scene(ajout);
            Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
            window.setScene(ajoutSecene);
            window.setHeight(400); window.setMaxHeight(400); window.setMinHeight(400);
            window.setWidth(606); window.setMaxWidth(600); window.setMinWidth(600);
            window.setTitle("Siyahi Bank | Ajouter un credit");
            window.show();
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }

    @FXML
    void SupprimerC(ActionEvent event) {
        Credit selectedCredit = ListCreditLV.getSelectionModel().getSelectedItem();

        if (selectedCredit == null) {
            showAlert(Alert.AlertType.ERROR, "Erreur", "Aucun credit sélectionné", "Veuillez sélectionner un crédit à supprimer.");
            return;
        }
        ReponseCreditService reponseCreditService = new ReponseCreditService();
        if (reponseCreditService.isTraite(selectedCredit.getId())) {
            showAlert(Alert.AlertType.WARNING, "Attention", "Crédit déjà traité.", "Le crédit sélectionné a déjà été traité. Vous ne pouvez pas le supprimé.");
            return;
        }
        CreditService cs = new CreditService();
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation");
        alert.setHeaderText("Voulez-vous vraiment supprimer le credit suivant ?");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            cs.Delete(ListCreditLV.getSelectionModel().getSelectedItem());
            ListCreditLV.getItems().remove(ListCreditLV.getSelectionModel().getSelectedItem());
            showSuccessMessage("Credit supprimé avec succès!");
        } else {
            alert.close();
        }
    }
    @FXML
    void AjouterReponseLV(ActionEvent event) {
        Credit selectedCredit = ListCreditLV.getSelectionModel().getSelectedItem();

        if (selectedCredit == null) {
            showAlert(Alert.AlertType.ERROR, "Erreur", "Aucun crédit sélectionné", "Veuillez sélectionner un crédit pour traiter.");
            return;
        }

        ReponseCreditService reponseCreditService = new ReponseCreditService();
        if (reponseCreditService.isTraite(selectedCredit.getId())) {
            showAlert(Alert.AlertType.WARNING, "Attention", "Crédit déjà traité", "Le crédit sélectionné a déjà été traité.");
            return;
        }
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AjouterReponseCredit.fxml"));
            Parent ajoutReponse = loader.load();

            AjouterReponseCredit ajouterReponseCredit = loader.getController();
            ajouterReponseCredit.initData(selectedCredit.getId());

            // Initialiser la sélection dans le ComboBox "ReferenceCredit" avec le crédit sélectionné
            ajouterReponseCredit.ReferenceCredit.getSelectionModel().select(selectedCredit);

            Scene ajoutReponseScene = new Scene(ajoutReponse);
            Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
            window.setScene(ajoutReponseScene);
            window.setHeight(400); window.setMaxHeight(400); window.setMinHeight(400);
            window.setWidth(606); window.setMaxWidth(600); window.setMinWidth(600);
            window.setTitle("Siyahi Bank | Ajouter une réponse à un crédit");
            window.show();
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }

    private void showAlert(Alert.AlertType alertType, String title, String content) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
    private void showSuccessMessage(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Succès");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    @FXML
    void ModifierLV(ActionEvent event) {
        Credit selectedCredit = ListCreditLV.getSelectionModel().getSelectedItem();

        if (selectedCredit == null) {
            showAlert(Alert.AlertType.ERROR, "Erreur", "Aucun credit sélectionné", "Veuillez sélectionner un credit à modifier.");
            return;
        }
        ReponseCreditService reponseCreditService = new ReponseCreditService();
        if (reponseCreditService.isTraite(selectedCredit.getId())) {
            showAlert(Alert.AlertType.WARNING, "Attention", "Crédit déjà traité.", "Le crédit sélectionné a déjà été traité. Vous ne pouvez pas le modifié.");
            return;
        }

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ModifierCredit.fxml"));
            Parent edit = loader.load();

            ModifierCredit ModifierCredit = loader.getController();
            ModifierCredit.initData(selectedCredit);

            Scene editScene = new Scene(edit);
            Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
            window.setScene(editScene);
            window.setHeight(400); window.setMaxHeight(400); window.setMinHeight(400);
            window.setWidth(606); window.setMaxWidth(600); window.setMinWidth(600);
            window.setTitle("Siyahi Bank | Modifier un credit");
            window.show();
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }
    private void showAlert(Alert.AlertType alertType, String title, String header, String content) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }
}