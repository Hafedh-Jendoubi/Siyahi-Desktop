package org.example.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MaConnexion {
    //DB
    final String URL="jdbc:mysql://localhost:3306/siyahiintegration1";
    final String USR="root";
    final String PWD="";

    //Attributes
    private Connection cnx;
    private static MaConnexion instance;

    //Constructor
    private MaConnexion(){
        try {
            cnx= DriverManager.getConnection(URL,USR,PWD);
            System.out.println("connexion établie avec succès");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
    public Connection getCnx() {
        return cnx;
    }

    public static MaConnexion getInstance() {
        if(instance==null)
            instance= new MaConnexion();

        return instance;
    }
}
