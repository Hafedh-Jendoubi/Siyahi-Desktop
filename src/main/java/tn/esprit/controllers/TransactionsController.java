package tn.esprit.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import tn.esprit.models.Transaction;
import tn.esprit.models.User;
import tn.esprit.services.TransactionService;
import tn.esprit.services.UserService;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import static tn.esprit.controllers.ProfileController.profileCheck;
import static tn.esprit.services.UserService.connectedUser;

public class TransactionsController {
    private TransactionService ts = new TransactionService();

    private final ObservableList<Transaction> dataList = FXCollections.observableList(ts.getOwnerOfTransactions(ts.getOwnerOfRIBs(connectedUser.getId())));

    @FXML
    private TableColumn<Transaction, String> DateCol;

    @FXML
    private TableColumn<Transaction, Long> RIBRecCol;

    @FXML
    private TableColumn<Transaction, Long> RIBSentCol;

    @FXML
    private TableColumn<Transaction, String> SoldeCol;

    @FXML
    private Circle circle;

    @FXML
    private Button congBut;

    @FXML
    private TextField filterField;

    @FXML
    private MenuItem menuItem;

    @FXML
    private TableColumn<Transaction, Integer> numCol;

    @FXML
    private TableView<Transaction> TableTrans = new TableView<>(dataList);

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

    @FXML
    void Logout(ActionEvent event) {
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
    void NavigationToAddTransaction(ActionEvent event) {
        TransactionService ts = new TransactionService();
        if(ts.getOwnerOfRIBs(connectedUser.getId()).isEmpty()){
            ListUsersController controller = new ListUsersController();
            Alert alert = controller.showFailedMessage("Vous devez avoir un compte pour transferer.");
            ButtonType confirmButton = new ButtonType("Ok");
            alert.getButtonTypes().setAll(confirmButton);
            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == confirmButton) {
                alert.close();
            }
        }else{
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
    }

    @FXML
    void initialize() {
        if(connectedUser.getRoles().equals("Employé(e)")){
            congBut.setOpacity(1);
        }else if(connectedUser.getRoles().equals("Client")){
            congBut.setOpacity(0);
        }

        TableTrans.setPlaceholder(new Label("Vous n'avez aucune transaction à afficher."));
        //Filling up the TableView
        numCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        RIBSentCol.setCellValueFactory(new PropertyValueFactory<>("ribUserSent"));
        RIBRecCol.setCellValueFactory(new PropertyValueFactory<>("ribUserReceived"));
        SoldeCol.setCellValueFactory(new PropertyValueFactory<>("cash"));
        DateCol.setCellValueFactory(new PropertyValueFactory<>("date"));

        numCol.setStyle("-fx-alignment: CENTER;");
        SoldeCol.setStyle("-fx-alignment: CENTER;");
        DateCol.setStyle("-fx-alignment: CENTER;");

        try {
            String imageName = connectedUser.getImage();
            String imagePath = "/uploads/user/" + imageName;
            Image image = new Image(getClass().getResource(imagePath).toExternalForm());
            circle.setFill(new ImagePattern(image));
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }

        //Search Filter
        FilteredList<Transaction> filteredData = new FilteredList<>(dataList, b -> true);
        filterField.setText("");
        filterField.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(User -> {
                if(newValue == null || newValue.isEmpty()){
                    return true;
                }

                String lowerCaseFilter = newValue.toLowerCase();

                if(String.valueOf(User.getRibUserReceived()).toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                }else if(String.valueOf(User.getRibUserSent()).toLowerCase().contains(lowerCaseFilter)){
                    return true;
                }else if(String.valueOf(User.getCash()).toLowerCase().contains(lowerCaseFilter)){
                    return true;
                }else
                    return false;
            });
        });
        SortedList<Transaction> sortedData = new SortedList<>(filteredData);
        sortedData.comparatorProperty().bind(TableTrans.comparatorProperty());
        TableTrans.setItems(sortedData);
    }
}
