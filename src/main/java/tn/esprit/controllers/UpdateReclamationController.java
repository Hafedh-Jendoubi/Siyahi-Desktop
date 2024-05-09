package tn.esprit.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.controlsfx.control.Notifications;
import tn.esprit.models.ObjetReclamation;
import tn.esprit.models.Reclamation;
import tn.esprit.services.ReclamationService;

import java.io.IOException;

public class UpdateReclamationController {
    @FXML
    private Button btnUpdate;

    @FXML
    private TextField auteurField;

    @FXML
    private TextField descriptionField;

    @FXML
    private TextField idField;

    @FXML
    private TextField emailField;

    @FXML
    private ComboBox<String> objectComboBox;

    @FXML
    private Text statusField;

    private ReclamationService reclamationService;

    public UpdateReclamationController() {
        reclamationService = ReclamationService.getInstance();
    }

    public void initData(Reclamation details) {
        idField.setOpacity(0);
        ObservableList<String> reclamations = FXCollections.observableArrayList("Frais Bancaires Inattendus", "Blocage de Carte Bancaire", "Virement Non Reçu", "Demande Extrait"); objectComboBox.setItems(reclamations);
        objectComboBox.setValue(details.getObject().getNom());
        idField.setText(String.valueOf(details.getId()));
        descriptionField.setText(details.getDescription());
        auteurField.setText(details.getAuteur());
        emailField.setText(details.getEmail());
        if(details.isStatus())
            statusField.setText("Répondu");
        else
            statusField.setText("En attente");
    }

    @FXML
    private void updateButtonClicked(ActionEvent event) {
        Reclamation reclamation = reclamationService.getOne(Integer.parseInt(idField.getText()));
        if(!reclamation.isStatus()){
            ObjetReclamation or = new ObjetReclamation(objectComboBox.getValue());
            reclamation.setObject(or);
            reclamation.setDescription(descriptionField.getText());
            reclamation.setAuteur(auteurField.getText());
            reclamation.setEmail(emailField.getText());
            reclamationService.update(reclamation);
            Notifications notificationBuilder = Notifications.create()
                    .title("Reclamation modifié")
                    .text("Votre réclamation a été modifié avec succes!")
                    .graphic(new ImageView())
                    .hideAfter(Duration.seconds(5))
                    .position(Pos.CENTER);
            notificationBuilder.showInformation();
        }else{
            Notifications notificationBuilder = Notifications.create()
                    .title("Error !")
                    .text("Votre réclamation est répondu. Vous pouvez pas la modifier!")
                    .graphic(new ImageView())
                    .hideAfter(Duration.seconds(5))
                    .position(Pos.CENTER);
            notificationBuilder.showInformation();
        }
        RetourHome(event);
    }

    public void delete(ActionEvent event) {
        Reclamation reclamation = reclamationService.getOne(Integer.parseInt(idField.getText()));
        reclamationService.delete(reclamation);
        Notifications notificationBuilder = Notifications.create()
                .title("Reclamation supprimé")
                .text("Votre réclamation a été supprimé avec succes!")
                .graphic(new ImageView())
                .hideAfter(Duration.seconds(5))
                .position(Pos.CENTER);
        notificationBuilder.showInformation();
        RetourHome(event);
    }

    @FXML
    void repondreRec(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/AddReponseReclamation.fxml"));
        Parent root = loader.load();
        AddReponseReclamationController controller = loader.getController();
        controller.initData(Integer.parseInt(idField.getText()));

        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.setTitle("Repondre Reclamation");
        stage.show();
    }

    private void showUpdateForm(Reclamation reclamation) {
        // Implement the logic to show the update form
    }

    private void showAlert(Alert.AlertType alertType, String title, String headerText, String contentText) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(headerText);
        alert.setContentText(contentText);
        alert.showAndWait();
    }

    public void RetourHome(ActionEvent event) {
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        window.close();
    }
}