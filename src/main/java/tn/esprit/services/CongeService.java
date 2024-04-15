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
        String req = "INSERT INTO conge ( Description, Date_Debut, Date_Fin,Type_conge,justification,status) VALUES (?, ?, ?, ?,?,?)";

// Assuming you have a PreparedStatement object named 'ps' and a Conge object named 'conge'

        try {
            PreparedStatement ps = cnx.prepareStatement(req);


            ps.setString(1, conge.getDescription());
            ps.setDate(2, conge.getDate_Debut());
            ps.setDate(3, conge.getDate_Fin());

            ps.setString(4, conge.getType_conge());
            ps.setString(5, conge.getJustification());
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

        String req = "UPDATE `credit` SET `Description`=?, `Date_Debut`=?, `Date_fin`=?, `Date_demande`=?,`Justification`=? ,`Type_conge`=?, `status`=? WHERE `id`=?";
        try {
            PreparedStatement ps = cnx.prepareStatement(req);
            ps.setString(1, conge.getDescription());
            ps.setDate(2, conge.getDate_Debut());
            ps.setDate(3, conge.getDate_Fin());
            ps.setTimestamp(4, conge.getDate_demande());
            ps.setString(5, conge.getType_conge());
            ps.setString(6, conge.getJustification());
            ps.setBoolean(7, conge.isStatus());
            ps.setInt(8, conge.getId()); // Assuming Conge class has an id field
            int rowsUpdated = ps.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("Reclamation updated successfully");
            } else {
                System.out.println("No reclamation found with ID: " + conge.getId());
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
        String req = "SELECT * FROM Conge";

        Statement st = null;
        try {
            st = cnx.createStatement();
            ResultSet res = st.executeQuery(req);
            while (res.next()) {
                Conge conge = new Conge();
                conge.setId(res.getInt("id"));
                conge.setDescription(res.getString("Description"));
                conge.setDate_Debut(res.getDate("Date_Debut"));
                conge.setDate_Fin(res.getDate("Date_Fin"));
                conge.setDate_demande(res.getTimestamp("Date_demande"));
                conge.setType_conge(res.getString("Type_conge"));
                conge.setStatus(res.getBoolean("status"));
                conge.setJustification(res.getString("Justification"));

                conges.add(conge);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return conges;
    }


    @Override
    public Conge getOne(int id) {
        String req = "SELECT * FROM conge WHERE id = ?";
        Conge conge = null;
        try {
            PreparedStatement st = cnx.prepareStatement(req);
            st.setInt(1, id);
            ResultSet res = st.executeQuery();
            if (res.next()) {
                 conge = new Conge();
                conge.setId(res.getInt("id"));
                conge.setDescription(res.getString("Description"));
                conge.setDate_Debut(res.getDate("Date_Debut"));
                conge.setDate_Fin(res.getDate("Date_Fin"));
                conge.setDate_demande(res.getTimestamp("Date_demande"));
                conge.setType_conge(res.getString("Type_conge"));
                conge.setStatus(res.getBoolean("status"));
                conge.setJustification(res.getString("Justification"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return conge;
    }
}
