package tn.esprit.services;

import tn.esprit.models.ReponseCredit;
import tn.esprit.interfaces.IService;
import tn.esprit.util.MaConnexion;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ReponseCreditService implements IService<ReponseCredit> {
    //attributes
    Connection cnx = MaConnexion.getInstance().getCnx();

    //actions
    @Override
    public void Add(ReponseCredit reponseCredit) {
        if (reponseCredit.getNbr_mois_paiement() <= 0) {
            throw new IllegalArgumentException("Le nombre de mois de paiement doit être supérieur à zéro.");
        }
        String req = "INSERT INTO `reponse_credit`(`nbr_mois_paiement`, `description`, `solde_a_payer`, `date_debut_paiement`) VALUES (?, ?, ?, ?)";
        try {
            PreparedStatement st = cnx.prepareStatement(req);
            st.setInt(1, reponseCredit.getNbr_mois_paiement());
            st.setString(2, reponseCredit.getDescription());
            st.setFloat(3, reponseCredit.getSolde_a_payer());
            st.setDate(4, new java.sql.Date(reponseCredit.getDate_debut_paiement().getTime()));
            st.executeUpdate();
            System.out.println("Réponse de crédit ajoutée avec succès !");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void Update(ReponseCredit reponseCredit) {
        if (reponseCredit.getNbr_mois_paiement() <= 0) {
            throw new IllegalArgumentException("Le nombre de mois de paiement doit être supérieur à zéro.");
        }
        String req = "UPDATE `reponse_credit` SET `nbr_mois_paiement`=?, `description`=?, `solde_a_payer`=?, `date_debut_paiement`=? WHERE `id`=?";
        try {
            PreparedStatement st = cnx.prepareStatement(req);
            st.setInt(1, reponseCredit.getNbr_mois_paiement());
            st.setString(2, reponseCredit.getDescription());
            st.setFloat(3, reponseCredit.getSolde_a_payer());
            st.setDate(4, new java.sql.Date(reponseCredit.getDate_debut_paiement().getTime()));
            st.setInt(5, reponseCredit.getId()); // Assuming ReponseCredit class has an id field
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

    @Override
    public void Delete(ReponseCredit reponseCredit) {
        String req = "DELETE FROM `reponse_credit` WHERE `id`=?";
        try {
            PreparedStatement st = cnx.prepareStatement(req);
            st.setInt(1, reponseCredit.getId()); // Assuming ReponseCredit class has an id field
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

    @Override
    public List<ReponseCredit> getAll() {
        List<ReponseCredit> reponseCredits = new ArrayList<>();
        String req = "SELECT * FROM reponse_credit";

        try {
            PreparedStatement st = cnx.prepareStatement(req);
            ResultSet res = st.executeQuery();
            while (res.next()) {
                ReponseCredit reponseCredit = new ReponseCredit();
                reponseCredit.setId(res.getInt("id"));
                reponseCredit.setNbr_mois_paiement(res.getInt("nbr_mois_paiement"));
                reponseCredit.setDescription(res.getString("description"));
                reponseCredit.setSolde_a_payer(res.getFloat("solde_a_payer"));
                reponseCredit.setDate_debut_paiement(res.getDate("date_debut_paiement"));
                reponseCredits.add(reponseCredit);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return reponseCredits;
    }

    @Override
    public ReponseCredit getOne(int id) {
        String req = "SELECT * FROM reponse_credit WHERE id = ?";
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
                reponseCredit.setDate_debut_paiement(res.getDate("date_debut_paiement"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return reponseCredit;
    }
}
