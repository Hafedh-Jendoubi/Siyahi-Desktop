package tn.esprit.Controllers;

import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class ChatClientController {
    @FXML
    private TextArea chatArea;

    @FXML
    private TextField messageField;

    private Socket clientSocket;
    private PrintWriter outputToServer;

    public void initialize() {
        try {
            clientSocket = new Socket("localhost", 6789);
            outputToServer = new PrintWriter(clientSocket.getOutputStream(), true);

            // Crée un thread pour lire les messages du serveur en arrière-plan
            Thread readThread = new Thread(() -> {
                try {
                    Scanner inputFromServer = new Scanner(clientSocket.getInputStream());
                    while (inputFromServer.hasNextLine()) {
                        String message = inputFromServer.nextLine();
                        chatArea.appendText("Server: " + message + "\n");
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
            readThread.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void sendMessage() {
        String message = messageField.getText();
        outputToServer.println(message);
        chatArea.appendText("Client: " + message + "\n");

        if ("**close**".equals(message)) {
            try {
                clientSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        // Efface le champ de message après l'envoi
        messageField.clear();
    }
}
