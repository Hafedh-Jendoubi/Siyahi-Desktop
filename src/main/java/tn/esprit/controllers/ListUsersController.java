package tn.esprit.controllers;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Side;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import tn.esprit.Main;
import tn.esprit.models.User;
import tn.esprit.services.UserService;

import java.awt.*;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Optional;

import static tn.esprit.controllers.AddUserController.userToUpdate;
import static tn.esprit.controllers.ProfileController.profileCheck;
import static tn.esprit.services.UserService.connectedUser;
import static tn.esprit.controllers.ProfileController.user;
import static tn.esprit.controllers.AddUserController.userToUpdate;

public class ListUsersController {
    private UserService us = new UserService();

    private final ObservableList<User> dataList = FXCollections.observableList(us.getAll());

    @FXML
    private TableColumn<User, String> ActiviteCol;

    @FXML
    private TableColumn<User, String> AdrCol;

    @FXML
    private TableColumn<User, Integer> CinCol;

    @FXML
    private TableColumn<User, String> GenreCol;

    @FXML
    private TableColumn<User, String> NomCol;

    @FXML
    private TableColumn<User, String> PrenomCol;

    @FXML
    private TableColumn<User, String> RoleCol;

    @FXML
    private TableColumn<User, Integer> TelCol;

    @FXML
    private TableView<User> TableUser = new TableView<>(dataList);

    @FXML
    private MenuItem menuItem;

    @FXML
    private Circle circle;

    @FXML
    private TextField filterField = new TextField();

    @FXML
    private TableColumn<User, Void> actionCol;

    @FXML
    public void navigateToHomePage(ActionEvent event) {
        try {
            Parent acceuil = FXMLLoader.load(getClass().getResource("/AdminHomePage.fxml"));
            Scene ajouterUserScene = new Scene(acceuil);
            Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
            window.setScene(ajouterUserScene);
            window.setMaxHeight(600); window.setMinHeight(600);
            window.setMaxWidth(1300); window.setMinWidth(1300);
            window.setTitle("Siyahi Bank | Dashboard");
            window.show();
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }

    @FXML
    public void navigateToUserSection(ActionEvent event) {
        try {
            Parent users_section = FXMLLoader.load(getClass().getResource("/Users.fxml"));
            Scene ajouterUserScene = new Scene(users_section);
            Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
            window.setScene(ajouterUserScene);
            window.setMaxHeight(600); window.setMinHeight(600);
            window.setMaxWidth(1300); window.setMinWidth(1300);
            window.setTitle("Siyahi Bank | La Gestion des Utilisateurs");
            window.show();
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }

    public void NavigateToAddUser(ActionEvent event) {
        try {
            Parent users_section = FXMLLoader.load(getClass().getResource("/AddUser.fxml"));
            Scene users_sectionSecene = new Scene(users_section);
            Stage window = new Stage();
            window.setScene(users_sectionSecene);
            window.setMaxHeight(550); window.setMinHeight(550);
            window.setMaxWidth(400); window.setMinWidth(400);
            if(userToUpdate == null)
                window.setTitle("Siyahi Bank | Ajouter Utilisateur");
            else
                window.setTitle("Siyahi Bank | Modifier Utilisateur");
            window.show();
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }

    @FXML
    public void NavigateToUserAuth(ActionEvent event) {
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
    }

    @FXML
    public void Logout(ActionEvent event){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation");
        alert.setHeaderText("Voulez-vous vraiment déconnecter?");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            NavigateToUserAuth(event);
        } else {
            alert.close();
        }
    }

    @FXML
    public void Profile(ActionEvent event) {
        Parent parent = null;
        try {
            profileCheck = 1;
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

    public Alert showFailedMessage(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Erreur de connexion");
        alert.setHeaderText("Une erreur de connexion est survenue.");
        alert.setContentText(message);

        return alert;
    }

    public void showSuccessMessage(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Succès");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    @FXML
    public void addUser(ActionEvent event){
        userToUpdate = null;
        NavigateToAddUser(event);
    }

    @FXML
    public void viewUser(MouseEvent event) {
        profileCheck = 2;
        if (event.getButton() == MouseButton.PRIMARY && event.getClickCount() == 2) {
            User selectedUser = TableUser.getSelectionModel().getSelectedItem();
            user = selectedUser;
            if (selectedUser != null) {
                try {
                    Parent parent = FXMLLoader.load(getClass().getResource("/ProfileAdmin.fxml"));
                    Scene scene = new Scene(parent);
                    Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
                    window.setScene(scene);
                    window.show();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void showMenu(User user, Button actionButton) {
        ContextMenu contextMenu = new ContextMenu();
        MenuItem editItem = new MenuItem("Modifier");
        MenuItem deleteItem = new MenuItem("Supprimer");
        MenuItem blockItem = new MenuItem("Bloquer");
        MenuItem unblockItem = new MenuItem("Débloquer");

        editItem.setOnAction(event -> {
            userToUpdate = user;
            NavigateToAddUser(event);
        });
        deleteItem.setOnAction(event -> {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmation");
            alert.setHeaderText("Voulez-vous vraiment supprimer l'utilisateur suivant ?");
            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                us.delete(user);
                showSuccessMessage("Utilisateur supprimé avec succès!");
            } else {
                alert.close();
            }
        });
        blockItem.setOnAction(event -> {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmation");
            alert.setHeaderText("Voulez-vous vraiment désactiver l'utilisateur suivant?");
            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                us.disableUser(user);
                showSuccessMessage("Utilisateur a été désactivé avec succès!");
            } else {
                alert.close();
            }
            TableUser.refresh();
        });
        unblockItem.setOnAction(event -> {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmation");
            alert.setHeaderText("Voulez-vous vraiment activer l'utilisateur suivant?");
            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                us.enableUser(user);
                showSuccessMessage("Utilisateur a été activé avec succès!");
            } else {
                alert.close();
            }
        });

        if(user.getActivity().equals("Active"))
            contextMenu.getItems().addAll(editItem, deleteItem, blockItem);
        else
            contextMenu.getItems().addAll(editItem, deleteItem, unblockItem);
        contextMenu.show(actionButton, Side.BOTTOM, 0, 0);
    }

    @FXML
    void initialize() {
        //Filling up the TableView
        try {
            ActiviteCol.setCellValueFactory(new PropertyValueFactory<>("activity"));
            NomCol.setCellValueFactory(new PropertyValueFactory<>("last_name"));
            PrenomCol.setCellValueFactory(new PropertyValueFactory<>("first_name"));
            GenreCol.setCellValueFactory(new PropertyValueFactory<>("gender"));
            AdrCol.setCellValueFactory(new PropertyValueFactory<>("address"));
            TelCol.setCellValueFactory(new PropertyValueFactory<>("phone_number"));
            CinCol.setCellValueFactory(new PropertyValueFactory<>("cin"));
            RoleCol.setCellValueFactory(new PropertyValueFactory<>("roles"));
            actionCol.setCellFactory(col -> new TableCell<User, Void>() {
                private final Button actionButton = new Button("☰");

                {
                    actionButton.setOnAction(event -> {
                        User user = getTableView().getItems().get(getIndex());
                        showMenu(user, actionButton);
                    });
                }

                @Override
                protected void updateItem(Void item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty) {
                        setGraphic(null);
                    } else {
                        setGraphic(actionButton);
                    }
                }
            });

            //Centering TableView Data:
            TelCol.setStyle("-fx-alignment: CENTER;");
            CinCol.setStyle("-fx-alignment: CENTER;");
            actionCol.setStyle("-fx-alignment: CENTER;");
        }catch (Exception e){
            System.err.println(e.getMessage());
        }

        //Set Connected User Image
        try {
            String imageName = connectedUser.getImage();
            String imagePath = "/uploads/user/" + imageName;
            javafx.scene.image.Image image = new Image(getClass().getResource(imagePath).toExternalForm());
            circle.setFill(new ImagePattern(image));
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }

        //Search Filter
        FilteredList<User> filteredData = new FilteredList<>(dataList, b -> true);
        filterField.setText("");
        filterField.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(User -> {
                if(newValue == null || newValue.isEmpty()){
                    return true;
                }

                String lowerCaseFilter = newValue.toLowerCase();

                if(User.getFirst_name().toLowerCase().indexOf(lowerCaseFilter) != -1){
                    return true;
                } else if (User.getLast_name().toLowerCase().indexOf(lowerCaseFilter) != -1){
                    return true;
                }else if(String.valueOf(User.getCin()).toLowerCase().indexOf(lowerCaseFilter) != -1){
                    return true;
                }else if(String.valueOf(User.getPhone_number()).toLowerCase().indexOf(lowerCaseFilter) != -1){
                    return true;
                }else
                    return false;
            });
        });
        SortedList<User> sortedData = new SortedList<>(filteredData);
        sortedData.comparatorProperty().bind(TableUser.comparatorProperty());
        TableUser.setItems(sortedData);
    }
}