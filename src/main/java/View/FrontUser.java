package View;


import Service.IService;
/*import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;*/
import javafx.event.ActionEvent;
import javafx.fxml.*;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import Entity.Achat;
import Entity.Demande_achat;
import Service.AchatService;
import Service.Demande_achatService;

import Utils.DataSource;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.File;
import java.io.IOException;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.animation.PauseTransition;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;

//import javax.mail.*;
//import javax.mail.internet.InternetAddress;
//import javax.mail.internet.MimeMessage;
//import java.util.*;
public class FrontUser implements Initializable {
    @FXML
    public TextField emailtf;
    @FXML
    public TextField nommedecin;

    @FXML
    private Tab gusertab;

    @FXML
    public TextField idtf;

    @FXML
    private ImageView imagepdp;

    @FXML
    private Tab listusertab;

    @FXML
    public TextField nomtf;

    @FXML
    public TextField prenomtf;

    @FXML
    public TextField specialitetf;
    @FXML
    private DatePicker datetf;

    @FXML
    public TextField cintf;

    @FXML
    private Label uinfolabel;

    @FXML
    private GridPane userContainer;

    @FXML
    private TextField usersearch;
    @FXML
    private TextField userIdTextField;

//    @FXML
//    private TextField achatIdTextField;

    @FXML
    private TextField nomTextField;

    @FXML
    private TextField prenomTextField;

    @FXML
    private TextField numTelTextField;
    @FXML
    private ComboBox<Integer> comboBox;

    @FXML
    private TextField typePaiementTextField;

    @FXML
    private TextField cinTextField;

    @FXML
    private TextField adresseTextField;
    @FXML
    private Label dateDemandeLabel;
    private final AchatService MedecinS = new AchatService();
    private IService<Demande_achat> demandeAchatService = new Demande_achatService();
    private IService<Achat> AchatService = new AchatService();

    private IndexDemandeAchatController indexDemandeAchatController = new IndexDemandeAchatController();

    @FXML
    private TabPane usertp;
    public void setIndexDemandeAchatController(IndexDemandeAchatController controller) {
        this.indexDemandeAchatController = controller;
    }
    private Connection cnx;
    public void initialize(URL url, ResourceBundle rb) {

load();
    }

    @FXML
    void Deconnection(ActionEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        // Close the stage (which effectively closes the scene)
        stage.close();
    }
    @FXML
    void RechercheNom(ActionEvent event) {

    }

    @FXML
    void refresh(ActionEvent event) {

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
                if (column == 3) {
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



}
