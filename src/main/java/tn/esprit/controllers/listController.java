package tn.esprit.controllers;

import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.control.ListView;
import tn.esprit.models.Conge;
import tn.esprit.models.limitationConge;
import tn.esprit.services.CongeService;
import tn.esprit.services.limitationcongeService;

import java.io.IOException;
import java.util.Optional;

import static tn.esprit.services.UserService.connectedUser;

public class listController {
    @FXML
    private ListView<limitationConge> listViewConge;

    private limitationcongeService congeService = new limitationcongeService(); // Changez CongeService avec votre service réel

    @FXML
    public void initialize() {
        if(connectedUser.getRoles().equals("Admin")){
            ObservableList<limitationConge> conges = FXCollections.observableArrayList(congeService.getLimitationConges(connectedUser.getId())); //Only the logged in user
            listViewConge.setItems(conges);
        }else{
            ObservableList<limitationConge> conges = FXCollections.observableArrayList(congeService.getAll());
            listViewConge.setItems(conges);
        }

        listViewConge.setCellFactory(param -> new ListCell<limitationConge>() {
            @Override
            protected void updateItem(limitationConge item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                    setGraphic(null);
                } else {
                    // Créer les éléments d'interface utilisateur pour afficher les détails de la réponse au crédit
                    Label anneeLabel = new Label("Année: " + item.getAnnee());
                    anneeLabel.setStyle("-fx-font-weight: bold; -fx-text-fill: #387296; -fx-font-size: 14;"); // Bleu clair

                    Label moisLabel = new Label("Mois: " + item.getMois() );
                    moisLabel.setStyle("-fx-font-weight: bold; -fx-text-fill: #387296; -fx-font-size: 14;"); // Bleu clair

                    Label nbrLabel = new Label("Nombre de jours de congé: " + item.getNbr_jours() );
                    nbrLabel.setStyle("-fx-font-weight: bold; -fx-text-fill: #387296; -fx-font-size: 14;"); // Bleu clair



                    // Créer un VBox pour contenir les éléments avec un padding et un espacement spécifiques
                    VBox rootVBox = new VBox(anneeLabel, moisLabel, nbrLabel);

                    rootVBox.setAlignment(Pos.CENTER_LEFT); // Alignement à gauche
                    rootVBox.setSpacing(5); // Espace vertical entre les éléments
                    rootVBox.setPadding(new Insets(10)); // Padding autour du VBox

                    // Appliquer un style au VBox pour définir un fond et une bordure
                    rootVBox.setStyle("-fx-background-color: #f0f0f0; -fx-border-color: #387296; -fx-border-width: 2px;");

                    // Set the layout as the graphic for the ListCell
                    setGraphic(rootVBox);
                }
            }
        });
    }
    private void showAlert(Alert.AlertType alertType, String title, String header, String content) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }
    @FXML
    void supprimerConge(ActionEvent event) {
        limitationcongeService cs = new limitationcongeService();
        limitationConge selectedConge = listViewConge.getSelectionModel().getSelectedItem();

        if (selectedConge == null) {
            // Aucun congé sélectionné, afficher un message d'erreur
            showAlert(Alert.AlertType.ERROR, "Erreur", "Aucun congé sélectionné", "Veuillez sélectionner un congé à supprimer.");
            return;
        }

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation");
        alert.setHeaderText("Voulez-vous vraiment supprimer le conge suivant ?");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            cs.delete(listViewConge.getSelectionModel().getSelectedItem());
            listViewConge.getItems().remove(listViewConge.getSelectionModel().getSelectedItem());
            showSuccessMessage("Congé supprimé avec succès!");
        } else {
            alert.close();
        }
    }
    private void showSuccessMessage(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Succès");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    @FXML
    void ajoutConge(ActionEvent event) {
        try {
            Parent ajout = FXMLLoader.load(getClass().getResource("/Addlimit.fxml"));
            Scene ajoutSecene = new Scene(ajout);
            Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
            window.setScene(ajoutSecene);
            window.setHeight(400); window.setMaxHeight(400); window.setMinHeight(400);
            window.setWidth(606); window.setMaxWidth(600); window.setMinWidth(600);
            window.setTitle("Siyahi Bank | Ajouter un congé");
            window.show();
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }
    @FXML
    void calendrier(ActionEvent event) {
        try {
            Parent ajout = FXMLLoader.load(getClass().getResource("/Addlimit.fxml"));
            Scene ajoutSecene = new Scene(ajout);
            Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
            window.setScene(ajoutSecene);
            window.setHeight(400); window.setMaxHeight(400); window.setMinHeight(400);
            window.setWidth(606); window.setMaxWidth(600); window.setMinWidth(600);
            window.setTitle("Siyahi Bank | Ajouter un congé");
            window.show();
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }
    @FXML
    void edit(ActionEvent event) {
       limitationConge selectedConge = listViewConge.getSelectionModel().getSelectedItem();

        if (selectedConge == null) {
            // Aucun congé sélectionné, afficher un message d'erreur
            showAlert(Alert.AlertType.ERROR, "Erreur", "Aucun congé sélectionné", "Veuillez sélectionner un congé à modifier.");
            return;
        }


        try {
            // Charger la vue de modification du congé
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/modif_limit.fxml"));
            Parent edit = loader.load();

            // Passer le congé sélectionné au contrôleur de la vue de modification
            EditLimitController EditLimitController = loader.getController();
            EditLimitController.initData2(selectedConge);

            // Afficher la fenêtre de modification
            Scene editScene = new Scene(edit);
            Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
            window.setScene(editScene);

            window.setTitle("Siyahi Bank | modifier un congé");
            window.show();
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }
}
