package tn.esprit.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.shape.Path;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import tn.esprit.models.User;
import tn.esprit.services.UserService;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Optional;

public class AddUserController {
    @FXML
    private TextField adresse;

    @FXML
    private TextField cin;

    @FXML
    private TextField email_input;

    @FXML
    private ComboBox<String> genre;

    @FXML
    private TextField nom;

    @FXML
    private TextField num_tel;

    @FXML
    private TextField prenom;

    private String picturePath;

    //Getter & Setter
    public String getPicturePath() {return picturePath;}

    public void setPicturePath(String picturePath) {this.picturePath = picturePath;}

    //Methods
    @FXML
    void AddUser(ActionEvent event) {
        //Controle de saisie
        int i = 0;
        UserService us = new UserService();
        if (prenom.getText().matches("[a-zA-Z]{4,14}")) {
            prenom.setStyle("-fx-border-color: transparent;"); i++;
        } else {
            prenom.setStyle("-fx-border-color: red;");
        }
        if (nom.getText().matches("[a-zA-Z]{4,19}")) {
            nom.setStyle("-fx-border-color: transparent;"); i++;
        } else {
            nom.setStyle("-fx-border-color: red;");
        }
        if (email_input.getText().matches("[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}")) {
            email_input.setStyle("-fx-border-color: transparent;"); i++;
        } else {
            email_input.setStyle("-fx-border-color: red;");
        }
        if (cin.getText().matches("\\d{8}")) {
            cin.setStyle("-fx-border-color: transparent;"); i++;
        } else {
            cin.setStyle("-fx-border-color: red;");
        }
        if (num_tel.getText().matches("\\d{8}")) {
            num_tel.setStyle("-fx-border-color: transparent;"); i++;
        } else {
            num_tel.setStyle("-fx-border-color: red;");
        }
        if(i == 5) { //Tous les inputs sont valides:
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmation");
            alert.setHeaderText("Voulez-vous vraiment ajouter l'utilisateur suivant?");
            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                if (genre.getValue().equals("Male"))
                    us.add(new User(prenom.getText(), nom.getText(), "M", adresse.getText(), Integer.parseInt(num_tel.getText()), Integer.parseInt(cin.getText()), email_input.getText(), picturePath));
                else
                    us.add(new User(prenom.getText(), nom.getText(), "F", adresse.getText(), Integer.parseInt(num_tel.getText()), Integer.parseInt(cin.getText()), email_input.getText(), picturePath));
                CancelAddUser(event);
            } else {
                alert.close();
            }
            CancelAddUser(event);
        }
    }

    @FXML
    void browseImage(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select Image File");
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("*.png", "*.jpg", "*.jpeg"));

        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        File selectedFile = fileChooser.showOpenDialog(window);
        if (selectedFile != null) {
            String destinationFolderPath1 = "D:\\Users\\hafeu\\Desktop\\Siyahi-Desktop\\src\\main\\resources\\uploads\\user";
            String destinationFolderPath2 = "D:\\Users\\hafeu\\Desktop\\Hafedh\\Personal-Repository\\3A42\\Semester 2\\PI-DEV\\Web Symfony\\Siyahi-Web\\public\\uploads\\user";

            try {
                java.nio.file.Path sourcePath = selectedFile.toPath();
                java.nio.file.Path destinationPath1 = Paths.get(destinationFolderPath1, selectedFile.getName());
                java.nio.file.Path destinationPath2 = Paths.get(destinationFolderPath2, selectedFile.getName());

                Files.copy(sourcePath, destinationPath1);
                Files.copy(sourcePath, destinationPath2);

                //Just to retreive the Picture Name in order to upload it to the Database
                String pictureName = destinationPath1.toString();
                String resultName = pictureName.replaceFirst(".*\\\\uploads\\\\user\\\\", "");
                setPicturePath(resultName);
            } catch (IOException e) {
                System.err.println("Error transferring image: " + e.getMessage());
            }
        }
    }

    @FXML
    void CancelAddUser(ActionEvent event) {
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        window.close();
    }

    @FXML
    void initialize() {
        ObservableList<String> gender = FXCollections.observableArrayList("Male", "Femelle");
        genre.setValue("Male");
        genre.setItems(gender);
    }
}
