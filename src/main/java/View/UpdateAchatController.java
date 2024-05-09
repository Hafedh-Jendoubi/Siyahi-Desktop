package View;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import Entity.Achat;
import Service.AchatService;
import Service.IService;
import javafx.stage.Stage;

public class UpdateAchatController {
    @FXML
    private TextField imageTextField;

    @FXML
    private TextField typeTextField;

    @FXML
    private TextField descriptionTextField;

    private Achat selectedAchat;
    private IService<Achat> achatService = new AchatService();
    private IndexAchatController indexAchatController = new IndexAchatController();

    public void setIndexAchatController(IndexAchatController controller) {
        this.indexAchatController = controller;
    }

    public void setSelectedAchat(Achat selectedAchat) {
        this.selectedAchat = selectedAchat;
        // Remplir les champs avec les valeurs de l'Achat sélectionné
        if (selectedAchat != null) {
            imageTextField.setText(selectedAchat.getImage());
            typeTextField.setText(selectedAchat.getType());
            descriptionTextField.setText(selectedAchat.getDescription());
        }
    }

    private boolean isInputValid() {
        String errorMessage = "";

        if (imageTextField.getText() == null || imageTextField.getText().isEmpty()) {
            errorMessage += "Image field is empty!\n";
        }

        if (typeTextField.getText().length() < 6 || typeTextField.getText().isEmpty()) {
            errorMessage += "Type field length must be greater than 6 !\n";
        }

        if (descriptionTextField.getText() == null || descriptionTextField.getText().isEmpty()) {
            errorMessage += "Description field is empty!\n";
        }

        if (errorMessage.isEmpty()) {
            return true;
        } else {
            showAlertDialog(Alert.AlertType.ERROR, "Error", "Failed to open new Achat window", errorMessage);
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

            // Update selected Achat object
            selectedAchat.setImage(image);
            selectedAchat.setType(type);
            selectedAchat.setDescription(description);

            // Update Achat in database
            achatService.update(selectedAchat);

            if (indexAchatController != null) {
                indexAchatController.updateTableView();
            }

            Stage stage = (Stage) imageTextField.getScene().getWindow();
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
