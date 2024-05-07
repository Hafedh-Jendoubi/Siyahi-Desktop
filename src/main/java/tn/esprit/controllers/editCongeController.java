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
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;


public class editCongeController {
    @FXML
    private TextField descriptionTF;
    @FXML
    private DatePicker datedebutTF;
    @FXML
    private DatePicker datefinTF;
    @FXML
    private TextField type_congeTF;
    @FXML
    private ImageView justification;
    @FXML
    private CheckBox statusCB;

    private Conge selectedConge;
    private final CongeService cs = new CongeService();
    private String imagePath;
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
    public void initData(Conge congé) {
        this.selectedConge = congé;
        // Afficher les données du congé dans les champs de la vue de modification
        descriptionTF.setText(congé.getDescription());
        datedebutTF.setValue(congé.getDate_Debut().toLocalDate());
        datefinTF.setValue(congé.getDate_Fin().toLocalDate());
        type_congeTF.setText(congé.getType_conge());


    }

    // Méthode appelée lors de la sauvegarde des modifications
    @FXML
    void modifierC() {
        // Mettre à jour les données du congé sélectionné avec les nouvelles valeurs des champs
        selectedConge.setDescription(descriptionTF.getText());
        selectedConge.setDate_Debut(Date.valueOf(datedebutTF.getValue()));
        selectedConge.setDate_Fin(Date.valueOf(datefinTF.getValue()));
        selectedConge.setType_conge(imagePath);

        selectedConge.setJustification(type_congeTF.getText());
        selectedConge.setStatus(statusCB.isSelected());


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
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ListConge.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
            window.setHeight(515); window.setMaxHeight(515); window.setMinHeight(515);
            window.setWidth(600); window.setMaxWidth(600); window.setMinWidth(600);
            window.setScene(scene);
            window.show();
        } catch (IOException e) {

        }
    }

    }




