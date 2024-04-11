package tn.esprit;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application{
    public static void main(String[] args) {
        launch();
    }


    @Override
    public void start(Stage primaryStage) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/UserAuth.fxml"));
        try{
            Parent root = loader.load();
            Scene scene = new Scene(root);
            primaryStage.setTitle("Siyahi Bank | Connexion");
            primaryStage.setScene(scene);
            primaryStage.show();
        }catch (IOException e){
            throw new RuntimeException(e);
        }
    }
}
