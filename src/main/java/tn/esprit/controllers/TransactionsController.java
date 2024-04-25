package tn.esprit.controllers;

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
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;

import static tn.esprit.controllers.ProfileController.profileCheck;
import static tn.esprit.services.UserService.connectedUser;

public class TransactionsController {
    @FXML
    private Circle circle;

    @FXML
    private MenuItem menuItem;

    @FXML
    void navigateToTransactions(ActionEvent event) {
        try {
            Parent ajouterUserParent = FXMLLoader.load(getClass().getResource("/Transactions.fxml"));
            Scene ajouterUserScene = new Scene(ajouterUserParent);
            Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
            window.setScene(ajouterUserScene);
            window.setTitle("Siyahi Bank | Gestion des Transactions");
            window.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void navigateToUserHomePage(ActionEvent event) {
        try {
            Parent ajouterUserParent = FXMLLoader.load(getClass().getResource("/UserHomePage.fxml"));
            Scene ajouterUserScene = new Scene(ajouterUserParent);
            Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
            window.setScene(ajouterUserScene);
            window.setTitle("Siyahi Bank | Home Page");
            window.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void Profile(ActionEvent event) {
        Parent parent = null;
        try {
            profileCheck = 1;
            parent = FXMLLoader.load(getClass().getResource("/ProfileUser.fxml"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Scene scene = new Scene(parent);
        Stage window = (Stage) menuItem.getParentPopup().getOwnerWindow();
        window.setScene(scene);
        window.setTitle("Siyahi Bank | Profil d'utitlisateur");
        window.show();
    }

    @FXML
    void Logout(ActionEvent event) {
        HomePageController controller = new HomePageController();
        controller.Logout(event);
    }

    @FXML
    void NavigationToAddTransaction(ActionEvent event) {
        try {
            Parent users_section = FXMLLoader.load(getClass().getResource("/AddTransaction.fxml"));
            Scene users_sectionSecene = new Scene(users_section);
            Stage window = new Stage();
            window.setScene(users_sectionSecene);
            window.setMaxHeight(550); window.setMinHeight(550);
            window.setMaxWidth(400); window.setMinWidth(400);
            window.setTitle("Siyahi Bank | Transferer Argent");
            window.show();
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }

    @FXML
    void ListTransactions(ActionEvent event) {

    }

    @FXML
    void initialize() {
        try {
            String imageName = connectedUser.getImage();
            String imagePath = "/uploads/user/" + imageName;
            Image image = new Image(getClass().getResource(imagePath).toExternalForm());
            circle.setFill(new ImagePattern(image));
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }
}
