package tn.esprit.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class ClientController {

    @FXML
    private TextArea messages;

    @FXML
    private TextField ecrit;

    @FXML
    private Button send_button;

    private Socket clientSocket;
    private PrintWriter outputToServer;
    private Scanner inputFromServer;

    public void initialize() {
        try {
            clientSocket = new Socket("localhost", 7789);
            outputToServer = new PrintWriter(clientSocket.getOutputStream(), true);
            inputFromServer = new Scanner(clientSocket.getInputStream());
            messages.appendText("Establishing connection....\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void sendMessage() {
        String message = ecrit.getText();
        messages.appendText("Client: " + message + "\n");
        outputToServer.println(message);
        if (message.equals("**close**")) {
            closeConnection();
        }
        ecrit.clear();
        receiveMessage();
    }

    private void receiveMessage() {
        String inFromServer = inputFromServer.nextLine();
        messages.appendText("Server: " + inFromServer + "\n");
    }

    private void closeConnection() {
        try {
            clientSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
