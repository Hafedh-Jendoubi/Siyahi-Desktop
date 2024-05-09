package tn.esprit.controllers;

import Entity.Achat;
import Service.AchatService;
import View.CardUserController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.*;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import tn.esprit.services.TransactionService;
import tn.esprit.services.UserService;
import Service.AchatService;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import static tn.esprit.services.UserService.connectedUser;
import static tn.esprit.controllers.ProfileController.profileCheck;

public class HomePageController {
    @FXML
    private Circle circle;

    @FXML
    private MenuItem menuItem;

    @FXML
    private BorderPane borderPane;

    @FXML
    private BorderPane borderPane1;

    @FXML
    private Rectangle reclamPicture;

    @FXML
    private Button congBut;

    @FXML
    private GridPane userContainer;

    private final AchatService MedecinS = new AchatService();

    @FXML
    void navigateToHomePage(ActionEvent event) {
        try {
            String pathTo = "";
            String titleTo = "";
            if(connectedUser.getRoles().equals("Client") || connectedUser.getRoles().equals("Employé(e)")) {
                pathTo = "/UserHomePage.fxml";
                titleTo = "Siyahi Bank | HomePage";
            } else{
                pathTo = "/AdminHomePage.fxml";
                titleTo = "Siyahi Bank | Dashboard";
            }
            Parent ajouterUserParent = FXMLLoader.load(getClass().getResource(pathTo));
            Scene ajouterUserScene = new Scene(ajouterUserParent);
            Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
            window.setScene(ajouterUserScene);
            window.setTitle(titleTo);
            window.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void navigateToUserSection(ActionEvent event) {
        try {
            Parent ajouterUserParent = FXMLLoader.load(getClass().getResource("/Users.fxml"));
            Scene ajouterUserScene = new Scene(ajouterUserParent);
            Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
            window.setScene(ajouterUserScene);
            window.setTitle("Siyahi Bank | Gestion des Utilisateurs");
            window.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

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
    void navigateToCredits(ActionEvent event) {
        try {
            String pathTo = "";
            String titleTo = "";
            if(connectedUser.getRoles().equals("Client") || connectedUser.getRoles().equals("Employé(e)")) {
                pathTo = "/ListCredit.fxml";
                titleTo = "Siyahi Bank | Gestion des Credits";
            } else{
                pathTo = "/ListTypeCredit.fxml";
                titleTo = "Siyahi Bank | Gestion des Types de Credits";
            }
            Parent ajouterUserParent = FXMLLoader.load(getClass().getResource(pathTo));
            Scene ajouterUserScene = new Scene(ajouterUserParent);
            Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
            window.setScene(ajouterUserScene);
            window.setTitle(titleTo);
            window.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void navigateToConge(ActionEvent event) {
        try {
            Parent ajouterUserParent = FXMLLoader.load(getClass().getResource("/ListConge.fxml"));
            Scene ajouterUserScene = new Scene(ajouterUserParent);
            Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
            window.setScene(ajouterUserScene);
            window.setTitle("Siyahi Bank | Gestion des Conges");
            window.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void navigateToAchat(ActionEvent event) {
        try {
            Parent ajouterUserParent = FXMLLoader.load(getClass().getResource("/demandeAchat.fxml"));
            Scene ajouterUserScene = new Scene(ajouterUserParent);
            Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
            window.setScene(ajouterUserScene);
            window.setTitle("Siyahi Bank | Gestion des Achats");
            window.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void navigateToHamroun(ActionEvent event) {
        try {
            Parent ajouterUserParent = FXMLLoader.load(getClass().getResource("/tn/esprit/siyahidesktop/MainPage.fxml"));
            Scene ajouterUserScene = new Scene(ajouterUserParent);
            Stage window = new Stage();
            window.setScene(ajouterUserScene);
            window.setTitle("Siyahi Bank | Gestion des Comptes & Services");
            window.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void navigateToReclamations(ActionEvent event) {
        try {
            Parent ajouterUserParent = FXMLLoader.load(getClass().getResource("/Home.fxml"));
            Scene ajouterUserScene = new Scene(ajouterUserParent);
            Stage window = new Stage();
            window.setScene(ajouterUserScene);
            window.setTitle("Siyahi Bank | Gestion des Conges");
            window.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void navigateToAccount(ActionEvent event) {
        Parent parent = null;
        try {
            parent = FXMLLoader.load(getClass().getResource("/tn/esprit/siyahidesktop/ShowAccountDetailsFront.fxml"));
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
    void Logout(ActionEvent event){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation");
        alert.setHeaderText("Voulez-vous vraiment déconnecter?");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            Stage window_toClose = (Stage) menuItem.getParentPopup().getOwnerWindow();
            window_toClose.close();
            Parent users_section = null;
            try {
                users_section = FXMLLoader.load(getClass().getResource("/UserAuth.fxml"));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            Scene users_sectionSecene = new Scene(users_section);
            Stage window = new Stage();
            window.setScene(users_sectionSecene);
            window.setHeight(400); window.setMaxHeight(400); window.setMinHeight(400);
            window.setWidth(600); window.setMaxWidth(600); window.setMinWidth(600);
            window.setTitle("Siyahi Bank | Connexion");
            window.show();
        } else {
            alert.close();
        }
    }

    @FXML
    void Profile(ActionEvent event) {
        Parent parent = null;
        try {
            profileCheck = 1;
            if(connectedUser.getRoles().equals("Client") || connectedUser.getRoles().equals("Employé(e)"))
                parent = FXMLLoader.load(getClass().getResource("/ProfileUser.fxml"));
            else
                parent = FXMLLoader.load(getClass().getResource("/ProfileAdmin.fxml"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Scene scene = new Scene(parent);
        Stage window = (Stage) menuItem.getParentPopup().getOwnerWindow();
        window.setScene(scene);
        window.setTitle("Siyahi Bank | Profil d'utitlisateur");
        window.show();
    }

    public void load() {
        int column = 0;
        int row = 1;
        try {
            for (Achat achat : MedecinS.readAll()) {
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("/CardUser.fxml"));
                Pane userBox = fxmlLoader.load();
                CardUserController cardC = fxmlLoader.getController();
                cardC.setData(achat);
                if (column == 2) {
                    column = 0;
                    ++row;
                }
                userContainer.add(userBox, column++, row);
                GridPane.setMargin(userBox, new Insets(10));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void initialize() {
        if(connectedUser.getRoles().equals("Employé(e)")){
            congBut.setOpacity(1);
        }else if(connectedUser.getRoles().equals("Client")){
            congBut.setOpacity(0);
        }

        try {
            String imageName = connectedUser.getImage();
            String imagePath = "/uploads/user/" + imageName;
            String image1Path = "/Images/danger.png";
            Image image = new Image(getClass().getResource(imagePath).toExternalForm());
            Image image1 = new Image(getClass().getResource(image1Path).toExternalForm());
            circle.setFill(new ImagePattern(image));
            reclamPicture.setFill(new ImagePattern(image1));
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }

        //Statistics for Admin+ Home Page
        if(connectedUser.getRoles().equals("Admin") || connectedUser.getRoles().equals("Super Admin")){
            /* --------------------- Bar Chart ------------------------ */
            CategoryAxis xAxis = new CategoryAxis();
            xAxis.setLabel("Type of User");
            NumberAxis yAxis = new NumberAxis();
            yAxis.setLabel("Quantity");

            BarChart barChart = new BarChart(xAxis, yAxis);

            XYChart.Series data = new XYChart.Series();
            data.setName("New Employees Data");

            UserService us = new UserService();
            List<Integer> result = us.getUserEmploye();

            if(connectedUser.getRoles().equals("Super Admin"))
                data.getData().add(new XYChart.Data<>("Admins", result.get(2)));
            data.getData().add(new XYChart.Data<>("Employés", result.get(1)));
            data.getData().add(new XYChart.Data<>("Clients", result.get(0)));
            barChart.getData().add(data);
            barChart.setLegendVisible(false);

            borderPane.setCenter(barChart);

            /* --------------------- Pie Chart ------------------------ */
            ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList(
                new PieChart.Data("Employés", result.get(1)),
                new PieChart.Data("Clients", result.get(0))
            );
            if(connectedUser.getRoles().equals("Super Admin"))
                pieChartData.add(new PieChart.Data("Admins", result.get(2)));

            PieChart pieChart = new PieChart(pieChartData);
            pieChart.setTitle("Nombres des users");
            pieChart.setClockwise(true);
            pieChart.setLabelLineLength(50);
            pieChart.setLabelsVisible(true);
            pieChart.setStartAngle(180);

            borderPane1.setCenter(pieChart);
        }else{
            load();
        }
    }
}
