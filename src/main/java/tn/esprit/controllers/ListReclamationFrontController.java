package tn.esprit.controllers;






import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.scene.control.*;
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
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;
import tn.esprit.models.User;
import tn.esprit.services.ReclamationService;
import tn.esprit.models.Reclamation;

import static tn.esprit.services.UserService.connectedUser;

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
    private TextField rechRecl;

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
        // Your existing initialization code...
        updateTable();

        // Set up row selection event handler
        TBReclamation.setOnMouseClicked(event -> {
            if (event.getClickCount() == 1) { // Check for single click
                Reclamation selectedReclamation = TBReclamation.getSelectionModel().getSelectedItem();
                if (selectedReclamation != null) {
                    try {
                        showDetails(selectedReclamation);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }


    public void updateTable() {
        List<Reclamation> listReclamations = new ArrayList<>();
        if(connectedUser.getRoles().equals("Client"))
            listReclamations = ReclamationService.getInstance().getReclamations(connectedUser.getId());
        else
            listReclamations = ReclamationService.getInstance().getAll();
        tbid.setCellValueFactory(new PropertyValueFactory<>("id"));
        tbobject.setCellValueFactory(new PropertyValueFactory<>("object"));
        tbdescription.setCellValueFactory(new PropertyValueFactory<>("description"));
        tbdate_creation.setCellValueFactory(new PropertyValueFactory<>("date_creation"));
        tbauteur.setCellValueFactory(new PropertyValueFactory<>("auteur"));
        tbemail.setCellValueFactory(new PropertyValueFactory<>("email"));
        tbstatus.setCellValueFactory(new PropertyValueFactory<>("status"));
        TBReclamation.setItems(FXCollections.observableArrayList(listReclamations));

        //Search Filter
        FilteredList<Reclamation> filteredData = new FilteredList<>(FXCollections.observableArrayList(listReclamations), b -> true);
        rechRecl.setText("");
        rechRecl.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(Reclamation -> {
                if(newValue == null || newValue.isEmpty()){
                    return true;
                }

                String lowerCaseFilter = newValue.toLowerCase();

                if(Reclamation.getDescription().toLowerCase().indexOf(lowerCaseFilter) != -1){
                    return true;
                } else if (Reclamation.getAuteur().toLowerCase().indexOf(lowerCaseFilter) != -1){
                    return true;
                }else if(String.valueOf(Reclamation.getId()).toLowerCase().indexOf(lowerCaseFilter) != -1){
                    return true;
                }else
                    return false;
            });
        });
        SortedList<Reclamation> sortedData = new SortedList<>(FXCollections.observableArrayList(listReclamations));
        sortedData.comparatorProperty().bind(TBReclamation.comparatorProperty());
        TBReclamation.setItems(sortedData);
    }

    @FXML
    void modifierreclamation(ActionEvent event) {
        Reclamation selectedReclamation = TBReclamation.getSelectionModel().getSelectedItem();



        try {
            // Chargez la vue de modification avec les détails de la réclamation sélectionnée
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/EditReclamation.fxml"));
            Parent edit = loader.load();

            // Passez la réclamation sélectionnée au contrôleur de la vue de modification
            EditReclamationController controller = loader.getController();
            controller.initData(selectedReclamation);

            // Affichez la fenêtre de modification
            Stage window = new Stage();
            window.setScene(new Scene(edit));
            window.setTitle("Modifier Réclamation");
            window.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @FXML
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

    @FXML
    void showDetails(Reclamation selectedReclamation) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/UpdateReclamation.fxml"));
        Parent root = loader.load();
        UpdateReclamationController controller = loader.getController();
        controller.initData(selectedReclamation);

        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.setTitle("Reclamation Details");
        stage.show();
    }

}
