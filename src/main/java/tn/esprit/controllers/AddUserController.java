package tn.esprit.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.text.Text;
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

    @FXML
    private Text err1;

    @FXML
    private Text err2;

    @FXML
    private Text err3;

    @FXML
    private Text err4;

    @FXML
    private Text err5;

    public static User userToUpdate = null;

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
        if (prenom.getText().matches("[a-zA-Z]{2,14}")) {
            prenom.setStyle("-fx-border-color: transparent;"); i++; err1.setOpacity(0);
        } else {
            prenom.setStyle("-fx-border-color: red;"); err1.setOpacity(1); err1.setText("• Obligatoire 2 et 14 chiffres.");
        }
        if (nom.getText().matches("[a-zA-Z]{2,19}")) {
            nom.setStyle("-fx-border-color: transparent;"); i++; err2.setOpacity(0);
        } else {
            nom.setStyle("-fx-border-color: red;"); err2.setOpacity(1); err2.setText("• Obligatoire 2 et 19 chiffres");
        }
        if (email_input.getText().matches("[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}")) {
            email_input.setStyle("-fx-border-color: transparent;"); i++; err3.setOpacity(0);
        } else {
            email_input.setStyle("-fx-border-color: red;"); err3.setOpacity(1); err3.setText("• John.Smith@example.com");
        }
        if (cin.getText().matches("\\d{8}")) {
            cin.setStyle("-fx-border-color: transparent;"); i++; err5.setOpacity(0);
        } else {
            cin.setStyle("-fx-border-color: red;"); err5.setOpacity(1); err5.setText("• Obligatoire 8 chiffres.");
        }
        if (num_tel.getText().matches("\\d{8}")) {
            num_tel.setStyle("-fx-border-color: transparent;"); i++; err4.setOpacity(0);
        } else {
            num_tel.setStyle("-fx-border-color: red;"); err4.setOpacity(1); err4.setText("• Obligatoire 8 chiffres.");
        }
        if(i == 5) { //Tous les inputs sont valides:
            if(userToUpdate == null){ // Add
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
            }else{ //update
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Confirmation");
                alert.setHeaderText("Voulez-vous vraiment modifier l'utilisateur suivant?");
                Optional<ButtonType> result = alert.showAndWait();
                if (result.isPresent() && result.get() == ButtonType.OK) {
                    User user = us.getOneByCIN(Integer.parseInt(cin.getText()));
                    user.setFirst_name(prenom.getText());
                    user.setLast_name(nom.getText());
                    if (genre.getValue().equals("Male"))
                        user.setGender("M");
                    else
                        user.setGender("F");
                    user.setAddress(adresse.getText());
                    user.setPhone_number(Integer.parseInt(num_tel.getText()));
                    user.setCin(Integer.parseInt(cin.getText()));
                    user.setImage(picturePath);
                    us.update(user);
                    CancelAddUser(event);
                } else {
                    alert.close();
                }
                CancelAddUser(event);
            }
        }
    }

    @FXML
    void browseImage(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select Image File");
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Chose File...","*.png", "*.jpg", "*.jpeg"));

        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        File selectedFile = fileChooser.showOpenDialog(window);
        if (selectedFile != null) {
            String destinationFolderPath1 = "D:\\Users\\hafeu\\Desktop\\Siyahi-Desktop\\src\\main\\resources\\uploads\\user";
            String destinationFolderPath2 = "D:\\Users\\hafeu\\Desktop\\Hafedh\\Personal-Repository\\3A42\\Semester 2\\PI-DEV\\Web Symfony\\Siyahi-Web\\public\\uploads\\user";

            try {
                java.nio.file.Path sourcePath = selectedFile.toPath();
                java.nio.file.Path destinationPath1 = Paths.get(destinationFolderPath1, selectedFile.getName());
                java.nio.file.Path destinationPath2 = Paths.get(destinationFolderPath2, selectedFile.getName());

                //Just to retreive the Picture Name in order to upload it to the Database
                String pictureName = destinationPath1.toString();
                String resultName = pictureName.replaceFirst(".*\\\\uploads\\\\user\\\\", "");
                setPicturePath(resultName);

                Files.copy(sourcePath, destinationPath1);
                Files.copy(sourcePath, destinationPath2);
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
        err1.setOpacity(0);
        err2.setOpacity(0);
        err3.setOpacity(0);
        err4.setOpacity(0);
        err5.setOpacity(0);

        if(userToUpdate == null){
            ObservableList<String> gender = FXCollections.observableArrayList("Male", "Femelle");
            genre.setValue("Male");
            genre.setItems(gender);
        }else{
            nom.setText(userToUpdate.getFirst_name());
            prenom.setText(userToUpdate.getLast_name());
            genre.setValue(userToUpdate.getGender());
            adresse.setText(userToUpdate.getAddress());
            email_input.setText(userToUpdate.getEmail()); email_input.setEditable(false); email_input.setStyle("-fx-opacity: 0.5;");
            num_tel.setText(String.valueOf(userToUpdate.getPhone_number()));
            cin.setText(String.valueOf(userToUpdate.getCin())); cin.setEditable(false); cin.setStyle("-fx-opacity: 0.5;");
            picturePath = userToUpdate.getImage();
        }
    }
}
