package tn.esprit.services;
import tn.esprit.interfaces.IService;

import tn.esprit.models.Conge;
import tn.esprit.models.limitationConge;
import tn.esprit.util.MaConnexion;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class limitationcongeService implements IService<limitationConge> {
    Connection cnx = MaConnexion.getInstance().getCnx();
    private List<limitationConge> limit_conges;
    @Override
    public void add(limitationConge limitationconge) {
        String req = "INSERT INTO limitation_Conge ( annee, mois, nbr_jours) VALUES (?, ?, ?)";

// Assuming you have a PreparedStatement object named 'ps' and a Conge object named 'conge'

        try {
            PreparedStatement ps = cnx.prepareStatement(req);
            ps.setInt(1,limitationconge .getAnnee());
            ps.setString(2,limitationconge.getMois());
            ps.setInt(3, limitationconge.getNbr_jours());






            // Execute the statement
            ps.executeUpdate();
            System.out.println("conge added successfully");

        } catch (SQLException e) {
            e.printStackTrace();
            // Handle exceptions appropriately
        }}
        @Override
        public void update(limitationConge limitationconge) {

            String req = "UPDATE limitation_conge SET  annee = ?, mois = ?, nbr_jrs = ?  WHERE id = ?";

            try {
                PreparedStatement ps = cnx.prepareStatement(req);

                ps.setInt(1, limitationconge.getAnnee());
                ps.setString(2, limitationconge.getMois());
                ps.setInt(3,  limitationconge.getNbr_jours());


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




}}



