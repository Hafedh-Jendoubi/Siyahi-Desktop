package View;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import Entity.Achat;
import Service.AchatService;
import Service.IService;
import javafx.stage.Stage;

import java.util.regex.Pattern;

public class AddAchatController {
    @FXML
    private TextField imageTextField;

    @FXML
    private TextField typeTextField;

    @FXML
    private TextField descriptionTextField;

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

        if (imageTextField.getText() == null || imageTextField.getText().isEmpty()) {
            errorMessage += "Image field is empty!\n";
        }

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
            String image = imageTextField.getText();
            String type = typeTextField.getText();
            String description = descriptionTextField.getText();

            // Create new Achat object
            Achat newAchat = new Achat(image, type, description);

            // Insert new Achat into database
            achatService.insert(newAchat);


            Stage stage = (Stage) imageTextField.getScene().getWindow();
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
}
