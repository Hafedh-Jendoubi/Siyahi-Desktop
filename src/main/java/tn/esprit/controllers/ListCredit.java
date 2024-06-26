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
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import tn.esprit.models.Conge;
import tn.esprit.models.Credit;
import tn.esprit.services.CreditService;
import tn.esprit.services.ReponseCreditService;


import java.io.IOException;
import java.util.Optional;

import static tn.esprit.controllers.ProfileController.profileCheck;
import static tn.esprit.services.UserService.connectedUser;

public class ListCredit {
    @FXML
    private Button traiter;

    @FXML
    private Rectangle reclamPicture;

    @FXML
    private Button update;

    @FXML
    private Button add;

    @FXML
    private Circle circle;

    @FXML
    private Button delete;

    @FXML
    private ListView<Credit> ListCreditLV;

    @FXML
    private Button congBut;

    private CreditService CreditService = new CreditService();

    @FXML
    private MenuItem menuItem;


    @FXML
    public void initialize() {
        if (connectedUser.getRoles().equals("Employé(e)")) {
            congBut.setOpacity(1);
        } else if (connectedUser.getRoles().equals("Client")) {
            congBut.setOpacity(0);
        }

        if (connectedUser.getRoles().equals("Client")) {
            add.setOpacity(1);
            delete.setOpacity(1);
            update.setOpacity(1);
            traiter.setOpacity(0);

            ObservableList<Credit> credits = FXCollections.observableArrayList(CreditService.getCredits(connectedUser.getId()));
            ListCreditLV.setItems(credits);
        } else {
            add.setOpacity(0);
            delete.setOpacity(0);
            update.setOpacity(0);
            traiter.setOpacity(1);

            ObservableList<Credit> credits = FXCollections.observableArrayList(CreditService.getAll());
            ListCreditLV.setItems(credits);
        }

        ListCreditLV.setCellFactory(param -> new ListCell<Credit>() {
            @Override
            protected void updateItem(Credit item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                    setGraphic(null);
                } else {
                    String connectedUserEmail = connectedUser.getEmail();
                    String userEmail = CreditService.getEmailById(item.getUser_id());

                    // Afficher l'e-mail de l'utilisateur
                    Label userEmailLabel = new Label("User Email: " + userEmail);
                    userEmailLabel.setStyle("-fx-font-weight: bold; -fx-text-fill: #387296; -fx-font-size: 14;");

                    Label typeLabel = new Label("Type: " + item.getType_credit_nom());
                    typeLabel.setStyle("-fx-font-weight: bold; -fx-text-fill: #387296; -fx-font-size: 14;"); // Bleu clair

                    Label soldeLabel = new Label("Solde demande: " + item.getSolde_demande() + " TND");
                    soldeLabel.setStyle("-fx-font-weight: bold; -fx-text-fill: #387296; -fx-font-size: 14;"); // Bleu clair

                    Label datePaiementLabel = new Label("Date de paiement: " + item.getDate_debut_paiement() +
                            " sur " + item.getNbr_mois_paiement() + " mois");
                    datePaiementLabel.setStyle("-fx-font-weight: bold; -fx-text-fill: #387296; -fx-font-size: 14;"); // Bleu clair

                    Label descriptionLabel = new Label("Description: " + item.getDescription());
                    descriptionLabel.setStyle("-fx-font-weight: bold; -fx-text-fill: #387296; -fx-font-size: 14;"); // Bleu clair

                    // Créer un VBox pour contenir les éléments avec un padding et un espacement spécifiques
                    VBox rootVBox = new VBox(userEmailLabel, typeLabel, soldeLabel, datePaiementLabel, descriptionLabel);
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
    }

    @FXML
    void AjouterC(ActionEvent event) {
        try {
            Parent ajout = FXMLLoader.load(getClass().getResource("/AjouterCredit.fxml"));
            Scene ajoutSecene = new Scene(ajout);
            Stage window = new Stage();
            window.setScene(ajoutSecene);
            window.setHeight(750); window.setMaxHeight(750); window.setMinHeight(750);
            window.setWidth(606); window.setMaxWidth(600); window.setMinWidth(600);
            window.setTitle("Siyahi Bank | Ajouter un credit");
            window.show();
        } catch (IOException e) {
            System.err.println(e.getMessage());
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
    void navigateToAccount(ActionEvent event) {
        Parent parent = null;
        try {
            parent = FXMLLoader.load(getClass().getResource("/tn/esprit/siyahidesktop/ShowAccountDetailsFront.fxml"));
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
            ajouterReponseCredit.setPreviousStage((Stage) ((Node) event.getSource()).getScene().getWindow()); // Pass reference to previous stage
            ajouterReponseCredit.initData(selectedCredit.getId());
            ajouterReponseCredit.ReferenceCredit.getSelectionModel().select(selectedCredit);

            Scene ajoutReponseScene = new Scene(ajoutReponse);
            Stage window = new Stage();
            window.setScene(ajoutReponseScene);
            window.setHeight(600);
            window.setMaxHeight(600);
            window.setMinHeight(600);
            window.setWidth(606);
            window.setMaxWidth(600);
            window.setMinWidth(600);
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
            Stage window = new Stage();
            window.setScene(editScene);
            window.setHeight(750); window.setMaxHeight(750); window.setMinHeight(750);
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