package tn.esprit.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.event.ActionEvent;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import tn.esprit.models.Conge;
import tn.esprit.services.CongeService;
import java.io.File;
import java.net.MalformedURLException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.sql.Date;
import java.time.LocalDate;
import java.util.Locale;
import java.util.ResourceBundle;

import javafx.scene.control.Alert;
import static tn.esprit.services.UserService.connectedUser;

public class AddCongeController {
    @FXML
    private DatePicker datedebutTF;
    @FXML
    private Text datedebut;
    @FXML
    private Text datefin;

    @FXML
    private DatePicker datefinTF;
    @FXML
    private Button uploadButton;
    @FXML
    private Button confirm;
    @FXML
    private Button retour;

    @FXML
    private TextField descriptionTF;
    @FXML
    private ComboBox<String> type_congeTF;
    @FXML
    private Text type_conge;
    @FXML
    private Text titre;
    @FXML
    private Text description;

    @FXML
    private CheckBox status;
    @FXML
    private ImageView justification;
    private ResourceBundle bundle;


    private final CongeService cs = new CongeService();
    private String imagePath;

    @FXML
    void switchToEnglish(ActionEvent event) {
        loadLanguage(Locale.ENGLISH);
    }

    @FXML
    void switchToFrench(ActionEvent event) {
        loadLanguage(Locale.FRENCH);
    }

    private void loadLanguage(Locale locale) {
        bundle = ResourceBundle.getBundle("Bundle", locale);

        // Mise à jour des textes dans l'interface utilisateur
        updateTexts();
    }

    private void updateTexts() {
        // Mettre à jour les textes avec les traductions correspondantes
        datedebutTF.setPromptText(bundle.getString("start_date"));
        datedebut.setText(bundle.getString("start_date"));
        datefinTF.setPromptText(bundle.getString("end_date"));
        datefin.setText(bundle.getString("end_date"));
        type_congeTF.setPromptText(bundle.getString("leave_type"));
        type_conge.setText(bundle.getString("leave_type"));
        description.setText(bundle.getString("description"));
        descriptionTF.setPromptText(bundle.getString("description"));
        uploadButton.setText(bundle.getString("upload_image"));
        confirm.setText(bundle.getString("confirm"));
        retour.setText(bundle.getString("return_to_leave_list"));
        status.setText(bundle.getString("status"));
        titre.setText(bundle.getString("add_leave"));


    }
    @FXML
    void showAlert1(AlertType alertType, String titleKey, String contentKey) {
        Alert alert = new Alert(alertType);
        alert.setTitle(bundle.getString(titleKey)); // Utilisation de la clé pour obtenir la traduction
        alert.setHeaderText(null);
        alert.setContentText(bundle.getString(contentKey)); // Utilisation de la clé pour obtenir la traduction
        alert.showAndWait();
    }
    @FXML

    void uploadImage(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choisir une image");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Images", ".png", ".jpg", "*.gif")
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
                justification.setImage(image);
                // Mettre à jour le chemin de l'image avec le chemin relatif du fichier dans les ressources
                imagePath = targetPath.toString().replace("\\", "/").replace("src/main/resources/", "");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }}
    @FXML
    void ajouterC(ActionEvent event) {
        // Check if any of the required fields are empty
        if (datedebutTF.getValue() == null || datefinTF.getValue() == null  || type_congeTF.getValue().isEmpty()  ) {
            showAlert(AlertType.ERROR, "Error", "Please fill in all required fields.");
            return;
        }

        try {

            // Extract data from input fields
            Date dateDebut = Date.valueOf(datedebutTF.getValue());
            Date dateFin = Date.valueOf(datefinTF.getValue());
            String description = descriptionTF.getText();
            String typeConge = type_congeTF.getValue();
            String relativeImagePath = "Images/" + new File(imagePath).getName();
            boolean stat = status.isSelected();

            LocalDate today = LocalDate.now();
            if (dateDebut.toLocalDate().isBefore(today)) {
                showAlert(AlertType.ERROR, "Error", "la date de debut de congé doit être superieur à aujourd'huit.");
                return;
            }

            // Create a Conge object
            Conge conge = new Conge(description, dateDebut, dateFin, typeConge, relativeImagePath, stat,connectedUser.getId());

            // Call CongeService to add the leave request
            cs.add(conge);

            // Show success message
            showAlert(AlertType.INFORMATION, "Success", "Leave request added successfully.");

            // Clear input fields
            clearFields();
        } catch (Exception e) {
            // Show error message if any exception occurs
            showAlert(AlertType.ERROR, "Error", e.getMessage());
        }
    }
    // Helper method to show alerts
    private void showAlert(AlertType alertType, String title, String content) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }

    // Helper method to clear input fields after submission
    private void clearFields() {
        datedebutTF.setValue(null);
        datefinTF.setValue(null);
        descriptionTF.clear();
        datefinTF.setValue(null);

        status.setSelected(false);
    }
    @FXML
    void retourLV(ActionEvent event) {
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        window.close();
    }

}

