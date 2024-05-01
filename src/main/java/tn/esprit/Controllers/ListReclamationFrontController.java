package tn.esprit.Controllers;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.List;
import java.util.ResourceBundle;

import org.controlsfx.control.Notifications;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;
import tn.esprit.services.ReclamationService;
import tn.esprit.models.Reclamation;

public class ListReclamationFrontController implements Initializable {

    @FXML
    private TableColumn<Reclamation, Integer> tbid;

    @FXML
    private TableColumn<Reclamation, String> tbobject;

    @FXML
    private TableColumn<Reclamation, String> tbdescription;

    @FXML
    private TableColumn<Reclamation, String> tbdate_creation;

    @FXML
    private TableColumn<Reclamation, String> tbauteur;

    @FXML
    private TableColumn<Reclamation, String> tbemail;

    @FXML
    private TableColumn<Reclamation, String> tbstatus;

    @FXML
    private TextField tfIdCommande;

    @FXML
    private TextField TFSujet;

    @FXML
    private TextArea TADescription;

    @FXML
    private DatePicker PickerDate;

    @FXML
    private ComboBox<String> CBObjet;

    @FXML
    private TableView<Reclamation> TBReclamation;

    @FXML
    private Button btnModifier;
    @FXML
    private TextField TFAuteur;
    @FXML
    private TextField TFEmail;
    @FXML
    private TextField CBStatus;

    ReclamationService sr = new ReclamationService();

    ObservableList<Reclamation> reclamations = FXCollections.observableArrayList();
    final ObservableList<Reclamation> data = FXCollections.observableArrayList();
    public static Reclamation reclamationActuel;

    Reclamation reclamation;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        PickerDate.setDayCellFactory(picker -> new DateCell() {
            public void updateItem(LocalDate date, boolean empty) {
                super.updateItem(date, empty);
                LocalDate today = LocalDate.now();

                setDisable(empty || date.compareTo(today) < 0);
            }
        });

        CBObjet.setItems(FXCollections.observableArrayList("Location", "Réparation", "Montage"));
        afficher();
    }

    @FXML
    void modifierReclamation(ActionEvent event) throws IOException {
        Image img = new Image("\\Ressources\\iconUpdate.jpg");
        Reclamation r = TBReclamation.getSelectionModel().getSelectedItem();

        int idCommande = Integer.parseInt(tfIdCommande.getText());
        String objet = CBObjet.getSelectionModel().getSelectedItem();
        String description = TADescription.getText();
        String dateCreation = PickerDate.getValue().toString();
        String auteur = TFAuteur.getText();
        String email = TFEmail.getText();

        ReclamationService src = new ReclamationService();
        src.update(new Reclamation(r.getId(), objet, description, dateCreation, auteur, email));

        Notifications notificationBuilder = Notifications.create()
                .title("Réclamation modifiée !")
                .text("Votre réclamation a bien été modifiée.")
                .graphic(new ImageView(img))
                .hideAfter(Duration.seconds(5))
                .position(Pos.TOP_RIGHT);
        notificationBuilder.showInformation();

        init();
    }

    public void updateTable() {
        List<Reclamation> listReclamations = ReclamationService.getInstance().getAll();
        tbid.setCellValueFactory(new PropertyValueFactory<>("id"));
        tbobject.setCellValueFactory(new PropertyValueFactory<>("objet"));
        tbdescription.setCellValueFactory(new PropertyValueFactory<>("description"));
        tbdate_creation.setCellValueFactory(new PropertyValueFactory<>("dateCreation"));
        tbauteur.setCellValueFactory(new PropertyValueFactory<>("auteur"));
        tbemail.setCellValueFactory(new PropertyValueFactory<>("email"));
        tbstatus.setCellValueFactory(new PropertyValueFactory<>("statut"));
        TBReclamation.setItems(reclamations);
    }

    public void init() {
        updateTable();
        reclamations.clear();
        afficher();
    }

    private void afficher() {
        List<Reclamation> listReclamations = ReclamationService.getInstance().getAll();

        if (!listReclamations.isEmpty()) {
            for (int i = 0; i < listReclamations.size(); i++) {
                reclamation = listReclamations.get(i);
                reclamations.add(reclamation);
            }

            tbid.setCellValueFactory(new PropertyValueFactory<>("id"));
            tbobject.setCellValueFactory(new PropertyValueFactory<>("objet"));
            tbdescription.setCellValueFactory(new PropertyValueFactory<>("description"));
            tbdate_creation.setCellValueFactory(new PropertyValueFactory<>("dateCreation"));
            tbauteur.setCellValueFactory(new PropertyValueFactory<>("auteur"));
            tbemail.setCellValueFactory(new PropertyValueFactory<>("email"));
            tbstatus.setCellValueFactory(new PropertyValueFactory<>("statut"));

            reclamationActuel = null;

            TBReclamation.setItems(reclamations);
        }
    }

    public void RetourHome(ActionEvent event) {
        try {
            // Charger le fichier FXML de votre interface Home
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Home.fxml"));
            Parent root = loader.load();

            // Créer une nouvelle scène avec la racine chargée depuis le fichier FXML
            Scene scene = new Scene(root);

            // Obtenir la fenêtre principale à partir de l'événement
            Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();

            // Définir la nouvelle scène sur la fenêtre principale
            stage.setScene(scene);
            stage.show(); // Afficher la nouvelle scène

        } catch (IOException e) {
            e.printStackTrace();
        }


    }
}