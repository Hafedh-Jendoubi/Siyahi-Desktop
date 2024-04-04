package tn.esprit.services;

import tn.esprit.interfaces.IService;
import tn.esprit.models.ReponseConge;
import tn.esprit.util.MaConnexion;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import java.sql.Date;


public class ReponseCongeService implements IService<ReponseConge> {

    //ATT
    Connection cnx = MaConnexion.getInstance().getCnx();
    private List<ReponseConge> reponse_conges;

    //act

    @Override
    public void add(ReponseConge Reponseconge) {
        String req = "INSERT INTO conge ( description, date_debut, date_fin,Date_creation) VALUES (?, ?, ?, ?)";

// Assuming you have a PreparedStatement object named 'ps' and a Conge object named 'conge'

        try {
            PreparedStatement ps = cnx.prepareStatement(req);


            ps.setString(1, Reponseconge.getDescription());
            ps.setDate(2, Reponseconge.getDate_debut());
            ps.setDate(3, Reponseconge.getDate_fin());
            ps.setTimestamp(4, Reponseconge.getDate_creation());





            // Execute the statement
            ps.executeUpdate();
            System.out.println("Reponseconge added successfully");

        } catch (SQLException e) {
            e.printStackTrace();
            // Handle exceptions appropriately
        }
    }

    @Override
    public void update(ReponseConge Reponseconge) {

        String req = "UPDATE conge SET  description = ?, date_debut = ?, date_fin = ?, Date_creation = ?  WHERE id = ?";

        try {
            PreparedStatement ps = cnx.prepareStatement(req);

            ps.setString(1, Reponseconge.getDescription());
            ps.setDate(2, Reponseconge.getDate_debut());
            ps.setDate(3, Reponseconge.getDate_fin());
            ps.setTimestamp(4, Reponseconge.getDate_creation());


            int rowsUpdated = ps.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("ReponseConge updated successfully");
            } else {
                System.out.println("No Repponseconge found with ID: " + Reponseconge.getId());
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle exceptions appropriately
        }
    }

    // ...


    @Override
    public void delete(ReponseConge Reponseconge) {
        String req = "DELETE FROM conge WHERE id = ?";

        try {
            PreparedStatement ps = cnx.prepareStatement(req);

            ps.setInt(1, Reponseconge.getId());

            int rowsDeleted = ps.executeUpdate();
            if (rowsDeleted > 0) {
                System.out.println("ReponseConge deleted successfully");
            } else {
                System.out.println("No Reponseconge found with ID: " + Reponseconge.getId());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    @Override
    public List<ReponseConge> getAll() {
        List<ReponseConge> reponse_conges = new ArrayList<>();

        String req = "SELECT * FROM Reponseconge";

        try {
            PreparedStatement ps = cnx.prepareStatement(req);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("id");

                String description = rs.getString("description");
                Timestamp datecreation = rs.getTimestamp("Date_creation");
                Date datedebut = rs.getDate("date_debut");
                Date datefin = rs.getDate("date_fin");




                ReponseConge reponseconge = new ReponseConge(description, datedebut,datefin, datecreation);
                reponse_conges.add(reponseconge);
            }

            rs.close();
            ps.close();
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle exceptions appropriately
        }

        return reponse_conges;
    }


    @Override
    public ReponseConge getOne(int id) {
        for (ReponseConge reponseconge : reponse_conges) {
            if (reponseconge.getId() == id) {
                return reponseconge;
            }
        }
        return null;
    }
}