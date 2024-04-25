package tn.esprit.Controllers;

import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class ChatServerController {

    @FXML
    private TextArea chatArea;

    @FXML
    private TextField messageField;

    private ServerSocket serverSocket;
    private Socket connectionSocket;
    private PrintWriter outFromServer;
    private Scanner inFromClient;

    @FXML
    public void initialize() {
        try {
            serverSocket = new ServerSocket(6789);
            connectionSocket = serverSocket.accept();
            outFromServer = new PrintWriter(connectionSocket.getOutputStream(), true);
            inFromClient = new Scanner(connectionSocket.getInputStream());

            Thread messageListener = new Thread(() -> {
                while (true) {
                    String message = inFromClient.nextLine();
                    if (message.equals("**close**")) {
                        break;
                    }
                    chatArea.appendText("Client: " + message + "\n");
                }
            });
            messageListener.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void sendMessage() {
        String message = messageField.getText();
        outFromServer.println(message);
        chatArea.appendText("Server: " + message + "\n");
        messageField.clear();
    }
}
