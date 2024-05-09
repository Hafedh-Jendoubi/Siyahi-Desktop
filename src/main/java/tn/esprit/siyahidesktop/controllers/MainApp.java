package tn.esprit.siyahidesktop.controllers;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.web.WebView;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class MainApp extends Application {

    @Override
    public void start(Stage stage) {
        WebView webView = new WebView();
        System.setProperty("prism.forceGPU", "true");




            // Proceed with loading WebView content if user agrees
            webView.getEngine().load(Objects.requireNonNull(getClass().getResource("/tn/esprit/siyahidesktop/Map.html")).toExternalForm());


        try {
            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/tn/esprit/siyahidesktop/Localisation.fxml")));

            Scene scene = new Scene(root);
            stage.setTitle("JavaFX and WebView Example");
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
