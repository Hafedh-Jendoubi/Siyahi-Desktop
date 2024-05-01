package View;

import Entity.Achat;
import Service.AchatService;
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

import java.io.IOException;
import java.sql.SQLException;


public class IndexAchatController {


    @FXML
    private TableColumn<Achat, String> imageColumn;
    @FXML
    private TableColumn<Achat, String> DescripColumn;
    @FXML
    private TableColumn<Achat, String> typeColumn;
    @FXML
    private TableColumn<Achat, Integer>
            idColumn;

    @FXML
    private Label labelId;

    @FXML
    private Label labelImage;

    @FXML
    private Label labelDesc;

    @FXML
    private Label labelType;

    @FXML
    private TableView<Achat> AchatTable;
    private IService<Achat> AchatIService = new AchatService();
    private ObservableList<Achat> AchatService = FXCollections.observableArrayList();

    @FXML
    private void initialize() {
        DescripColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getDescription()));
        typeColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getType()));
        imageColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getImage()));
        idColumn.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getId()).asObject());
        showAchatDetails(null);

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
        if (achat != null) {


            labelDesc.setText(achat.getDescription());
            labelId.setText(String.valueOf(achat.getId()));
            labelImage.setText(achat.getImage());
            labelType.setText(achat.getType());
        } else {
            labelDesc.setText("");
            labelId.setText("");
            labelImage.setText("");
            labelType.setText("");
        }
    }

    @FXML
    public void updateTableView() {
        try {
            AchatService.clear();
            AchatService.addAll(AchatIService.readAll());
        } catch (Exception e) {
            e.printStackTrace();
            showAlertDialog(Alert.AlertType.ERROR, "Database Error", "Failed to fetch RendezVous", e.getMessage());
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
    public void refreshTableView(ActionEvent actionEvent) {
        updateTableView();
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

}