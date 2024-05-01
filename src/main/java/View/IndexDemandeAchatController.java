package View;

import Entity.Demande_achat;
import Service.Demande_achatService;
import Service.IService;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.scene.control.Alert;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import javafx.scene.control.Alert;
import java.io.IOException;
import java.io.IOException;
import org.controlsfx.control.Notifications;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.image.Image;
import javafx.scene.image.Image;

public class IndexDemandeAchatController {

    @FXML
    private TableColumn<Demande_achat, Integer> idColumn;

    @FXML
    private TableColumn<Demande_achat, Integer> userIdColumn;

    @FXML
    private TableColumn<Demande_achat, Integer> achatIdColumn;

    @FXML
    private TableColumn<Demande_achat, String> nomColumn;

    @FXML
    private TableColumn<Demande_achat, String> prenomColumn;

    @FXML
    private TableColumn<Demande_achat, String> dateDemandeColumn;

    @FXML
    private TableColumn<Demande_achat, String> numTelColumn;

    @FXML
    private TableColumn<Demande_achat, String> typePaiementColumn;

    @FXML
    private TableColumn<Demande_achat, String> cinColumn;

    @FXML
    private TableColumn<Demande_achat, String> adresseColumn;

    @FXML
    private TableColumn<Demande_achat, String> etatDemandeColumn;

    @FXML
    private Label labelId;

    @FXML
    private Label labelUserId;

    @FXML
    private Label labelAchatId;

    @FXML
    private Label labelNom;

    @FXML
    private Label labelPrenom;

    @FXML
    private Label labelDateDemande;

    @FXML
    private Label labelNumTel;

    @FXML
    private Label labelTypePaiement;

    @FXML
    private Label labelCIN;

    @FXML
    private Label labelAdresse;

    @FXML
    private Label labelEtatDemande;

    @FXML
    private TableView<Demande_achat> DemandeAchatTable;

    private IService<Demande_achat> demandeAchatService = new Demande_achatService();
    private ObservableList<Demande_achat> demandeAchatList = FXCollections.observableArrayList();

    @FXML
    private void initialize() {
        idColumn.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getId()).asObject());
        userIdColumn.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getUser_id()).asObject());
        achatIdColumn.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getAchat_id()).asObject());
        nomColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getNom()));
        prenomColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getPrenom()));
        dateDemandeColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getDate_demande().toString()));
        numTelColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getNum_tel()));
        typePaiementColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getType_paiement()));
        cinColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getCin().toString()));
        adresseColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getAdresse()));
        etatDemandeColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getEtatdemande()));

        showDemandeAchatDetails(null);

        try {
            demandeAchatList.addAll(demandeAchatService.readAll());
            DemandeAchatTable.setItems(demandeAchatList);
        } catch (Exception e) {
            e.printStackTrace();
            showAlertDialog(Alert.AlertType.ERROR, "Error", "Failed to open new RendezVous window", e.getMessage());
        }

        DemandeAchatTable.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> showDemandeAchatDetails(newValue));
    }

    private void showDemandeAchatDetails(Demande_achat demandeAchat) {
        if (demandeAchat != null) {
            labelId.setText(String.valueOf(demandeAchat.getId()));
            labelUserId.setText(String.valueOf(demandeAchat.getUser_id()));
            labelAchatId.setText(String.valueOf(demandeAchat.getAchat_id()));
            labelNom.setText(demandeAchat.getNom());
            labelPrenom.setText(demandeAchat.getPrenom());
            labelDateDemande.setText(demandeAchat.getDate_demande().toString());
            labelNumTel.setText(demandeAchat.getNum_tel());
            labelTypePaiement.setText(demandeAchat.getType_paiement());
            labelCIN.setText(demandeAchat.getCin().toString());
            labelAdresse.setText(demandeAchat.getAdresse());
            labelEtatDemande.setText(demandeAchat.getEtatdemande());
        } else {
            labelId.setText("");
            labelUserId.setText("");
            labelAchatId.setText("");
            labelNom.setText("");
            labelPrenom.setText("");
            labelDateDemande.setText("");
            labelNumTel.setText("");
            labelTypePaiement.setText("");
            labelCIN.setText("");
            labelAdresse.setText("");
            labelEtatDemande.setText("");
        }
    }

    @FXML
    public void updateTableView() {
        try {
            demandeAchatList.clear();
            demandeAchatList.addAll(demandeAchatService.readAll());
        } catch (Exception e) {
            e.printStackTrace();
            showAlertDialog(Alert.AlertType.ERROR, "Database Error", "Failed to fetch Demande Achat", e.getMessage());
        }
    }

    private void showAlertDialog(Alert.AlertType type, String title, String header, String content) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }

    @FXML
    private void createDemandeAchat(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/add-demandeAchat.fxml"));
            Parent root = loader.load();

            Scene scene = new Scene(root);

            Stage stage = new Stage();
            stage.setTitle("Ajouter une demande d'achat");
            stage.setScene(scene);

            AddDemandeAchatController controller = loader.getController();
           controller.setIndexDemandeAchatController(this);

            stage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
        showNotification("Opération réussie", "La demande d'achat a été ajoutée avec succès.");


    }

    @FXML
    public void refreshTableView(ActionEvent actionEvent) {
        updateTableView();
    }

    @FXML
    private void handleDeleteDemandeAchat(ActionEvent event) {
        Demande_achat selectedDemandeAchat = DemandeAchatTable.getSelectionModel().getSelectedItem();
        if (selectedDemandeAchat != null) {
            try {
                demandeAchatService.delete(selectedDemandeAchat.getId());
                updateTableView();
            } catch (Exception e) {
                e.printStackTrace();
                showAlertDialog(Alert.AlertType.ERROR, "Database Error", "Failed to delete Demande Achat", e.getMessage());
            }
        } else {
            showAlertDialog(Alert.AlertType.WARNING, "No Selection", "No Demande Achat Selected!", "Please select a Demande Achat in the table!");
        }
        showNotification("Opération réussie", "La demande d'achat a été supprimé avec succées .");

    }

    @FXML
    private void updateDemandeAchat(ActionEvent event) {
        Demande_achat selectedDemandeAchat = DemandeAchatTable.getSelectionModel().getSelectedItem();
        if (selectedDemandeAchat != null) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/update-demandeAchat.fxml"));
                Parent root = loader.load();

                Scene scene = new Scene(root);

                Stage stage = new Stage();
                stage.setTitle("Modifier une demande d'achat");
                stage.setScene(scene);

                UpdateDemandeAchatController controller = loader.getController();
                controller.setIndexDemandeAchatController(this);
                controller.setDemandeAchatToUpdate(selectedDemandeAchat);

                stage.showAndWait();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            showAlertDialog(Alert.AlertType.WARNING, "No Selection", "No Demande Achat Selected!", "Please select a Demande Achat in the table!");
        }
        showNotification("Opération réussie", "La demande d'achat a été modifiée avec succès.");

    }
    @FXML
    private void generatePDF() {
        Demande_achat selectedDemandeAchat = DemandeAchatTable.getSelectionModel().getSelectedItem();

        if (selectedDemandeAchat != null) {
            try {
                // Création du document PDF
                PDDocument document = new PDDocument();
                PDPage page = new PDPage();
                document.addPage(page);

                try (PDPageContentStream contentStream = new PDPageContentStream(document, page)) {
                    // Définir les dimensions de la page
                    float pageWidth = page.getMediaBox().getWidth();
                    float pageHeight = page.getMediaBox().getHeight();

                    // Couleur du titre
                    contentStream.setNonStrokingColor(255, 0, 0); // Rouge vif

                    // Titre "Siyahi Banque"
                    String title = "Siyahi Banque";
                    float titleFontSize = 24;
                    float titleWidth = PDType1Font.HELVETICA_BOLD.getStringWidth(title) / 1000 * titleFontSize;
                    float titleX = (pageWidth - titleWidth) / 2;
                    float titleY = pageHeight - 50;

                    // Sous-titre "La demande avec ID: [id]"
                    String subtitle = "La demande avec ID: " + selectedDemandeAchat.getId();
                    float subtitleFontSize = 16;
                    float subtitleWidth = PDType1Font.HELVETICA_BOLD.getStringWidth(subtitle) / 1000 * subtitleFontSize;
                    float subtitleX = (pageWidth - subtitleWidth) / 2;
                    float subtitleY = titleY - 30;

                    // Espacement supplémentaire entre le titre et le sous-titre
                    float extraSpacing1 = 20;

                    // Écrire le titre
                    contentStream.beginText();
                    contentStream.setFont(PDType1Font.HELVETICA_BOLD, titleFontSize);
                    contentStream.newLineAtOffset(titleX, titleY);
                    contentStream.showText(title);
                    contentStream.endText();

                    // Couleur du sous-titre
                    contentStream.setNonStrokingColor(0, 0, 255); // Bleu vif

                    // Écrire le sous-titre
                    contentStream.beginText();
                    contentStream.setFont(PDType1Font.HELVETICA_BOLD, subtitleFontSize);
                    contentStream.newLineAtOffset(subtitleX, subtitleY - extraSpacing1);
                    contentStream.showText(subtitle);
                    contentStream.endText();

                    // Espacement supplémentaire entre le sous-titre et le tableau
                    float extraSpacing2 = 20;

                    // Définir les dimensions du tableau
                    float tableWidth = 500;
                    float tableHeight = 11 * 20; // 11 lignes avec une hauteur de 20
                    float tableX = (pageWidth - tableWidth) / 2;
                    float tableY = subtitleY - extraSpacing1 - extraSpacing2;

                    // Définir les dimensions de chaque cellule
                    float cellWidth = tableWidth / 2;
                    float cellHeight = 20;

                    // Données de la demande d'achat
                    String[][] data = {
                            {"ID", String.valueOf(selectedDemandeAchat.getId())},
                            {"User ID", String.valueOf(selectedDemandeAchat.getUser_id())},
                            {"Achat ID", String.valueOf(selectedDemandeAchat.getAchat_id())},
                            {"Nom", selectedDemandeAchat.getNom()},
                            {"Prénom", selectedDemandeAchat.getPrenom()},
                            {"Date Demande", selectedDemandeAchat.getDate_demande().toString()},
                            {"Num Tel", selectedDemandeAchat.getNum_tel()},
                            {"Type Paiement", selectedDemandeAchat.getType_paiement()},
                            {"CIN", selectedDemandeAchat.getCin().toString()},
                            {"Adresse", selectedDemandeAchat.getAdresse()},
                            {"État Demande", selectedDemandeAchat.getEtatdemande()}
                    };

                    // Définir la police et la taille de police pour les données
                    contentStream.setFont(PDType1Font.HELVETICA, 12);

                    // Écrire les données dans le tableau
                    for (int i = 0; i < data.length; i++) {
                        for (int j = 0; j < data[i].length; j++) {
                            float x = tableX + j * cellWidth;
                            float y = tableY - i * cellHeight - cellHeight;
                            contentStream.beginText();
                            contentStream.newLineAtOffset(x, y);
                            contentStream.showText(data[i][j]);
                            contentStream.endText();
                        }
                    }
                }

                // Enregistrement du document PDF
                String filename = "demande_achat_" + selectedDemandeAchat.getId() + ".pdf";
                document.save(filename);
                document.close();

                // Affichage d'une confirmation à l'utilisateur
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("PDF Généré");
                alert.setHeaderText(null);
                alert.setContentText("Le PDF a été généré avec succès !");
                alert.showAndWait();
                // Dans la méthode generatePDF après la génération réussie du PDF

            } catch (IOException e) {
                e.printStackTrace();
                // Affichage d'une erreur à l'utilisateur
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Erreur");
                alert.setHeaderText(null);
                alert.setContentText("Une erreur est survenue lors de la génération du PDF : " + e.getMessage());
                alert.showAndWait();
            }
        } else {
            // Si aucune demande d'achat n'est sélectionnée, afficher un message d'erreur à l'utilisateur
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Aucune sélection");
            alert.setHeaderText(null);
            alert.setContentText("Veuillez sélectionner une demande d'achat dans le tableau !");
            alert.showAndWait();
        }
    }
    @FXML

    private void showNotification(String title, String message) {
        Notifications.create()
                .title(title)
                .text(message)
                .darkStyle() // Vous pouvez personnaliser le style ici
                .show();
    }
    @FXML
    private void handleVoirStatistiques(ActionEvent event) {
        // Collecte des données fictives (remplacez cela par vos propres données)
        int totalDemandes = 100;
        int demandesParMois = 20;
        int demandesParUtilisateur = 50;

        // Génération des statistiques
        String statistiques = "Statistiques :\n" +
                "Nombre total de demandes : " + totalDemandes + "\n" +
                "Demandes par mois en moyenne : " + demandesParMois + "\n" +
                "Demandes par utilisateur en moyenne : " + demandesParUtilisateur;

        // Affichage des statistiques dans une fenêtre d'alerte
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Statistiques");
        alert.setHeaderText(null);
        alert.setContentText(statistiques);
        alert.showAndWait();
    }

}

