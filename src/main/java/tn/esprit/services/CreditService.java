package tn.esprit.services;

import tn.esprit.interfaces.CRService;
import tn.esprit.models.Credit;
import tn.esprit.interfaces.IService;
import tn.esprit.util.MaConnexion;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CreditService implements CRService<Credit> {
    //attributes
    Connection cnx = MaConnexion.getInstance().getCnx();

    //actions
    @Override
    public void Add(Credit credit) {
        if (credit.getNbr_mois_paiement() <= 0) {
            throw new IllegalArgumentException("Le nombre de mois de paiement doit être supérieur à zéro.");
        }
        String req = "INSERT INTO `credit`(`user_id`, `solde_demande`, `date_debut_paiement`, `nbr_mois_paiement`, `description`, `contrat`, `type_credit_id`) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try {
            PreparedStatement st = cnx.prepareStatement(req);
            st.setInt(1, credit.getUser_id());
            st.setDouble(2, credit.getSolde_demande());
            st.setDate(3, new java.sql.Date(credit.getDate_debut_paiement().getTime()));
            st.setInt(4, credit.getNbr_mois_paiement());
            st.setString(5, credit.getDescription());
            st.setString(6, credit.getContrat());
            st.setInt(7, credit.getType_credit_id()); // Assuming Credit class has a type_credit_id field
            st.executeUpdate();
            System.out.println("Credit added successfully");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void Update(Credit credit) {
        if (credit.getNbr_mois_paiement() <= 0) {
            throw new IllegalArgumentException("Le nombre de mois de paiement doit être supérieur à zéro.");
        }
        String req = "UPDATE `credit` SET `solde_demande`=?, `date_debut_paiement`=?, `nbr_mois_paiement`=?, `description`=?, `contrat`=?, `type_credit_id`=? WHERE `id`=?";
        try {
            PreparedStatement st = cnx.prepareStatement(req);
            st.setDouble(1, credit.getSolde_demande());
            st.setDate(2, new java.sql.Date(credit.getDate_debut_paiement().getTime()));
            st.setInt(3, credit.getNbr_mois_paiement());
            st.setString(4, credit.getDescription());
            st.setString(5, credit.getContrat());
            st.setInt(6, credit.getType_credit_id()); // Assuming Credit class has a type_credit_id field
            st.setInt(7, credit.getId()); // Assuming Credit class has an id field
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
        String req = "SELECT c.id, c.user_id, c.solde_demande, c.date_debut_paiement, c.nbr_mois_paiement, c.description, c.contrat, c.type_credit_id ,tc.nom_type_credit  FROM credit c  JOIN type_credit tc ON c.type_credit_id = tc.id";

        Statement st = null;
        try {
            st = cnx.createStatement();
            ResultSet res = st.executeQuery(req);
            while (res.next()) {
                Credit credit = new Credit();
                credit.setId(res.getInt("id"));
                credit.setUser_id(res.getInt("user_id"));
                credit.setSolde_demande(res.getFloat("solde_demande"));
                credit.setDate_debut_paiement(res.getDate("date_debut_paiement"));
                credit.setNbr_mois_paiement(res.getInt("nbr_mois_paiement"));
                credit.setDescription(res.getString("description"));
                credit.setContrat(res.getString("contrat"));
                credit.setType_credit_id(res.getInt("type_credit_id")); // Assuming Credit class has a type_credit_id field
                credit.setType_credit_nom(res.getString("nom_type_credit")); // Assuming Credit class has a type_credit_nom field

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
                credit.setUser_id(res.getInt("user_id"));
                credit.setSolde_demande(res.getFloat("solde_demande"));
                credit.setDate_debut_paiement(res.getDate("date_debut_paiement"));
                credit.setNbr_mois_paiement(res.getInt("nbr_mois_paiement"));
                credit.setDescription(res.getString("description"));
                credit.setContrat(res.getString("contrat"));
                credit.setType_credit_id(res.getInt("type_credit_id")); // Assuming Credit class has a type_credit_id field
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return credit;
    }

    public List<Credit> getCredits(int id) {
        List<Credit> Credits = new ArrayList<>();
        try {
            String req = "SELECT c.id, c.user_id, c.solde_demande, c.date_debut_paiement, c.nbr_mois_paiement, c.description, c.contrat, c.type_credit_id ,tc.nom_type_credit  FROM credit c  JOIN type_credit tc ON c.type_credit_id = tc.id WHERE c.user_id = ?";
            PreparedStatement ps = cnx.prepareStatement(req);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                Credit credit = new Credit();
                credit.setId(rs.getInt("id"));
                credit.setUser_id(rs.getInt("user_id"));
                credit.setSolde_demande(rs.getFloat("solde_demande"));
                credit.setDate_debut_paiement(rs.getDate("date_debut_paiement"));
                credit.setNbr_mois_paiement(rs.getInt("nbr_mois_paiement"));
                credit.setDescription(rs.getString("description"));
                credit.setContrat(rs.getString("contrat"));
                credit.setType_credit_id(rs.getInt("type_credit_id")); // Assuming Credit class has a type_credit_id field
                credit.setType_credit_nom(rs.getString("nom_type_credit")); // Assuming Credit class has a type_credit_nom field

                Credits.add(credit);
            }
        } catch (SQLException ex) {
            System.out.println("Failed to get Credit: " + ex.getMessage());
        }

        return Credits;
    }
}
