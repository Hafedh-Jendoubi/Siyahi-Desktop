package tn.esprit.siyahidesktop.controllers;

import javafx.concurrent.Worker;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.web.WebView;
import netscape.javascript.JSObject;

import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

public class MapTest implements Initializable {

    @FXML
    private WebView mapView;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        mapView.getEngine().setOnAlert(event -> System.out.println(event.getData()));
        mapView.getEngine().getLoadWorker().exceptionProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                System.out.println("JavaScript error occurred: " + newValue.getMessage());
            }
        });
        mapView.getEngine().getLoadWorker().stateProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue == Worker.State.FAILED) {
                System.out.println("Failed to load content: " + mapView.getEngine().getLocation());
                System.out.println("Error: " + mapView.getEngine().getLoadWorker().getException().getMessage());
            }
        });

        mapView.getEngine().load(Objects.requireNonNull(getClass().getResource("/tn/esprit/siyahidesktop/Map.html")).toExternalForm());

        // Expose Java object to JavaScript
        mapView.getEngine().getLoadWorker().stateProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue == Worker.State.SUCCEEDED) {
                JSObject window = (JSObject) mapView.getEngine().executeScript("window");
                window.setMember("javaBridge", new JavaBridge());
            }
        });
    }

    // Java class to bridge communication with JavaScript
    public class JavaBridge {
        public void showCity(String city) {
            System.out.println("City: " + city);
        }
    }
}

