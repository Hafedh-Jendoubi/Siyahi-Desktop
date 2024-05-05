package View;

import Entity.Demande_achat;
import Service.Demande_achatService;
import Service.IService;
import com.mysql.cj.exceptions.DeadlockTimeoutRollbackMarker;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.SortedList;
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
import java.util.*;
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;


import org.controlsfx.control.Notifications;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.image.Image;
import javafx.scene.image.Image;

import java.util.Comparator;
import javafx.scene.control.cell.PropertyValueFactory;
import java.sql.SQLException;
import java.io.IOException;

import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;


public class IndexDemandeAchatController {
    @FXML
    private ChoiceBox<String> colonneChoiceBox;
    @FXML
    private BarChart<String, Integer> barChart;
    @FXML
    private ArrayList<String> donnees = new ArrayList<>();
    @FXML
    private CategoryAxis xAxis;

    @FXML
    private NumberAxis yAxis;

    @FXML
    private ChoiceBox<String> ordreChoiceBox;
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
    private TextField searchField;

    @FXML
    private TableView <Demande_achat> tableView;

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
    private ChoiceBox<String> triChoiceBox;
    @FXML
    private TableView<Demande_achat> DemandeAchatTable;
    private final Demande_achatService rec;

    {
        rec = new Demande_achatService();
    }
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
        searchField.textProperty().addListener((observable, oldValue, newValue) -> searchDemandeAchat());

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
        showNotification("Opération réussie", "La demande d'achat a été supprimé avec succès.");
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
    private void searchDemandeAchat() {
        String keyword = searchField.getText().trim().toLowerCase();
        if (keyword.isEmpty()) {
            // Si le champ de recherche est vide, afficher toutes les demandes d'achat
            DemandeAchatTable.setItems(demandeAchatList);
        } else {
            // Filtrer les demandes d'achat en fonction du mot-clé de recherche
            ObservableList<Demande_achat> filteredList = FXCollections.observableArrayList();
            for (Demande_achat demandeAchat : demandeAchatList) {
                if (demandeAchat.getNom().toLowerCase().contains(keyword) ||
                        demandeAchat.getPrenom().toLowerCase().contains(keyword) ||
                        demandeAchat.getNum_tel().toLowerCase().contains(keyword) ||
                        demandeAchat.getType_paiement().toLowerCase().contains(keyword) ||
                        demandeAchat.getCin().toString().toLowerCase().contains(keyword) ||
                        demandeAchat.getAdresse().toLowerCase().contains(keyword) ||
                        demandeAchat.getEtatdemande().toLowerCase().contains(keyword)) {
                    filteredList.add(demandeAchat);
                }
            }
            DemandeAchatTable.setItems(filteredList);
        }
    }
    @FXML
    private void choisirCroissant(ActionEvent event) {
        if (!donnees.isEmpty()) {
            Collections.sort(donnees);
            afficherDonneesTriees("Tri Croissant", donnees);
        } else {
            afficherMessageErreur("Erreur", "Aucune donnée à trier.");
        }
    }

    // Méthode pour afficher les données triées dans une boîte de dialogue
    private void afficherDonneesTriees(String titre, ArrayList<String> donneesTriees) {
        StringBuilder message = new StringBuilder("Données triées (Croissant) :\n");
        for (String donnee : donneesTriees) {
            message.append(donnee).append("\n");
        }
        afficherMessageInfo(titre, message.toString());
    }

    // Méthode pour afficher une boîte de dialogue d'erreur
    private void afficherMessageErreur(String titre, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(titre);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    // Méthode pour afficher une boîte de dialogue d'information
    private void afficherMessageInfo(String titre, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(titre);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}






