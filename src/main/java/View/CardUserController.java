package View;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.Pane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.stage.Stage;
import Entity.Achat;
import Service.AchatService;

import java.io.File;
import java.io.IOException;


public class CardUserController {
    @FXML
    private Label carddesc;
    @FXML
    private Label cardtype;
    @FXML
    private Pane Card;
    @FXML
    private ImageView cardimage;
    @FXML
    private Label cardspecialite;
    private final AchatService MedecinS = new AchatService();

    int uid;
    String utype, udesc;

    private String[] colors = {"#CDB4DB", "#FFC8DD", "#FFAFCC", "#BDE0FE", "#A2D2FF",
            "#F4C2D7", "#FFD4E2", "#FFB7D0", "#A6D9FF", "#8BC8FF",
            "#E6A9CB", "#FFBFD3", "#FFA7C1", "#9AC2FF", "#74AFFA",
            "#D8B6D8", "#FFC9D7", "#FFB3C8", "#B0E1FF", "#8DCFFD",
            "#D3AADB", "#FFBEDF", "#FFA9CC", "#AFD5FF", "#93C5FF"};


    public void setData(Achat achat) {

        cardtype.setText(achat.getType()); cardtype.setStyle("-fx-text-fill: black;");
        carddesc.setText(achat.getDescription()); carddesc.setStyle("-fx-text-fill: black;");
        String imagePath = achat.getImage();
        if (imagePath != null && !imagePath.isEmpty()) {
            try {
                File file = new File(imagePath);
                Image image = new Image(file.toURI().toString());
                cardimage.setImage(image);
            } catch (Exception e) {
                e.printStackTrace();
                // Handle image loading error
                cardimage.setImage(null); // Set to null if image loading fails
            }
        } else {
            // No image path provided or empty
            cardimage.setImage(null); // Clear the image view
        }


      // Card.setBackground(Background.fill(Color.web(colors[(int)(Math.random()* colors.length)])));
        Card.setStyle("-fx-border-radius: 5px;-fx-border-color:#808080");

        utype = achat.getType();
        uid = achat.getId();
        udesc = achat.getDescription();

    }



    public void modifuser(ActionEvent actionEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/add-demandeAchat.fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();
            Scene scene = new Scene(root);
            AddDemandeAchatController AUC = loader.getController();


            stage.setScene(scene);
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

