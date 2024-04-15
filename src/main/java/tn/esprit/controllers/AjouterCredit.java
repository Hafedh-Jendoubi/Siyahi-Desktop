package tn.esprit.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import tn.esprit.models.Credit;
import tn.esprit.services.CreditService;

import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;

public class AjouterCredit {

    @FXML
    private TextField ContratTF;

    @FXML
    private DatePicker DateTF;

    @FXML
    private TextField DescriptionTF;

    @FXML
    private TextField NbrTF;

    @FXML
    private TextField SoldeTF;

    private final CreditService cs = new CreditService();
    @FXML
    void AjouterC(ActionEvent event) {
        try{
        cs.Insert(new Credit(Integer.parseInt(NbrTF.getText()),DescriptionTF.getText(),ContratTF.getText(),Float.parseFloat(SoldeTF.getText()),Date.valueOf(DateTF.getValue())));
            showAlert(Alert.AlertType.INFORMATION, "Success", "Leave request added successfully.");
            clearFields();

       // } catch (IllegalArgumentException e) {
       //     System.out.println("Erreur : " + e.getMessage());
        }catch (Exception f) {
            showAlert(Alert.AlertType.ERROR, "Error", f.getMessage());

        }
    }

    @FXML
    void RetourLV(ActionEvent event) {
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
    private void clearFields() {
        DateTF.setValue(null);
        DescriptionTF.clear();
        NbrTF.clear();
        SoldeTF.clear();
        ContratTF.clear();
    }


}
