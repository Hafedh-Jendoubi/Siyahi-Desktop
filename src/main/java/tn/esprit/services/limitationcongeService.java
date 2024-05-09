package tn.esprit.services;
import tn.esprit.interfaces.ConService;

import tn.esprit.models.Conge;
import tn.esprit.models.limitationConge;
import tn.esprit.util.MaConnexion;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class limitationcongeService implements ConService<limitationConge> {
    Connection cnx = MaConnexion.getInstance().getCnx();
    private List<limitationConge> limit_conges;
    @Override
    public void add(limitationConge limitationconge) {
        String req = "INSERT INTO limitation_Conge ( annee, mois, nbr_jours, user_id) VALUES (?, ?, ?, ?)";

// Assuming you have a PreparedStatement object named 'ps' and a Conge object named 'conge'

        try {
            PreparedStatement ps = cnx.prepareStatement(req);
            ps.setInt(1,limitationconge .getAnnee());
            ps.setString(2,limitationconge.getMois());
            ps.setInt(3, limitationconge.getNbr_jours());
            ps.setInt(4, limitationconge.getUser_id());





            // Execute the statement
            ps.executeUpdate();
            System.out.println("conge added successfully");

        } catch (SQLException e) {
            e.printStackTrace();
            // Handle exceptions appropriately
        }}
    @Override
    public void update(limitationConge limitationconge) {

        String req = "UPDATE limitation_conge SET  annee = ?, mois = ?, nbr_jours = ?  WHERE id = ?";

        try {
            PreparedStatement ps = cnx.prepareStatement(req);

            ps.setInt(1, limitationconge.getAnnee());
            ps.setString(2, limitationconge.getMois());
            ps.setInt(3,  limitationconge.getNbr_jours());
            ps.setInt(4, limitationconge.getId());


            int rowsUpdated = ps.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("Conge updated successfully");
            } else {
                System.out.println("No conge found with ID: " + limitationconge.getId());
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle exceptions appropriately
        }

    }
    @Override
    public void delete(limitationConge limitationconge) {
        String req = "DELETE FROM limitation_conge WHERE id = ?";

        try {
            PreparedStatement ps = cnx.prepareStatement(req);

            ps.setInt(1, limitationconge.getId());

            int rowsDeleted = ps.executeUpdate();
            if (rowsDeleted > 0) {
                System.out.println("Conge deleted successfully");
            } else {
                System.out.println("No conge found with ID: " + limitationconge.getId());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
    @Override
    public List<limitationConge> getAll() {
        List<limitationConge> conges = new ArrayList<>();
        String req = "SELECT * FROM limitation_conge";

        Statement st = null;
        try {
            st = cnx.createStatement();
            ResultSet res = st.executeQuery(req);
            while (res.next()) {
                limitationConge conge = new limitationConge();
                conge.setId(res.getInt("id"));
                conge.setAnnee(res.getInt("annee"));
                conge.setMois(res.getString("mois"));
                conge.setNbr_jours(res.getInt("nbr_jours"));


                conges.add(conge);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return conges;
    }
    @Override
    public limitationConge getOne(int id) {
        String req = "SELECT * FROM limitation_conge WHERE id = ?";
        limitationConge conge = null;
        try {
            PreparedStatement st = cnx.prepareStatement(req);
            st.setInt(1, id);
            ResultSet res = st.executeQuery();
            if (res.next()) {
                conge = new limitationConge();
                conge.setId(res.getInt("id"));
                conge.setAnnee(res.getInt("annee"));
                conge.setMois(res.getString("mois"));
                conge.setNbr_jours(res.getInt("nbr_jrs"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return conge;
    }

    public List<limitationConge> getLimitationConges(int id) {
        List<limitationConge> LimitationConges = new ArrayList<>();
        String req = "SELECT * FROM `limitation_conge` WHERE user_id = ?";
        try {

            PreparedStatement ps = cnx.prepareStatement(req);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                limitationConge conge = new limitationConge(rs.getInt(2), rs.getString(3), rs.getInt(4));
                conge.setId(rs.getInt(1));
                LimitationConges.add(conge);
            }
        } catch (SQLException ex) {
            System.out.println("Failed to get Conges: " + ex.getMessage());
        }

        return LimitationConges;
    }

    public int getDaysLeft(int id, int annee, String mois){
        String req = "SELECT SUM(nbr_jours) AS total_days FROM limitation_conge WHERE user_id = ? AND annee = ? AND mois = ?";
        int totalDays = 0;
        try {
            PreparedStatement ps = cnx.prepareStatement(req);
            ps.setInt(1, id);
            ps.setInt(2, annee);
            ps.setString(3, mois);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                totalDays = rs.getInt("total_days");
            }
            rs.close();
            ps.close();
        } catch (SQLException e) {
            System.out.println("Failed to get: " + e.getMessage());
        }
        return 10 - totalDays;
    }
}