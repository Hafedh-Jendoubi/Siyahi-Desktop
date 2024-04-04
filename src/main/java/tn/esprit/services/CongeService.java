package tn.esprit.services;

import tn.esprit.interfaces.IService;
import tn.esprit.models.Conge;
import tn.esprit.util.MaConnexion;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import java.sql.Date;


public class CongeService implements IService<Conge> {

    //ATT
    Connection cnx = MaConnexion.getInstance().getCnx();
    private List<Conge> conges;

    //act

    @Override
    public void add(Conge conge) {
        String req = "INSERT INTO conge ( Description, Date_Debut, Date_Fin,Date_demande,Type_conge,status) VALUES (?, ?, ?, ?,?,?)";

// Assuming you have a PreparedStatement object named 'ps' and a Conge object named 'conge'

        try {
            PreparedStatement ps = cnx.prepareStatement(req);


            ps.setString(1, conge.getDescription());
            ps.setDate(2, conge.getDate_Debut());
            ps.setDate(3, conge.getDate_Fin());
            ps.setTimestamp(4, conge.getDate_demande());
            ps.setString(5, conge.getType_conge());
            ps.setBoolean(6, conge.isStatus());




            // Execute the statement
            ps.executeUpdate();
            System.out.println("conge added successfully");

        } catch (SQLException e) {
            e.printStackTrace();
            // Handle exceptions appropriately
        }
    }

    @Override
    public void update(Conge conge) {

        String req = "UPDATE conge SET  Description = ?, Date_Debut = ?, Date_Fin = ?, Date_demande = ? ,Type_demande = ? WHERE id = ?";

        try {
            PreparedStatement ps = cnx.prepareStatement(req);

            ps.setString(1, conge.getDescription());
            ps.setDate(2, conge.getDate_Debut());
            ps.setDate(3, conge.getDate_Fin());
            ps.setTimestamp(4, conge.getDate_demande());
            ps.setString(5, conge.getType_conge());
            ps.setInt(6, conge.getId());

            int rowsUpdated = ps.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("Conge updated successfully");
            } else {
                System.out.println("No conge found with ID: " + conge.getId());
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle exceptions appropriately
        }
    }

    // ...


    @Override
    public void delete(Conge conge) {
        String req = "DELETE FROM conge WHERE id = ?";

        try {
            PreparedStatement ps = cnx.prepareStatement(req);

            ps.setInt(1, conge.getId());

            int rowsDeleted = ps.executeUpdate();
            if (rowsDeleted > 0) {
                System.out.println("Conge deleted successfully");
            } else {
                System.out.println("No conge found with ID: " + conge.getId());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    @Override
    public List<Conge> getAll() {
        List<Conge> conges = new ArrayList<>();

        String req = "SELECT * FROM conge";

        try {
            PreparedStatement ps = cnx.prepareStatement(req);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("id");

                String description = rs.getString("Description");
                Timestamp dateDemande = rs.getTimestamp("Date_demande");
                Date datedebut = rs.getDate("Date_Debut");
                Date datefin = rs.getDate("Date_Fin");
                String type = rs.getString("Type_conge");

                boolean status = rs.getBoolean("status");

                Conge conge = new Conge(id,description, datedebut,datefin, dateDemande,type, status);
                conges.add(conge);
            }

            rs.close();
            ps.close();
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle exceptions appropriately
        }

        return conges;
    }


    @Override
    public Conge getOne(int id) {
        for (Conge conge : conges) {
            if (conge.getId() == id) {
                return conge;
            }
        }
        return null;
    }
}