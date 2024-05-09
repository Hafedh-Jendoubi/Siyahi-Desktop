package Service;


import Entity.Demande_achat;
import Utils.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;



public class Demande_achatService implements IService<Demande_achat>{
        private Connection conn;

    public Demande_achatService()
        {
            conn = DataSource.getInstance().getCnx();
        }


    public void insert(Demande_achat demandeAchat) {
        String requete = "INSERT INTO demande_achat (user_id, achat_id, nom, prenom, date_demande, num_tel, type_paiement, cin, adresse, etatdemande) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try {
            PreparedStatement pst = conn.prepareStatement(requete);
            pst.setInt(1, demandeAchat.getUser_id());
            pst.setInt(2, demandeAchat.getAchat_id());
            pst.setString(3, demandeAchat.getNom());
            pst.setString(4, demandeAchat.getPrenom());
            pst.setDate(5, java.sql.Date.valueOf(demandeAchat.getDate_demande()));
            pst.setString(6, demandeAchat.getNum_tel());
            pst.setString(7, demandeAchat.getType_paiement());
            pst.setInt(8, demandeAchat.getCin());
            pst.setString(9, demandeAchat.getAdresse());
            pst.setString(10, demandeAchat.getEtatdemande());

            pst.executeUpdate();
            System.out.println("Demande d'achat ajoutée avec succès !");
        } catch (SQLException ex) {
            Logger.getLogger(Demande_achatService.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public List<Demande_achat> chercher(String searchText) throws SQLException {
        List<Demande_achat> results = new ArrayList<>();

        String query = "SELECT * FROM demande_achat WHERE nom LIKE ? OR prenom LIKE ? OR type_paiement LIKE ? OR cin LIKE ? ORDER BY nom, prenom, type_paiement, cin";
        try (PreparedStatement st = conn.prepareStatement(query)) {
            for (int i = 1; i <= 4; i++) {
                st.setString(i, "%" + searchText + "%");
            }

            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                Demande_achat ev = new Demande_achat();
                ev.setId(rs.getInt("id"));
                ev.setNom(rs.getString("nom"));
                ev.setPrenom(rs.getString("prenom"));
                ev.setType_paiement(rs.getString("type_paiement"));
                ev.setCin(rs.getInt("cin"));

                results.add(ev);
            }
        }

        return results;
    }

    public void update(Demande_achat demandeAchat) {
        String requete = "UPDATE demande_achat SET user_id=?, achat_id=?, nom=?, prenom=?, date_demande=?, num_tel=?, type_paiement=?, cin=?, adresse=?, etatdemande=? WHERE id=?";
        try {
            PreparedStatement pst = conn.prepareStatement(requete);
            pst.setInt(1, demandeAchat.getUser_id());
            pst.setInt(2, demandeAchat.getAchat_id());
            pst.setString(3, demandeAchat.getNom());
            pst.setString(4, demandeAchat.getPrenom());
            pst.setDate(5, java.sql.Date.valueOf(demandeAchat.getDate_demande()));
            pst.setString(6, demandeAchat.getNum_tel());
            pst.setString(7, demandeAchat.getType_paiement());
            pst.setInt(8, demandeAchat.getCin());
            pst.setString(9, demandeAchat.getAdresse());
            pst.setString(10, demandeAchat.getEtatdemande());
            pst.setInt(11, demandeAchat.getId());

            pst.executeUpdate();
            System.out.println("Demande d'achat mise à jour avec succès !");
        } catch (SQLException ex) {
            Logger.getLogger(Demande_achatService.class.getName()).log(Level.SEVERE, null, ex);
        }
    }


    public void delete(int id) {
        String requete = "DELETE FROM demande_achat WHERE id=?";
        try {
            PreparedStatement pst = conn.prepareStatement(requete);
            pst.setInt(1, id);
            pst.executeUpdate();
            System.out.println("Demande d'achat supprimée avec succès !");
        } catch (SQLException ex) {
            Logger.getLogger(Demande_achatService.class.getName()).log(Level.SEVERE, null, ex);
        }
    }


    public Demande_achat readById(int id) {
        String requete = "SELECT * FROM demande_achat WHERE id=?";
        Demande_achat demandeAchat = null;
        try {
            PreparedStatement pst = conn.prepareStatement(requete);
            pst.setInt(1, id);
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                demandeAchat = new Demande_achat();
                demandeAchat.setId(rs.getInt("id"));
                demandeAchat.setUser_id(rs.getInt("user_id"));
                demandeAchat.setAchat_id(rs.getInt("achat_id"));
                demandeAchat.setNom(rs.getString("nom"));
                demandeAchat.setPrenom(rs.getString("prenom"));
                demandeAchat.setDate_demande(rs.getDate("date_demande").toLocalDate());
                demandeAchat.setNum_tel(rs.getString("num_tel"));
                demandeAchat.setType_paiement(rs.getString("type_paiement"));
                demandeAchat.setCin(rs.getInt("cin"));
                demandeAchat.setAdresse(rs.getString("adresse"));
                demandeAchat.setEtatdemande(rs.getString("etatdemande"));
            }
        } catch (SQLException ex) {
            Logger.getLogger(Demande_achatService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return demandeAchat;
    }


    public ArrayList<Demande_achat> readAll() {
        String requete = "SELECT * FROM demande_achat";
        ArrayList<Demande_achat> demandes = new ArrayList<>();
        try {
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(requete);
            while (rs.next()) {
                Demande_achat demandeAchat = new Demande_achat();
                demandeAchat.setId(rs.getInt("id"));
                demandeAchat.setUser_id(rs.getInt("user_id"));
                demandeAchat.setAchat_id(rs.getInt("achat_id"));
                demandeAchat.setNom(rs.getString("nom"));
                demandeAchat.setPrenom(rs.getString("prenom"));
                demandeAchat.setDate_demande(rs.getDate("date_demande").toLocalDate());
                demandeAchat.setNum_tel(rs.getString("num_tel"));
                demandeAchat.setType_paiement(rs.getString("type_paiement"));
                demandeAchat.setCin(rs.getInt("cin"));
                demandeAchat.setAdresse(rs.getString("adresse"));
                demandeAchat.setEtatdemande(rs.getString("etatdemande"));
                demandes.add(demandeAchat);
            }
        } catch (SQLException ex) {
            Logger.getLogger(Demande_achatService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return demandes;
    }

}
