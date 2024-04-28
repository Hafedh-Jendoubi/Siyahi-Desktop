package tn.esprit.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import tn.esprit.Main;
import tn.esprit.models.User;
import tn.esprit.services.UserService;

/*import com.twilio.rest.api.v2010.account.Message;
import com.twilio.Twilio;*/

import java.io.IOException;
import java.util.Optional;

import static tn.esprit.controllers.ProfileController.user;

public class AuthentificationController {
    /*public static final String ACCOUNT_SID = "AC25a8f5d86d539901682583652f62154b";
    public static final String AUTH_TOKEN = "e9ff5c1445cd614a355a97771a8c9dfa";*/
    ListUsersController controller = new ListUsersController();

    @FXML
    private ImageView Image;

    @FXML
    private TextField email;

    @FXML
    private PasswordField password;

    @FXML
    private Button send;

    @FXML
    private Label pass_label;

    @FXML
    private Hyperlink forgotpasshl;

    @FXML
    private Button cancel_send;

    @FXML
    private Button login_but;

    UserService us = new UserService();

    @FXML
    public void authentification(ActionEvent event) {
        try {
            if(us.authentification(email.getText(), password.getText()) != null) { //Login Success
                User user = us.getOneByEMAIL(email.getText());
                if(user.getActivity().equals("F")){ //Compte desactivé
                    Alert alert = controller.showFailedMessage("Votre compte a été désactivé.");
                    ButtonType confirmButton = new ButtonType("Ok");
                    alert.getButtonTypes().setAll(confirmButton);
                    Optional<ButtonType> result = alert.showAndWait();
                    if (result.isPresent() && result.get() == confirmButton) {
                        alert.close();
                    }
                }else { //Compte activé
                    if(user.getRoles().equals("[\"ROLE_USER\"]") || user.getRoles().equals("[\"ROLE_STAFF\"]")){
                        try {
                            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/UserHomePage.fxml"));
                            Parent root = fxmlLoader.load();
                            Stage stage = (Stage) email.getScene().getWindow();
                            stage.setWidth(1300); stage.setMaxWidth(1300); stage.setMinWidth(1300);
                            stage.setHeight(600); stage.setMaxHeight(600); stage.setMinHeight(600);
                            email.getScene().setRoot(root);
                            stage.setTitle("Siyahi Bank | Home Page");
                            stage.show();
                        } catch (IOException ex) {
                            System.err.println(ex.getMessage());
                        }
                    }else{
                        try {
                            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/AdminHomePage.fxml"));
                            Parent root = fxmlLoader.load();
                            Stage stage = (Stage) email.getScene().getWindow();
                            stage.setWidth(1300); stage.setMaxWidth(1300); stage.setMinWidth(1300);
                            stage.setHeight(600); stage.setMaxHeight(600); stage.setMinHeight(600);
                            email.getScene().setRoot(root);
                            stage.setTitle("Siyahi Bank | Dashboard");
                            stage.show();
                        } catch (IOException ex) {
                            System.err.println(ex.getMessage());
                        }
                    }
                }
            } else { //Login Failure.
                Alert alert = controller.showFailedMessage("Veuillez vérifier vos identifiants.");
                ButtonType confirmButton = new ButtonType("Ok");
                alert.getButtonTypes().setAll(confirmButton);
                Optional<ButtonType> result = alert.showAndWait();
                if (result.isPresent() && result.get() == confirmButton) {
                    alert.close();
                }
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @FXML
    public void ForgotPassword(ActionEvent event) {
        password.setStyle("-fx-opacity: 0;");
        forgotpasshl.setStyle("-fx-opacity: 0;");
        login_but.setStyle("-fx-opacity: 0;");
        pass_label.setStyle("-fx-opacity: 0;");
        send.setStyle("-fx-opacity: 1;");
        cancel_send.setStyle("-fx-opacity: 1;");
        Stage stage = (Stage) email.getScene().getWindow();
        stage.setTitle("Siyahi Bank | Récupérer mot de passe");
    }

    @FXML
    public void resetPass(ActionEvent event) {
        user = us.getOneByEMAIL(email.getText());
        if(user == null){
            controller.showFailedMessage("Email n'existe pas! Vérifiez vos credentials.").show();
        }else{
            us.RequestResetPassword(user);
            Parent users_section = null;
            try {
                users_section = FXMLLoader.load(getClass().getResource("/resetPass.fxml"));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            Scene users_sectionSecene = new Scene(users_section);
            Stage window = new Stage();
            window.setScene(users_sectionSecene);
            window.setHeight(400); window.setMaxHeight(400); window.setMinHeight(400);
            window.setWidth(600); window.setMaxWidth(600); window.setMinWidth(600);
            window.setTitle("Siyahi Bank | Token Verification");
            getBack(event);
            window.show();
        }
    }

    @FXML
    public void getBack(ActionEvent event) {
        password.setStyle("-fx-opacity: 1;");
        forgotpasshl.setStyle("-fx-opacity: 1;");
        login_but.setStyle("-fx-opacity: 1;");
        pass_label.setStyle("-fx-opacity: 1;");
        send.setStyle("-fx-opacity: 0;");
        cancel_send.setStyle("-fx-opacity: 0;");
        Stage stage = (Stage) email.getScene().getWindow();
        stage.setTitle("Siyahi Bank | Connexion");
    }

    @FXML
    void initialize(){
        send.setOpacity(0);
        cancel_send.setOpacity(0);
    }
}
