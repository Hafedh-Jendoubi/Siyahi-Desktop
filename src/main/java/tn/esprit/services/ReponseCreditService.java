package tn.esprit.services;

import tn.esprit.models.ReponseCredit;
import tn.esprit.util.MaConnexion;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ReponseCreditService {
    private Connection cnx = MaConnexion.getInstance().getCnx();

    public void Add(ReponseCredit reponseCredit) {
        if (reponseCredit.getNbr_mois_paiement() <= 0) {
            throw new IllegalArgumentException("Le nombre de mois de paiement doit être supérieur à zéro.");
        }
        String req = "INSERT INTO `reponse_credit`(`nbr_mois_paiement`, `description`, `solde_a_payer`, `auto_financement`, `date_debut_paiement`, `credit_id`, `user_id`) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try {
            PreparedStatement st = cnx.prepareStatement(req);
            st.setInt(1, reponseCredit.getNbr_mois_paiement());
            st.setString(2, reponseCredit.getDescription());
            st.setFloat(3, reponseCredit.getSolde_a_payer());
            st.setFloat(4, reponseCredit.getauto_financement());
            st.setDate(5, new java.sql.Date(reponseCredit.getDate_debut_paiement().getTime()));
            st.setInt(6, reponseCredit.getCredit_id());
            st.setInt(7, reponseCredit.getUser_id());
            st.executeUpdate();
            System.out.println("Réponse de crédit ajoutée avec succès !");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void Update(ReponseCredit reponseCredit) {
        if (reponseCredit.getNbr_mois_paiement() <= 0) {
            throw new IllegalArgumentException("Le nombre de mois de paiement doit être supérieur à zéro.");
        }
        String req = "UPDATE `reponse_credit` SET `nbr_mois_paiement`=?, `description`=?, `solde_a_payer`=?, `auto_financement`=?, `date_debut_paiement`=?, `credit_id`=? WHERE `id`=?";
        try {
            PreparedStatement st = cnx.prepareStatement(req);
            st.setInt(1, reponseCredit.getNbr_mois_paiement());
            st.setString(2, reponseCredit.getDescription());
            st.setFloat(3, reponseCredit.getSolde_a_payer());
            st.setFloat(4, reponseCredit.getauto_financement());
            st.setDate(5, new java.sql.Date(reponseCredit.getDate_debut_paiement().getTime()));
            st.setInt(6, reponseCredit.getCredit_id());
            st.setInt(7, reponseCredit.getId());
            int rowsUpdated = st.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("Réponse de crédit mise à jour avec succès !");
            } else {
                System.out.println("Aucune réponse de crédit mise à jour !");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void Delete(ReponseCredit reponseCredit) {
        String req = "DELETE FROM `reponse_credit` WHERE `id`=?";
        try {
            PreparedStatement st = cnx.prepareStatement(req);
            st.setInt(1, reponseCredit.getId());
            int rowsDeleted = st.executeUpdate();
            if (rowsDeleted > 0) {
                System.out.println("Réponse de crédit supprimée avec succès !");
            } else {
                System.out.println("Aucune réponse de crédit supprimée !");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<ReponseCredit> getAll() {
        List<ReponseCredit> reponseCredits = new ArrayList<>();
        String req = "SELECT rc.*, c.id AS credit_id FROM reponse_credit rc JOIN credit c ON rc.credit_id = c.id";

        try {
            PreparedStatement st = cnx.prepareStatement(req);
            ResultSet res = st.executeQuery();
            while (res.next()) {
                ReponseCredit reponseCredit = new ReponseCredit();
                reponseCredit.setId(res.getInt("id"));
                reponseCredit.setNbr_mois_paiement(res.getInt("nbr_mois_paiement"));
                reponseCredit.setDescription(res.getString("description"));
                reponseCredit.setSolde_a_payer(res.getFloat("solde_a_payer"));
                reponseCredit.setauto_financement(res.getFloat("auto_financement"));
                reponseCredit.setDate_debut_paiement(res.getDate("date_debut_paiement"));
                reponseCredit.setCredit_id(res.getInt("credit_id"));
                reponseCredit.setUser_id(res.getInt("user_id"));
                reponseCredits.add(reponseCredit);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return reponseCredits;
    }

    public ReponseCredit getOne(int id) {
        String req = "SELECT rc.*, c.id AS credit_id FROM reponse_credit rc JOIN credit c ON rc.credit_id = c.id WHERE rc.id = ?";
        ReponseCredit reponseCredit = null;
        try {
            PreparedStatement st = cnx.prepareStatement(req);
            st.setInt(1, id);
            ResultSet res = st.executeQuery();
            if (res.next()) {
                reponseCredit = new ReponseCredit();
                reponseCredit.setId(res.getInt("id"));
                reponseCredit.setNbr_mois_paiement(res.getInt("nbr_mois_paiement"));
                reponseCredit.setDescription(res.getString("description"));
                reponseCredit.setSolde_a_payer(res.getFloat("solde_a_payer"));
                reponseCredit.setauto_financement(res.getFloat("auto_financement"));
                reponseCredit.setDate_debut_paiement(res.getDate("date_debut_paiement"));
                reponseCredit.setCredit_id(res.getInt("credit_id"));
                reponseCredit.setUser_id(res.getInt("user_id"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return reponseCredit;
    }

    public boolean isTraite(int creditId) {
        String req = "SELECT * FROM reponse_credit WHERE credit_id = ?";
        try {
            PreparedStatement st = cnx.prepareStatement(req);
            st.setInt(1, creditId);
            ResultSet res = st.executeQuery();
            return res.next(); // Retourne vrai si une réponse de crédit est trouvée, sinon faux
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
