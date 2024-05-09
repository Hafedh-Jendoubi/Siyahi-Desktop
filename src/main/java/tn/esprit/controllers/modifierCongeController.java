package tn.esprit.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import tn.esprit.models.Conge;
import tn.esprit.services.CongeService;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.sql.Date;
import java.util.Locale;
import java.util.ResourceBundle;

public class modifierCongeController {
    @FXML
    private TextField descriptionTF;
    @FXML
    private DatePicker datedebutTF;
    @FXML
    private DatePicker datefinTF;

    @FXML
    private ImageView justification;
    @FXML
    private CheckBox statusCB;
    @FXML
    private ComboBox<String> type_congeTF;
    @FXML
    private Text datedebut;
    @FXML
    private Text datefin;
    @FXML
    private Text type_conge;
    @FXML
    private Text description;

    private Conge selectedConge;
    private final CongeService cs = new CongeService();
    private String imagePath;
    private ResourceBundle bundle;
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
    public void initData1(Conge congé) {
        this.selectedConge = congé;
        // Afficher les données du congé dans les champs de la vue de modification
        descriptionTF.setText(congé.getDescription());
        datedebutTF.setValue(congé.getDate_Debut().toLocalDate());
        datefinTF.setValue(congé.getDate_Fin().toLocalDate());
        type_congeTF.setValue(congé.getType_conge());



    }

    // Méthode appelée lors de la sauvegarde des modifications
    @FXML
    void editc() {
        // Mettre à jour les données du congé sélectionné avec les nouvelles valeurs des champs
        selectedConge.setDescription(descriptionTF.getText());
        selectedConge.setDate_Debut(Date.valueOf(datedebutTF.getValue()));
        selectedConge.setDate_Fin(Date.valueOf(datefinTF.getValue()));
        selectedConge.setType_conge(imagePath);

        selectedConge.setJustification(type_congeTF.getValue());



        // Call CongeService to add the leave request
        cs.update(selectedConge);


        // Afficher un message de succès
        showAlert(Alert.AlertType.INFORMATION, "Modification réussie", null, "Les modifications ont été enregistrées avec succès.");
    }

    // Fonction utilitaire pour afficher une boîte de dialogue d'alerte
    private void showAlert(Alert.AlertType alertType, String title, String header, String content) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }
    @FXML
    void retourLV(ActionEvent event) {
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        window.close();
    }

}

