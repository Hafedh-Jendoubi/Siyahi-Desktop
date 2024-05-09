package tn.esprit.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class ServerController {

    @FXML
    private TextArea messages;

    @FXML
    private TextField ecrit;

    @FXML
    private Button send_button;

    private ServerSocket serverSocket;
    private Socket connectionSocket;
    private PrintWriter outFromServer;
    private Scanner inFromClient;

    public void initialize() {
        new Thread(() -> {
            try {
                serverSocket = new ServerSocket(7789);
                while (true) {
                    connectionSocket = serverSocket.accept();
                    inFromClient = new Scanner(connectionSocket.getInputStream());
                    outFromServer = new PrintWriter(connectionSocket.getOutputStream(), true);
                    messages.appendText("Client connected.\n");
                    receiveMessage();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();
    }

    private void receiveMessage() {
        while (connectionSocket.isConnected()) {
            if (!inFromClient.hasNextLine()) {
                break;
            }
            String input = inFromClient.nextLine();
            messages.appendText("Client: " + input + "\n");
            if (input.equals("**close**")) {
                closeConnection();
                break;
            }
        }
    }

    @FXML
    private void sendMessage() {
        String output = ecrit.getText();
        messages.appendText("Server: " + output + "\n");
        outFromServer.println(output);
        ecrit.clear();
    }

    private void closeConnection() {
        try {
            serverSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
