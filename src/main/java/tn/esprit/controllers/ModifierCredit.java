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
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import tn.esprit.models.Credit;
import tn.esprit.models.TypeCredit;
import tn.esprit.services.CreditService;
import tn.esprit.services.TypeCreditService;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.sql.Date;
import java.util.List;

public class ModifierCredit {
    @FXML
    private TextField DescriptionTFM;
    @FXML
    private DatePicker DateTFM;
    @FXML
    private TextField NbrTFM;
    @FXML
    private TextField SoldeTFM;
    @FXML
    private ImageView contrat;
    @FXML
    private ComboBox<TypeCredit> TypeCreditCB;

    private Credit selectedCredit;
    private final CreditService cs = new CreditService();
    private final TypeCreditService typeCreditService = new TypeCreditService();
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
            String targetDirectory = "src/main/resources/Images/";
            try {
                File directory = new File(targetDirectory);
                if (!directory.exists()) {
                    directory.mkdirs();
                }
                Path sourcePath = selectedFile.toPath();
                Path targetPath = new File(targetDirectory + selectedFile.getName()).toPath();
                Files.copy(sourcePath, targetPath, StandardCopyOption.REPLACE_EXISTING);
                Image image = new Image(selectedFile.toURI().toURL().toExternalForm());
                contrat.setImage(image);
                imagePath = targetPath.toString().replace("\\", "/").replace("src/main/resources/", "");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @FXML
    void resetImage(ActionEvent event) {
        contrat.setImage(null);
        imagePath = null;
    }

    @FXML
    void initialize() {
        loadCredits();
    }

    private void loadCredits() {
        List<TypeCredit> typeCredits = typeCreditService.getAll();
        ObservableList<TypeCredit> typeCreditList = FXCollections.observableArrayList(typeCredits);
        TypeCreditCB.setItems(typeCreditList);
    }

    @FXML
    void initData(Credit credit) {
        selectedCredit = credit;
        DescriptionTFM.setText(selectedCredit.getDescription());
        DateTFM.setValue(selectedCredit.getDate_debut_paiement().toLocalDate());
        NbrTFM.setText(String.valueOf(selectedCredit.getNbr_mois_paiement()));
        SoldeTFM.setText(String.valueOf(selectedCredit.getSolde_demande()));

        if (selectedCredit.getContrat() != null && !selectedCredit.getContrat().isEmpty()) {
            try {
                String imagePath = "file:src/main/resources/" + selectedCredit.getContrat();
                Image image = new Image(imagePath);
                contrat.setImage(image);
            } catch (Exception e) {
                e.printStackTrace();
                showAlert(Alert.AlertType.ERROR, "Erreur de chargement", null, "Impossible de charger l'image du contrat.");
            }
        } else {
            contrat.setImage(null);
        }

        TypeCreditCB.setValue(typeCreditService.getOne(selectedCredit.getType_credit_id()));
    }

    @FXML
    void modifierC(ActionEvent event) {
        try {
            String relativeImagePath = (imagePath != null && !imagePath.isEmpty()) ? "Images/" + new File(imagePath).getName() : null;
            selectedCredit.setDescription(DescriptionTFM.getText());
            selectedCredit.setDate_debut_paiement(Date.valueOf(DateTFM.getValue()));
            selectedCredit.setNbr_mois_paiement(Integer.parseInt(NbrTFM.getText()));
            selectedCredit.setSolde_demande(Float.parseFloat(SoldeTFM.getText()));
            selectedCredit.setContrat(relativeImagePath);

            TypeCredit selectedTypeCredit = TypeCreditCB.getSelectionModel().getSelectedItem();
            if (selectedTypeCredit != null) {
                selectedCredit.setType_credit_id(selectedTypeCredit.getId());
            }

            cs.Update(selectedCredit);
            showAlert(Alert.AlertType.INFORMATION, "Modification réussie", null, "Les modifications ont été enregistrées avec succès.");
            RetourMLV(event);
        } catch (Exception e) {
            showAlert(Alert.AlertType.ERROR, "Erreur", "Erreur lors de la modification", e.getMessage());
        }
    }

    @FXML
    void RetourMLV(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ListCredit.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            showAlert(Alert.AlertType.ERROR, "Error", e.getMessage());
        }
    }
    private void showAlert(Alert.AlertType alertType, String title, String content) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
    // Fonction utilitaire pour afficher une boîte de dialogue d'alerte
    private void showAlert(Alert.AlertType alertType, String title, String header, String content) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }
}