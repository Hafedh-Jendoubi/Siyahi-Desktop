package tn.esprit.services;

import tn.esprit.models.Credit;
import tn.esprit.interfaces.IService;
import tn.esprit.util.MaConnexion;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CreditService implements IService<Credit> {
    //attributes
    Connection cnx = MaConnexion.getInstance().getCnx();

    //actions
    @Override
    public void Add(Credit credit) {
        if (credit.getNbr_mois_paiement() <= 0) {
            throw new IllegalArgumentException("Le nombre de mois de paiement doit être supérieur à zéro.");
        }
        String req = "INSERT INTO `credit`(`solde_demande`, `date_debut_paiement`, `nbr_mois_paiement`, `description`, `contrat`) VALUES (" + credit.getSolde_demande() + ",'" + credit.getDate_debut_paiement() + "'," + credit.getNbr_mois_paiement() + ",'" + credit.getDescription() + "','" + credit.getContrat() + "')";
        try {
            Statement st = cnx.createStatement();
            st.executeUpdate(req);
            System.out.println("Credit added successfully");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void Insert(Credit credit) {
        if (credit.getNbr_mois_paiement() <= 0) {
            throw new IllegalArgumentException("Le nombre de mois de paiement doit être supérieur à zéro.");
        }

        String req = "INSERT INTO `credit`(`solde_demande`, `date_debut_paiement`, `nbr_mois_paiement`, `description`, `contrat`) VALUES (?, ?, ?, ?, ?)";
        try {
            PreparedStatement st = cnx.prepareStatement(req);
            st.setDouble(1, credit.getSolde_demande());
            st.setDate(2, new java.sql.Date(credit.getDate_debut_paiement().getTime()));
            st.setInt(3, credit.getNbr_mois_paiement());
            st.setString(4, credit.getDescription());
            st.setString(5, credit.getContrat());
            st.executeUpdate();
            System.out.println("Crédit ajouté avec succès !");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    @Override
    public void Update(Credit credit) {
        if (credit.getNbr_mois_paiement() <= 0) {
            throw new IllegalArgumentException("Le nombre de mois de paiement doit être supérieur à zéro.");
        }
        String req = "UPDATE `credit` SET `solde_demande`=?, `date_debut_paiement`=?, `nbr_mois_paiement`=?, `description`=?, `contrat`=? WHERE `id`=?";
        try {
            PreparedStatement st = cnx.prepareStatement(req);
            st.setDouble(1, credit.getSolde_demande());
            st.setDate(2, new java.sql.Date(credit.getDate_debut_paiement().getTime()));
            st.setInt(3, credit.getNbr_mois_paiement());
            st.setString(4, credit.getDescription());
            st.setString(5, credit.getContrat());
            st.setInt(6, credit.getId()); // Assuming Credit class has an id field
            int rowsUpdated = st.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("Crédit mis à jour avec succès !");
            } else {
                System.out.println("Aucun crédit mis à jour !");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void Delete(Credit credit) {
        String req = "DELETE FROM `credit` WHERE `id`=?";
        try {
            PreparedStatement st = cnx.prepareStatement(req);
            st.setInt(1, credit.getId()); // Assuming Credit class has an id field
            int rowsDeleted = st.executeUpdate();
            if (rowsDeleted > 0) {
                System.out.println("Crédit supprimé avec succès !");
            } else {
                System.out.println("Aucun crédit supprimé !");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Credit> getAll() {
        List<Credit> credits = new ArrayList<>();
        String req = "SELECT * FROM Credit";

        Statement st = null;
        try {
            st = cnx.createStatement();
            ResultSet res = st.executeQuery(req);
            while (res.next()) {
                Credit credit = new Credit();
                credit.setId(res.getInt("id"));
                credit.setSolde_demande(res.getFloat("solde_demande"));
                credit.setDate_debut_paiement(res.getDate("date_debut_paiement"));
                credit.setNbr_mois_paiement(res.getInt("nbr_mois_paiement"));
                credit.setDescription(res.getString("description"));
                credit.setContrat(res.getString("contrat"));

                credits.add(credit);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return credits;
    }

    @Override
    public Credit getOne(int id) {
        String req = "SELECT * FROM credit WHERE id = ?";
        Credit credit = null;
        try {
            PreparedStatement st = cnx.prepareStatement(req);
            st.setInt(1, id);
            ResultSet res = st.executeQuery();
            if (res.next()) {
                credit = new Credit();
                credit.setId(res.getInt("id"));
                credit.setSolde_demande(res.getFloat("solde_demande"));
                credit.setDate_debut_paiement(res.getDate("date_debut_paiement"));
                credit.setNbr_mois_paiement(res.getInt("nbr_mois_paiement"));
                credit.setDescription(res.getString("description"));
                credit.setContrat(res.getString("contrat"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return credit;
    }


}