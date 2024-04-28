package tn.esprit.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import tn.esprit.models.User;
import tn.esprit.services.UserService;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Objects;
import java.util.Optional;

import static tn.esprit.services.UserService.connectedUser;

public class EditProfileUserController {

    @FXML
    private Button ToHomePage;

    @FXML
    private TextField adrField;

    @FXML
    private Circle big_circle;

    @FXML
    private TextField cinField;

    @FXML
    private TextField emailField;

    @FXML
    private ComboBox<String> genreField;

    @FXML
    private TextField nomField;

    @FXML
    private TextField prenomField;

    @FXML
    private TextField telField;

    UserService us = new UserService();

    private String picturePath;

    //Getter & Setter
    public String getPicturePath() {return picturePath;}

    public void setPicturePath(String picturePath) {this.picturePath = picturePath;}

    ProfileController controller = new ProfileController();

    @FXML
    private void navigateToHomePage(ActionEvent event) {
        controller.navigateToHomePage(event);
    }


    @FXML
    void navigateToTransactions(ActionEvent event) {
        controller.navigateToTransactions(event);
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
                picturePath = pictureName.replaceFirst(".*\\\\uploads\\\\user\\\\", "");

                Files.copy(sourcePath, destinationPath1);
                Files.copy(sourcePath, destinationPath2);
            } catch (IOException e) {
                System.err.println("Error transferring image: " + e.getMessage());
            }
        }
    }

    @FXML
    void save(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation");
        alert.setHeaderText("Voulez-vous vraiment modifier votre profile?");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            User user = us.getOneByID(connectedUser.getId());
            user.setFirst_name(nomField.getText());
            user.setLast_name(prenomField.getText());
            if (genreField.getValue().equals("Male"))
                user.setGender("M");
            else
                user.setGender("F");
            user.setAddress(adrField.getText());
            user.setPhone_number(Integer.parseInt(telField.getText()));
            user.setCin(Integer.parseInt(cinField.getText()));
            user.setImage(picturePath);
            us.update(user);
            if(user.getGender().equals("M"))
                user.setGender("Male");
            else
                user.setGender("Femelle");
            connectedUser = user;
            cancel(event);
        } else {
            alert.close();
        }
    }

    @FXML
    void cancel(ActionEvent event) {
        try {
            Parent users_section = FXMLLoader.load(getClass().getResource("/ProfileUser.fxml"));
            Scene ajouterUserScene = new Scene(users_section);
            Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
            window.setScene(ajouterUserScene);
            window.setMaxHeight(600); window.setMinHeight(600);
            window.setMaxWidth(1300); window.setMinWidth(1300);
            window.setTitle("Siyahi Bank | Profil d'utilisateur");
            window.show();
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }

    @FXML
    void initialize() {
        picturePath = connectedUser.getImage();

        nomField.setText(connectedUser.getFirst_name());
        prenomField.setText(connectedUser.getLast_name());
        ObservableList<String> gender = FXCollections.observableArrayList("Male", "Femelle"); genreField.setItems(gender); genreField.setValue(connectedUser.getGender());
        cinField.setText(String.valueOf(connectedUser.getCin()));
        telField.setText(String.valueOf(connectedUser.getPhone_number()));
        emailField.setText(connectedUser.getEmail()); emailField.setOpacity(0.5); emailField.setEditable(false);
        adrField.setText(connectedUser.getAddress());

        //Setting the user picture
        String imageName = connectedUser.getImage();
        String imagePath = "/uploads/user/" + imageName;
        Image image = new Image((Objects.requireNonNull(getClass().getResource(imagePath))).toExternalForm());
        big_circle.setFill(new ImagePattern(image));
    }
}
