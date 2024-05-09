package tn.esprit.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import tn.esprit.models.Credit;
import tn.esprit.models.TypeCredit;
import tn.esprit.services.CreditService;
import tn.esprit.services.TypeCreditService;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.sql.Date;
import java.util.List;

import static tn.esprit.services.UserService.connectedUser;

public class AjouterCredit {

    @FXML
    private ImageView contrat;

    @FXML
    private DatePicker DateTF;

    @FXML
    private TextField DescriptionTF;

    @FXML
    private TextField NbrTF;

    @FXML
    private TextField SoldeTF;

    @FXML
    private ComboBox<TypeCredit> TypeCreditCB; // ComboBox for selecting credit types

    private final CreditService creditService = new CreditService();
    private final TypeCreditService typeCreditService = new TypeCreditService();
    private String imagePath;
    @FXML

    void uploadImage(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choisir une image");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Images", "*.png", "*.jpg", "*.jpeg")
        );
        File selectedFile = fileChooser.showOpenDialog(null);
        if (selectedFile != null) {

            // Définir le répertoire cible dans les ressources
            String targetDirectory = "src/main/resources/Images/";

            try {
                // Créer le répertoire cible s'il n'existe pas
                File directory = new File(targetDirectory);
                if (!directory.exists()) {
                    directory.mkdirs();
                }

                // Copier le fichier sélectionné vers le répertoire cible
                Path sourcePath = selectedFile.toPath();
                Path targetPath = new File(targetDirectory + selectedFile.getName()).toPath();
                Files.copy(sourcePath, targetPath, StandardCopyOption.REPLACE_EXISTING);
                Image image = new Image(selectedFile.toURI().toURL().toExternalForm());
                contrat.setImage(image);
                // Mettre à jour le chemin de l'image avec le chemin relatif du fichier dans les ressources
                imagePath = targetPath.toString().replace("\\", "/").replace("src/main/resources/", "");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }}

    @FXML
    void resetImage(ActionEvent event) {
        contrat.setImage(null);  // Réinitialise l'image affichée
        imagePath = null;        // Réinitialise le chemin de l'image si nécessaire
    }

    @FXML
    void initialize() {
        // Load credit types into the ComboBox
        List<TypeCredit> typeCredits = typeCreditService.getAll();
        ObservableList<TypeCredit> typeCreditList = FXCollections.observableArrayList(typeCredits);
        TypeCreditCB.setItems(typeCreditList);
    }

    @FXML
    void AjouterC(ActionEvent event) {
        try {
            // Supprimez la validation de l'image ici
            String relativeImagePath = imagePath != null ? "Images/" + new File(imagePath).getName() : null;

            // Reste du code pour récupérer et ajouter le crédit
            TypeCredit selectedTypeCredit = TypeCreditCB.getSelectionModel().getSelectedItem();
            if (selectedTypeCredit == null) {
                showAlert(Alert.AlertType.ERROR, "Error", "Please select a credit type.");
                return;
            }

            Credit credit = new Credit();
            credit.setUser_id(connectedUser.getId());
            credit.setNbr_mois_paiement(Integer.parseInt(NbrTF.getText()));
            credit.setDescription(DescriptionTF.getText());
            credit.setContrat(relativeImagePath); // Utilisez le chemin relatif seulement si imagePath n'est pas null
            credit.setSolde_demande(Float.parseFloat(SoldeTF.getText()));
            credit.setDate_debut_paiement(Date.valueOf(DateTF.getValue()));
            credit.setType_credit_id(selectedTypeCredit.getId());

            creditService.Add(credit);
            showAlert(Alert.AlertType.INFORMATION, "Success", "Credit added successfully.");
            RetourLV(event);
        } catch (Exception e) {
            showAlert(Alert.AlertType.ERROR, "Error", e.getMessage());
        }
    }


    @FXML
    void RetourLV(ActionEvent event) {
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        window.close();
    }

    private void showAlert(Alert.AlertType alertType, String title, String content) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }


}
