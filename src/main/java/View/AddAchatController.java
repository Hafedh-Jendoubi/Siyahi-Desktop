package View;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import Entity.Achat;
import Service.AchatService;
import Service.IService;
import javafx.scene.image.Image;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.regex.Pattern;
import javafx.stage.FileChooser;
import java.io.File;
import javafx.scene.image.ImageView;
public class AddAchatController {
    @FXML
    private ImageView imageView;
    @FXML
    private TextField typeTextField;

    @FXML
    private TextField descriptionTextField;

    private String picturePath;

    //Getter & Setter
    public String getPicturePath() {return picturePath;}

    public void setPicturePath(String picturePath) {this.picturePath = picturePath;}

    private IService<Achat> achatService = new AchatService();

    private IndexAchatController indexAchatController = new IndexAchatController();
    public void setIndexAchatController(IndexAchatController controller) {
        this.indexAchatController = controller;
    }
    @FXML
    private void initialize() {
        setIndexAchatController(indexAchatController);
    }
    private boolean isInputValid() {
        String errorMessage = "";

        if (typeTextField.getText().length() <6 || typeTextField.getText().isEmpty()) {
            errorMessage += "Type field length must be greater than 6 !\n";
        }

        if (descriptionTextField.getText() == null || descriptionTextField.getText().isEmpty()) {
            errorMessage += "Description field is empty!\n";
        }

        if (errorMessage.isEmpty()) {
            return true;
        } else {
            showAlertDialog(Alert.AlertType.ERROR, "Error", "Failed to open new RendezVous window", errorMessage);
            return false;
        }
    }

    @FXML
    private void handleSave() {
        if (isInputValid()) {
            // Get data from fields
            String image = picturePath;
            String type = typeTextField.getText();
            String description = descriptionTextField.getText();

            // Create new Achat object
            Achat newAchat = new Achat(image, type, description);

            // Insert new Achat into database
            achatService.insert(newAchat);


            Stage stage = (Stage) typeTextField.getScene().getWindow();
         //   if (indexAchatController != null) {
                indexAchatController.updateTableView();

            stage.close();
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
    private void handleUploadImage(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select Image File");
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Chose File...", "*.png", "*.jpg", "*.jpeg"));

        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        File selectedFile = fileChooser.showOpenDialog(window);
        if (selectedFile != null) {
            String destinationFolderPath1 = "D:\\Users\\hafeu\\Desktop\\imedPI\\javaFx\\src\\main\\resources";

            try {
                java.nio.file.Path sourcePath = selectedFile.toPath();
                java.nio.file.Path destinationPath1 = Paths.get(destinationFolderPath1, selectedFile.getName());

                //Just to retreive the Picture Name in order to upload it to the Database
                String pictureName = destinationPath1.toString();
                System.out.println(pictureName);
                setPicturePath(pictureName);

                Files.copy(sourcePath, destinationPath1);
            } catch (IOException e) {
                System.err.println("Error transferring image: " + e.getMessage());
            }
        }
    }
}
