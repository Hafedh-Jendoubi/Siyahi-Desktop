package tn.esprit.services;

import tn.esprit.interfaces.IService;
import tn.esprit.models.Reclamation;
import tn.esprit.models.ReponseReclamation;
import tn.esprit.util.MaConnexion;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ReponseReclamationService implements IService<ReponseReclamation> {

    //ATT
    Connection cnx = MaConnexion.getInstance().getCnx();

    @Override
    public void add(ReponseReclamation reponseReclamation) {
        String req = "INSERT INTO reponse_reclamation (description, date_creation, auteur) VALUES (?, ?, ?)";

        try {
            PreparedStatement ps = cnx.prepareStatement(req);
            ps.setString(1, reponseReclamation.getDescription());
            ps.setTimestamp(2, reponseReclamation.getDate_creation());
            ps.setString(3, reponseReclamation.getAuteur());

            ps.executeUpdate();
            System.out.println("Réponse à la réclamation ajoutée avec succès");

            ps.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(ReponseReclamation reponseReclamation) {
        String req = "UPDATE reponse_reclamation SET description = ?, date_creation = ?, auteur = ? WHERE id = ?";

        try {
            PreparedStatement ps = cnx.prepareStatement(req);
            ps.setString(1, reponseReclamation.getDescription());
            ps.setTimestamp(2, reponseReclamation.getDate_creation());
            ps.setString(3, reponseReclamation.getAuteur());
            ps.setInt(4, reponseReclamation.getId());

            int rowsUpdated = ps.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("Réponse à la réclamation mise à jour avec succès");
            } else {
                System.out.println("Aucune réponse à la réclamation trouvée avec l'ID: " + reponseReclamation.getId());
            }

            ps.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(ReponseReclamation reponseReclamation) {
        String req = "DELETE FROM reponse_reclamation WHERE id = ?";

        try {
            PreparedStatement ps = cnx.prepareStatement(req);
            ps.setInt(1, reponseReclamation.getId());

            int rowsDeleted = ps.executeUpdate();
            if (rowsDeleted > 0) {
                System.out.println("Réponse à la réclamation supprimée avec succès");
            } else {
                System.out.println("Aucune réponse à la réclamation trouvée avec l'ID: " + reponseReclamation.getId());
            }

            ps.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<ReponseReclamation> getAll() {
        List<ReponseReclamation> reponses = new ArrayList<>();

        String req = "SELECT * FROM reponse_reclamation";

        try {
            Statement st = cnx.createStatement();
            ResultSet rs = st.executeQuery(req);

            while (rs.next()) {
                int id = rs.getInt("id");
                String description = rs.getString("description");
                int reclamationId = rs.getInt("reclamation_id");
                int userId = rs.getInt("user_id");
                Timestamp dateCreation = rs.getTimestamp("date_creation");
                String auteur = rs.getString("auteur");

                ReponseReclamation reponse = new ReponseReclamation(id, description, reclamationId, userId, dateCreation, auteur);
                reponses.add(reponse);
            }

            rs.close();
            st.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return reponses;
    }

    @Override
    public ReponseReclamation getOne(int id) {
        ReponseReclamation reponseReclamation = null;
        String req = "SELECT * FROM reponse_reclamation WHERE id = ?";

        try {
            PreparedStatement ps = cnx.prepareStatement(req);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                String description = rs.getString("description");
                int reclamationId = rs.getInt("reclamation_id");
                int userId = rs.getInt("user_id");
                Timestamp dateCreation = rs.getTimestamp("date_creation");
                String auteur = rs.getString("auteur");

                reponseReclamation = new ReponseReclamation(id, description, reclamationId, userId, dateCreation, auteur);
            }

            rs.close();
            ps.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return reponseReclamation;
    }
}
