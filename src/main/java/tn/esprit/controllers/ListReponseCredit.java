package tn.esprit.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.VBox;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import tn.esprit.models.Credit;
import tn.esprit.models.ReponseCredit;
import tn.esprit.services.ReponseCreditService;

import java.io.IOException;
import java.util.Optional;

import static tn.esprit.controllers.ProfileController.profileCheck;
import static tn.esprit.services.UserService.connectedUser;

public class ListReponseCredit {
    @FXML
    private ListView<ReponseCredit> ListReponseCreditLV;

    @FXML
    private MenuItem menuItem;

    @FXML
    private Circle circle;

    private ReponseCreditService ReponseCreditService = new ReponseCreditService();

    @FXML
    public void initialize() {
        try {
            String imageName = connectedUser.getImage();
            String imagePath = "/uploads/user/" + imageName;
            Image image = new Image(getClass().getResource(imagePath).toExternalForm());
            circle.setFill(new ImagePattern(image));
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }

        ObservableList<ReponseCredit> credits = FXCollections.observableArrayList(ReponseCreditService.getAll());
        ListReponseCreditLV.setItems(credits);

        ListReponseCreditLV.setCellFactory(param -> new ListCell<ReponseCredit>() {
            @Override
            protected void updateItem(ReponseCredit item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                    setGraphic(null);
                } else {
                    // Créer les éléments d'interface utilisateur pour afficher les détails de la réponse au crédit
                    Label creditIdLabel = new Label("ID crédit: " + item.getCredit_id());
                    creditIdLabel.setStyle("-fx-font-weight: bold; -fx-text-fill: #387296; -fx-font-size: 14;"); // Bleu clair

                    Label soldeLabel = new Label("Solde à payer: " + item.getSolde_a_payer() + " TND");
                    soldeLabel.setStyle("-fx-font-weight: bold; -fx-text-fill: #387296; -fx-font-size: 14;"); // Bleu clair

                    Label autoFinancementLabel = new Label("Auto-financement: " + item.getauto_financement() + " TND");
                    autoFinancementLabel.setStyle("-fx-font-weight: bold; -fx-text-fill: #387296; -fx-font-size: 14;"); // Bleu clair

                    Label datePaiementLabel = new Label("Date de paiement: " + item.getDate_debut_paiement() +
                            " sur " + item.getNbr_mois_paiement() + " mois");
                    datePaiementLabel.setStyle("-fx-font-weight: bold; -fx-text-fill: #387296; -fx-font-size: 14;"); // Bleu clair

                    Label descriptionLabel = new Label("Description: " + item.getDescription());
                    descriptionLabel.setStyle("-fx-font-weight: bold; -fx-text-fill: #387296; -fx-font-size: 14;"); // Bleu clair

                    // Créer un VBox pour contenir les éléments avec un padding et un espacement spécifiques
                    VBox rootVBox = new VBox(creditIdLabel, soldeLabel, autoFinancementLabel, datePaiementLabel, descriptionLabel);
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

    @FXML
    void AjouterRC(ActionEvent event) {
        try {
            Parent ajoute = FXMLLoader.load(getClass().getResource("/AjouterReponseCredit.fxml"));
            Scene ajouteSecene = new Scene(ajoute);
            Stage window = new Stage();
            window.setScene(ajouteSecene);
            window.setTitle("Siyahi Bank | Ajouter une reponse à un credit");
            window.show();
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }
    @FXML
    void ModifierMLV(ActionEvent event) {
        ReponseCredit selectedReponseCredit = ListReponseCreditLV.getSelectionModel().getSelectedItem();

        if (selectedReponseCredit == null) {
            showAlert(Alert.AlertType.ERROR, "Erreur", "Aucune reponse n'est sélectionné", "Veuillez sélectionner une reponse à modifier.");
            return;
        }

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ModifierReponseCredit.fxml"));
            Parent edit = loader.load();

            ModifierReponseCredit ModifierReponseCredit = loader.getController();
            ModifierReponseCredit.initData(selectedReponseCredit);

            Scene editScene = new Scene(edit);
            Stage window = new Stage();
            window.setScene(editScene);
            window.setTitle("Siyahi Bank | Modifier une reponse d'un credit");
            window.show();
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }

    @FXML
    void SupprimerRC(ActionEvent event) {
        ReponseCredit selectedReponseCredit = ListReponseCreditLV.getSelectionModel().getSelectedItem();

        if (selectedReponseCredit == null) {
            showAlert(Alert.AlertType.ERROR, "Erreur", "Aucune reponse n'est sélectionné", "Veuillez sélectionner une reponse à modifier.");
            return;
        }

        ReponseCreditService rcs = new ReponseCreditService();
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation");
        alert.setHeaderText("Voulez-vous vraiment supprimer la reponse du credit suivante ?");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            rcs.Delete(ListReponseCreditLV.getSelectionModel().getSelectedItem());
            ListReponseCreditLV.getItems().remove(ListReponseCreditLV.getSelectionModel().getSelectedItem());
            showSuccessMessage("Credit supprimé avec succès!");
        } else {
            alert.close();
        }
    }

    @FXML
    void VoirCreditsLV(ActionEvent event) {
        try {
            Parent ajouterUserParent = FXMLLoader.load(getClass().getResource("/ListCredit.fxml"));
            Scene ajouterUserScene = new Scene(ajouterUserParent);
            Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
            window.setScene(ajouterUserScene);
            window.setTitle("Siyahi Bank | Gestion des Creditss");
            window.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private void showAlert(Alert.AlertType alertType, String title, String header, String content) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
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
    void navigateToCredits(ActionEvent event) {

    }

    @FXML
    void Logout(ActionEvent event){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation");
        alert.setHeaderText("Voulez-vous vraiment déconnecter?");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            Stage window_toClose = (Stage) menuItem.getParentPopup().getOwnerWindow();
            window_toClose.close();
            Parent users_section = null;
            try {
                users_section = FXMLLoader.load(getClass().getResource("/UserAuth.fxml"));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            Scene users_sectionSecene = new Scene(users_section);
            Stage window = new Stage();
            window.setScene(users_sectionSecene);
            window.setHeight(400); window.setMaxHeight(400); window.setMinHeight(400);
            window.setWidth(600); window.setMaxWidth(600); window.setMinWidth(600);
            window.setTitle("Siyahi Bank | Connexion");
            window.show();
        } else {
            alert.close();
        }
    }

    @FXML
    void Profile(ActionEvent event) {
        Parent parent = null;
        try {
            profileCheck = 1;
            if(connectedUser.getRoles().equals("Client") || connectedUser.getRoles().equals("Employé(e)"))
                parent = FXMLLoader.load(getClass().getResource("/ProfileUser.fxml"));
            else
                parent = FXMLLoader.load(getClass().getResource("/ProfileAdmin.fxml"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Scene scene = new Scene(parent);
        Stage window = (Stage) menuItem.getParentPopup().getOwnerWindow();
        window.setScene(scene);
        window.setTitle("Siyahi Bank | Profil d'utitlisateur");
        window.show();
    }

    @FXML
    void navigateToTransactions(ActionEvent event) {
        try {
            Parent ajouterUserParent = FXMLLoader.load(getClass().getResource("/Transactions.fxml"));
            Scene ajouterUserScene = new Scene(ajouterUserParent);
            Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
            window.setScene(ajouterUserScene);
            window.setTitle("Siyahi Bank | Gestion des Transactions");
            window.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void navigateToHomePage(ActionEvent event) {
        try {
            String pathTo = "";
            String titleTo = "";
            if(connectedUser.getRoles().equals("Client") || connectedUser.getRoles().equals("Employé(e)")) {
                pathTo = "/UserHomePage.fxml";
                titleTo = "Siyahi Bank | HomePage";
            } else{
                pathTo = "/AdminHomePage.fxml";
                titleTo = "Siyahi Bank | Dashboard";
            }
            Parent ajouterUserParent = FXMLLoader.load(getClass().getResource(pathTo));
            Scene ajouterUserScene = new Scene(ajouterUserParent);
            Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
            window.setScene(ajouterUserScene);
            window.setTitle(titleTo);
            window.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void navigateToConge(ActionEvent event) {
        try {
            Parent ajouterUserParent = FXMLLoader.load(getClass().getResource("/ListConge.fxml"));
            Scene ajouterUserScene = new Scene(ajouterUserParent);
            Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
            window.setScene(ajouterUserScene);
            window.setTitle("Siyahi Bank | Gestion des Conges");
            window.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
