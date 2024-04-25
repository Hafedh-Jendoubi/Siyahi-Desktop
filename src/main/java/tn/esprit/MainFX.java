package tn.esprit;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


import java.io.IOException;
import java.net.URL;

public class MainFX extends Application {

    @Override
    public void start(Stage stage) throws IOException {
        /*
            URL resourceUrl = getClass().getResource("/AddService.fxml");
            assert resourceUrl != null;*/

            //Parent root= FXMLLoader.load(resourceUrl);
            try{
                Parent root =  FXMLLoader.load(getClass().getResource("/FXML/MainPage.fxml"));
                Scene scene1=new Scene(root);
                stage.setScene(scene1);
                stage.show();
            }catch (IOException e){
                e.printStackTrace();
            }


    }
    public static void main(String[] args){launch();}
}
