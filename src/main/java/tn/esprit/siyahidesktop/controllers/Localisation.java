package tn.esprit.siyahidesktop.controllers;


import javafx.concurrent.Worker;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.web.WebView;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

public class Localisation implements Initializable {
    @FXML
    private WebView mapView;

    @FXML
    private ImageView return_icon;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        mapView.getEngine().setOnAlert(event -> System.out.println(event.getData()));
        mapView.getEngine().getLoadWorker().exceptionProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                System.out.println("JavaScript error occurred: " + newValue.getMessage());
            }
        });

        URL mapUrl = getClass().getResource("/tn/esprit/siyahidesktop/Map.html");
        if (mapUrl == null) {
            System.out.println("Failed to find the map HTML file.");
        } else {
            mapView.getEngine().load(mapUrl.toExternalForm());
            System.out.println("Map loaded successfully.");
        }

        mapView.getEngine().getLoadWorker().stateProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue == Worker.State.FAILED) {
                System.out.println("Loading failed with exception:");
                System.out.println(mapView.getEngine().getLoadWorker().getException().getMessage());
            }
        });
    }


    @FXML
    void back(MouseEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/tn/esprit/siyahidesktop/ShowAccountDetailsFront.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            Stage stage = (Stage) return_icon.getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
