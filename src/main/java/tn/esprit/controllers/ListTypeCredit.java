package tn.esprit.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.VBox;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import tn.esprit.models.TypeCredit;
import tn.esprit.services.TypeCreditService;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;

import java.io.IOException;
import java.util.Optional;

import static tn.esprit.controllers.ProfileController.profileCheck;
import static tn.esprit.services.UserService.connectedUser;

public class ListTypeCredit {
    @FXML
    private Circle circle;

    @FXML
    private MenuItem menuItem;

    @FXML
    private ListView<TypeCredit> ListTypeCreditLV;

    @FXML
    private Rectangle reclamPicture;

    private final TypeCreditService typeCreditService = new TypeCreditService();

    @FXML
    public void initialize() {
        try {
            String imageName = connectedUser.getImage();
            String imagePath = "/uploads/user/" + imageName;
            String image1Path = "/Images/danger.png";
            Image image = new Image(getClass().getResource(imagePath).toExternalForm());
            Image image1 = new Image(getClass().getResource(image1Path).toExternalForm());
            circle.setFill(new ImagePattern(image));
            reclamPicture.setFill(new ImagePattern(image1));
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }

        refreshList();
    }

    private void refreshList() {
        ObservableList<TypeCredit> typeCredits = FXCollections.observableArrayList(typeCreditService.getAll());
        ListTypeCreditLV.setItems(typeCredits);

        ListTypeCreditLV.setCellFactory(param -> new ListCell<TypeCredit>() {
            @Override
            protected void updateItem(TypeCredit item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                    setGraphic(null);
                } else {
                    // Créer les éléments d'interface utilisateur pour afficher les détails du type de crédit
                    Label nomLabel = new Label("Nom: " + item.getNomTypeCredit());
                    nomLabel.setStyle("-fx-font-weight: bold; -fx-text-fill: #387296; -fx-font-size: 14;"); // Bleu clair

                    Label tauxLabel = new Label("Taux: " + item.getTauxCreditDirect());
                    tauxLabel.setStyle("-fx-font-weight: bold; -fx-text-fill: #387296; -fx-font-size: 14;"); // Bleu clair

                    // Créer un VBox pour contenir les éléments avec un padding et un espacement spécifiques
                    VBox rootVBox = new VBox(nomLabel, tauxLabel);
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
    void navigateToAchat(ActionEvent event) {
        try {
            Parent ajouterUserParent = FXMLLoader.load(getClass().getResource("/demandeAchat.fxml"));
            Scene ajouterUserScene = new Scene(ajouterUserParent);
            Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
            window.setScene(ajouterUserScene);
            window.setTitle("Siyahi Bank | Gestion des Conges");
            window.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void navigateToHamroun(ActionEvent event) {
        try {
            Parent ajouterUserParent = FXMLLoader.load(getClass().getResource("/tn/esprit/siyahidesktop/MainPage.fxml"));
            Scene ajouterUserScene = new Scene(ajouterUserParent);
            Stage window = new Stage();
            window.setScene(ajouterUserScene);
            window.setTitle("Siyahi Bank | Gestion des Comptes & Services");
            window.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void navigateToReclamations(ActionEvent event) {
        try {
            Parent ajouterUserParent = FXMLLoader.load(getClass().getResource("/Home.fxml"));
            Scene ajouterUserScene = new Scene(ajouterUserParent);
            Stage window = new Stage();
            window.setScene(ajouterUserScene);
            window.setTitle("Siyahi Bank | Gestion des Conges");
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

    @FXML
    void AjouterTypeCredit(ActionEvent event) {
        // Code pour aller à l'interface d'ajout d'un type de crédit
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AjouterTypeCredit.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.showAndWait();
            refreshList(); // Rafraîchir la liste après ajout
        } catch (IOException e) {
            showAlert(Alert.AlertType.ERROR, "Erreur", "Erreur lors de l'ouverture de l'interface d'ajout", e.getMessage());
        }
    }

    @FXML
    void ModifierTypeCredit(ActionEvent event) {
        // Code pour aller à l'interface de modification d'un type de crédit sélectionné dans la liste
        TypeCredit selectedTypeCredit = ListTypeCreditLV.getSelectionModel().getSelectedItem();
        if (selectedTypeCredit == null) {
            showAlert(Alert.AlertType.ERROR, "Erreur", "Aucun type de crédit sélectionné", "Veuillez sélectionner un type de crédit à modifier.");
            return;
        }

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ModifierTypeCredit.fxml"));
            Parent edit = loader.load();

            ModifierTypeCredit modifierTypeCredit = loader.getController();
            modifierTypeCredit.initData(selectedTypeCredit);

            Stage window = new Stage();
            Scene editScene = new Scene(edit);
            window.setScene(editScene);
            window.showAndWait();
            refreshList(); // Rafraîchir la liste après modification
        } catch (IOException e) {
            showAlert(Alert.AlertType.ERROR, "Erreur", "Erreur lors de l'ouverture de l'interface de modification", e.getMessage());
        }
    }

    @FXML
    void SupprimerTypeCredit(ActionEvent event) {
        // Code pour supprimer un type de crédit sélectionné dans la liste
        TypeCredit selectedTypeCredit = ListTypeCreditLV.getSelectionModel().getSelectedItem();
        if (selectedTypeCredit == null) {
            showAlert(Alert.AlertType.ERROR, "Erreur", "Aucun type de crédit sélectionné", "Veuillez sélectionner un type de crédit à supprimer.");
            return;
        }

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation de suppression");
        alert.setHeaderText("Voulez-vous vraiment supprimer ce type de crédit ?");
        alert.setContentText("Cette action est irréversible.");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            typeCreditService.Delete(selectedTypeCredit);
            ListTypeCreditLV.getItems().remove(selectedTypeCredit);
            showAlert(Alert.AlertType.INFORMATION, "Suppression réussie", null, "Le type de crédit a été supprimé avec succès.");
        }
    }

    private void showAlert(Alert.AlertType alertType, String title, String header, String content) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
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
    void navigateToUserSection(ActionEvent event) {
        try {
            Parent ajouterUserParent = FXMLLoader.load(getClass().getResource("/Users.fxml"));
            Scene ajouterUserScene = new Scene(ajouterUserParent);
            Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
            window.setScene(ajouterUserScene);
            window.setTitle("Siyahi Bank | Gestion des Utilisateurs");
            window.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void navigateToCredits(ActionEvent event){

    }
}
