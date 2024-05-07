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
import java.io.IOException;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.image.Image;
import javafx.scene.layout.VBox;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import tn.esprit.models.Conge;
import tn.esprit.models.Credit;
import tn.esprit.services.CongeService; // Assurez-vous d'avoir une classe CongeService pour récupérer les données


import java.io.IOException;
import java.util.Optional;

import static tn.esprit.controllers.ProfileController.profileCheck;
import static tn.esprit.services.UserService.connectedUser;

public class listCongeController {
    @FXML
    private Circle circle;

    @FXML
    private ListView<Conge> listViewConge;
    @FXML
    private Button btnModifier;

    @FXML
    private Button addBut;

    @FXML
    private Button checkBut;

    @FXML
    private Button delBut;

    @FXML
    private Button updBut;

    @FXML
    private Button calendrier;
    @FXML
    private Button btnSupprimer;

    @FXML
    private MenuItem menuItem;
    private CongeService congeService = new CongeService(); // Changez CongeService avec votre service réel
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

        if(connectedUser.getRoles().equals("Admin") || connectedUser.getRoles().equals("Employé(e)")){
            addBut.setOpacity(1); delBut.setOpacity(1); updBut.setOpacity(1); checkBut.setOpacity(1); calendrier.setOpacity(0); btnModifier.setOpacity(0);
            ObservableList<Conge> conges = FXCollections.observableArrayList(congeService.getConges(connectedUser.getId())); //Only the logged in user
            listViewConge.setItems(conges);
        }else{
            addBut.setOpacity(0); delBut.setOpacity(0); updBut.setOpacity(0); checkBut.setOpacity(0); calendrier.setOpacity(1); btnModifier.setOpacity(1);
            ObservableList<Conge> conges = FXCollections.observableArrayList(congeService.getAll());
            listViewConge.setItems(conges);
        }

        listViewConge.setCellFactory(param -> new ListCell<Conge>() {
            @Override
            protected void updateItem(Conge item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                    setGraphic(null);
                } else {
                    // Créer les éléments d'interface utilisateur pour afficher les détails de la réponse au crédit
                    Label descriptionLabel = new Label("Description: " + item.getDescription());
                    descriptionLabel.setStyle("-fx-font-weight: bold; -fx-text-fill: #387296; -fx-font-size: 14;"); // Bleu clair

                    Label dateDebutLabel = new Label("Date debut de congé: " + item.getDate_Debut() );
                    dateDebutLabel.setStyle("-fx-font-weight: bold; -fx-text-fill: #387296; -fx-font-size: 14;"); // Bleu clair

                    Label DateFinLabel = new Label("Date de fin de congé: " + item.getDate_Fin() );
                    DateFinLabel.setStyle("-fx-font-weight: bold; -fx-text-fill: #387296; -fx-font-size: 14;"); // Bleu clair

                    Label TypeLabel = new Label("Type de congé: " + item.getType_conge() );
                    TypeLabel.setStyle("-fx-font-weight: bold; -fx-text-fill: #387296; -fx-font-size: 14;"); // Bleu clair

                    Label statusLabel = new Label("Status+: " + item.isStatus());
                    statusLabel.setStyle("-fx-font-weight: bold; -fx-text-fill: #387296; -fx-font-size: 14;"); // Bleu clair

                    // Créer un VBox pour contenir les éléments avec un padding et un espacement spécifiques
                    VBox rootVBox = new VBox(descriptionLabel, dateDebutLabel, DateFinLabel, TypeLabel, statusLabel);

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
    void modifierConge(ActionEvent event) {
        Conge selectedConge = listViewConge.getSelectionModel().getSelectedItem();

        if (selectedConge == null) {
            // Aucun congé sélectionné, afficher un message d'erreur
            showAlert(Alert.AlertType.ERROR, "Erreur", "Aucun congé sélectionné", "Veuillez sélectionner un congé à modifier.");
            return;
        }

        try {
            // Charger la vue de modification du congé
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/EditConge.fxml"));
            Parent edit = loader.load();

            // Passer le congé sélectionné au contrôleur de la vue de modification
            editCongeController editCongeController = loader.getController();
            editCongeController.initData(selectedConge);

            // Afficher la fenêtre de modification
            Scene editScene = new Scene(edit);
            Stage window = new Stage();
            window.setScene(editScene);
            window.setHeight(515); window.setMaxHeight(515); window.setMinHeight(515);
            window.setWidth(530); window.setMaxWidth(530); window.setMinWidth(530);
            window.setTitle("Siyahi Bank | Modifier un congé");
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
    @FXML
    void supprimerConge(ActionEvent event) {
        CongeService cs = new CongeService();
        Conge selectedConge = listViewConge.getSelectionModel().getSelectedItem();

        if (selectedConge == null) {
            // Aucun congé sélectionné, afficher un message d'erreur
            showAlert(Alert.AlertType.ERROR, "Erreur", "Aucun congé sélectionné", "Veuillez sélectionner un congé à supprimer.");
            return;
        }
        if (selectedConge.isStatus()) {
            showAlert(Alert.AlertType.WARNING, "Attention", "Impossible de supprimer le congé", "Ce congé a déjà été traité et ne peut pas être supprimé.");
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
            Parent ajout = FXMLLoader.load(getClass().getResource("/AddConge.fxml"));
            Scene ajoutSecene = new Scene(ajout);
            Stage window = new Stage();
            window.setScene(ajoutSecene);
            window.setHeight(515); window.setMaxHeight(515); window.setMinHeight(515);
            window.setWidth(600); window.setMaxWidth(600); window.setMinWidth(600);
            window.setTitle("Siyahi Bank | Ajouter un congé");
            window.show();
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }
    @FXML
    void calendrier(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/calendrier.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {

        }
    }
    @FXML
    void edit(ActionEvent event) {
        Conge selectedConge = listViewConge.getSelectionModel().getSelectedItem();

        if (selectedConge == null) {
            // Aucun congé sélectionné, afficher un message d'erreur
            showAlert(Alert.AlertType.ERROR, "Erreur", "Aucun congé sélectionné", "Veuillez sélectionner un congé à modifier.");
            return;
        }
        if (selectedConge.isStatus()) {
            showAlert(Alert.AlertType.WARNING, "Attention", "Impossible de modifier le congé", "Ce congé a déjà été traité et ne peut pas être modifié.");
            return;
        }

        try {
            // Charger la vue de modification du congé
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/modfierConge.fxml"));
            Parent edit = loader.load();

            // Passer le congé sélectionné au contrôleur de la vue de modification
            modifierCongeController modifierCongeController = loader.getController();
            modifierCongeController.initData1(selectedConge);

            // Afficher la fenêtre de modification
            Scene editScene = new Scene(edit);
            Stage window = new Stage();
            window.setScene(editScene);
            window.setHeight(515); window.setMaxHeight(515); window.setMinHeight(515);
            window.setWidth(600); window.setMaxWidth(600); window.setMinWidth(600);
            window.setTitle("Siyahi Bank | Reponse à un congé");
            window.show();
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }

    @FXML
    void navigateToCredits(ActionEvent event) {
        try {
            String pathTo = "";
            String titleTo = "";
            if(connectedUser.getRoles().equals("Client") || connectedUser.getRoles().equals("Employé(e)")) {
                pathTo = "/ListCredit.fxml";
                titleTo = "Siyahi Bank | Gestion des Credits";
            } else{
                pathTo = "/ListTypeCredit.fxml";
                titleTo = "Siyahi Bank | Gestion des Types de Credits";
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
    void voirListLimitation(ActionEvent event) {
        try {
            Parent ajout = FXMLLoader.load(getClass().getResource("/list.fxml"));
            Scene ajoutSecene = new Scene(ajout);
            Stage window = new Stage();
            window.setScene(ajoutSecene);
            window.setHeight(515); window.setMaxHeight(515); window.setMinHeight(515);
            window.setWidth(600); window.setMaxWidth(600); window.setMinWidth(600);
            window.setTitle("Siyahi Bank | Limitation Congés");
            window.show();
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
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
}