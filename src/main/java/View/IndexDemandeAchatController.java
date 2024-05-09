package View;

import Entity.Achat;
import Entity.Demande_achat;
import Service.AchatService;
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
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.scene.control.Alert;
/*import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;*/
import javafx.scene.control.Alert;

import java.io.File;
import java.io.IOException;
import java.io.IOException;
import java.util.*;
/*import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;*/


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

import static tn.esprit.controllers.ProfileController.profileCheck;
import static tn.esprit.services.UserService.connectedUser;


public class IndexDemandeAchatController extends IndexAchatController {
    @FXML
    public TableColumn<Achat, String> imageColumn;
    @FXML
    public TableColumn<Achat, String> DescripColumn;
    @FXML
    public TableColumn<Achat, String> typeColumn;
    @FXML
    private TableColumn<Achat, Integer>
            idColumn;

    @FXML
    private Label labelId;
    @FXML
    private ImageView imageview;

    @FXML
    private Label labelDesc;

    @FXML
    private Label labelType;

    @FXML
    private TableView<Achat> AchatTable;
    private IService<Achat> AchatIService = new AchatService();
    private ObservableList<Achat> AchatService = FXCollections.observableArrayList();

    @FXML
    private Circle circle;
    @FXML
    private ArrayList<String> donnees = new ArrayList<>();

    @FXML
    private TableColumn<Demande_achat, Integer> userIdColumn;

    @FXML
    private TableColumn<Demande_achat, Integer> achatIdColumn;

    @FXML
    private TableColumn<Demande_achat, String> nomColumn;

    @FXML
    private MenuItem menuItem;

    @FXML
    private TableColumn<Demande_achat, String> prenomColumn;

    @FXML
    private TableColumn<Demande_achat, String> dateDemandeColumn;

    @FXML
    private TableColumn<Demande_achat, String> numTelColumn;

    @FXML
    private TextField searchField;

    @FXML
    private TableColumn<Demande_achat, String> typePaiementColumn;

    @FXML
    private TableColumn<Demande_achat, String> cinColumn;

    @FXML
    private TableColumn<Demande_achat, String> adresseColumn;

    @FXML
    private TableColumn<Demande_achat, String> etatDemandeColumn;
    @FXML
    private ChoiceBox<String> triChoiceBox;
    @FXML
    private TableView<Demande_achat> DemandeAchatTable;
    private final Demande_achatService rec;

    @FXML
    private Rectangle reclamPicture;

    {
        rec = new Demande_achatService();
    }
    private IService<Demande_achat> demandeAchatService = new Demande_achatService();
    private ObservableList<Demande_achat> demandeAchatList = FXCollections.observableArrayList();

    @FXML
    private void initialize() {
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

        DescripColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getDescription()));
        typeColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getType()));
        imageColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getImage()));
        idColumn.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getId()).asObject());
        showAchatDetails(null);

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

        try {
            demandeAchatList.addAll(demandeAchatService.readAll());
            DemandeAchatTable.setItems(demandeAchatList);
        } catch (Exception e) {
            e.printStackTrace();
            showAlertDialog(Alert.AlertType.ERROR, "Error", "Failed to open new RendezVous window", e.getMessage());
        }

        try {
            AchatService.addAll(AchatIService.readAll());
            AchatTable.setItems(AchatService);
        } catch (Exception e) {
            e.printStackTrace();
            showAlertDialog(Alert.AlertType.ERROR, "Error", "Failed to open new RendezVous window", e.getMessage());
        }

        AchatTable.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> showAchatDetails(newValue));
    }

    private void showAchatDetails(Achat achat) {

    }

    @FXML
    private void createAchat(ActionEvent event) {
        try {
            // Charger le fichier FXML de l'interface d'ajout d'achat
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/add-achat.fxml"));
            Parent root = loader.load();

            // Créer une nouvelle scène avec la racine chargée
            Scene scene = new Scene(root);

            // Créer un nouveau stage pour la scène
            Stage stage = new Stage();
            stage.setTitle("Ajouter un achat");
            stage.setScene(scene);

            AddAchatController controller = loader.getController();

            controller.setIndexAchatController(this);

            // Afficher la fenêtre d'ajout d'achat
            stage.showAndWait(); // Utilisez show() si vous voulez afficher la fenêtre sans bloquer le thread principal
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleDeleteAchat(ActionEvent event) {
        Achat selectedAchat = AchatTable.getSelectionModel().getSelectedItem();
        if (selectedAchat != null) {
            try {
                AchatIService.delete(selectedAchat.getId());
                updateTableView();

            } catch (Exception e) {
                e.printStackTrace();
                showAlertDialog(Alert.AlertType.ERROR, "Database Error", "Failed to delete Achat", e.getMessage());
            }
        } else {
            showAlertDialog(Alert.AlertType.WARNING, "No Selection", "No Achat Selected!", "Please select an Achat in the table!");
        }
    }

    @FXML
    private void updateAchat(ActionEvent event) {
        Achat selectedAchat = AchatTable.getSelectionModel().getSelectedItem();
        if (selectedAchat != null) {
            try {
                // Charger le fichier FXML de l'interface de mise à jour d'achat
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/update-achat.fxml"));
                Parent root = loader.load();

                // Créer une nouvelle scène avec la racine chargée
                Scene scene = new Scene(root);

                // Créer un nouveau stage pour la scène
                Stage stage = new Stage();
                stage.setTitle("Modifier un achat");
                stage.setScene(scene);

                // Obtenir le contrôleur du formulaire de mise à jour
                UpdateAchatController controller = loader.getController();
                controller.setIndexAchatController(this);
                // Passer l'Achat sélectionné au contrôleur de mise à jour
                controller.setSelectedAchat(selectedAchat);

                // Afficher la fenêtre de mise à jour d'achat
                stage.showAndWait(); // Utilisez show() si vous voulez afficher la fenêtre sans bloquer le thread principal
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            showAlertDialog(Alert.AlertType.WARNING, "No Selection", "No Achat Selected!", "Please select an Achat in the table!");
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
    public void updateTableView() {
        try {
            demandeAchatList.clear();
            demandeAchatList.addAll(demandeAchatService.readAll());
        } catch (Exception e) {
            e.printStackTrace();
            showAlertDialog(Alert.AlertType.ERROR, "Database Error", "Failed to fetch Demande Achat", e.getMessage());
        }
    }

    @FXML
    void addAchat(ActionEvent event) {
        try {
            Parent ajouterUserParent = FXMLLoader.load(getClass().getResource("/add-achat.fxml"));
            Scene ajouterUserScene = new Scene(ajouterUserParent);
            Stage window = new Stage();
            window.setScene(ajouterUserScene);
            window.setTitle("Siyahi Bank | Gestion des Utilisateurs");
            window.show();
        } catch (IOException e) {
            e.printStackTrace();
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
    /*@FXML
    private void generatePDF() {
        Demande_achat selectedDemandeAchat = DemandeAchatTable.getSelectionModel().getSelectedItem();

        if (selectedDemandeAchat != null) {
            try {
                // Création du document PDF
                *//*PDDocument document = new PDDocument();
                PDPage page = new PDPage();
                document.addPage(page);
*//*
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
    }*/



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
    void navigateToAchat(ActionEvent event) {
        try {
            Parent ajouterUserParent = FXMLLoader.load(getClass().getResource("/index-demandeAchat.fxml"));
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
}






