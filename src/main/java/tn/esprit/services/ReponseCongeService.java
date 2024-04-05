package tn.esprit.services;

import tn.esprit.interfaces.IService;
import tn.esprit.models.Conge;
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
        List<ReponseConge> reponseconges = new ArrayList<>();
        String req = "SELECT * FROM Reponse_Conge";

        Statement st = null;
        try {
            st = cnx.createStatement();
            ResultSet res = st.executeQuery(req);
            while (res.next()) {
                ReponseConge reponseconge = new ReponseConge();
                reponseconge.setId(res.getInt("id"));
                reponseconge.setDescription(res.getString("description"));
                reponseconge.setDate_debut(res.getDate("date_debut"));
                reponseconge.setDate_fin(res.getDate("date_fin"));
                reponseconge.setDate_creation(res.getTimestamp("Date_creation"));


                reponseconges.add(reponseconge);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return reponseconges;
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