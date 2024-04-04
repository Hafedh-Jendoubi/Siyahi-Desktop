package tn.esprit.siyahidesktop.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MaConnexion {
    //DB
    final String URL = "jdbc:mysql://localhost:3306/siyahi";
    final String USR = "root";
    final String PWD = "";

    //Att
    private Connection cnx;
    private static MaConnexion instance;

    //Constructor
    private MaConnexion(){
        try {
            cnx = DriverManager.getConnection(URL, USR, PWD);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Connection getCnx() {
        return cnx;
    }

    public static MaConnexion getInstance() {
        if(instance == null)
            instance = new MaConnexion();

        return instance;
    }
}
