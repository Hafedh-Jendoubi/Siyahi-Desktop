package Service;

import Entity.Achat;
import Utils.DataSource;

import java.sql.*;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class AchatService implements IService<Achat>{
    private Connection conn;

    public AchatService()
    {
        conn = DataSource.getInstance().getCnx();
    }

    @Override
    public void insert(Achat o) {
        String requete = "INSERT INTO achat (image, type, description) VALUES (?, ?, ?)";

        try {
            PreparedStatement pst = conn.prepareStatement(requete);
            pst.setString(1, o.getImage());
            pst.setString(2, o.getType());
            pst.setString(3, o.getDescription());

            pst.executeUpdate();
            System.out.println("Achat ajouté avec succès");
        } catch (SQLException ex) {
            Logger.getLogger(AchatService.class.getName()).log(Level.SEVERE, null, ex);
        }
    }


    @Override
    public void update(Achat o) {
        String requete = "UPDATE achat SET image=?, type=?, description=? WHERE id=?";
        try {
            PreparedStatement pst = conn.prepareStatement(requete);
            pst.setString(1, o.getImage());
            pst.setString(2, o.getType());
            pst.setString(3, o.getDescription());
            pst.setInt(4, o.getId());

            pst.executeUpdate();
            System.out.println("Achat mis à jour avec succès !");
        } catch (SQLException ex) {
            Logger.getLogger(AchatService.class.getName()).log(Level.SEVERE, null, ex);
        }
    }


    @Override
    public void delete(int id) {
        String requete = "DELETE FROM achat WHERE id=?";
        try {
            PreparedStatement pst = conn.prepareStatement(requete);
            pst.setInt(1, id);
            pst.executeUpdate();
            System.out.println("Achat supprimé avec succès !");
        } catch (SQLException ex) {
            Logger.getLogger(AchatService.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public Achat readById(int id) {
        String requete = "SELECT * FROM achat WHERE id=?";
        Achat achat = null;
        try {
            PreparedStatement pst = conn.prepareStatement(requete);
            pst.setInt(1, id);
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                achat = new Achat(
                        rs.getString("image"),
                        rs.getString("type"),
                        rs.getString("description")
                );
                achat.setId(rs.getInt("id")); // Assuming you have setId method in your Achat class
            }
        } catch (SQLException ex) {
            Logger.getLogger(AchatService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return achat;
    }


    @Override
    public ArrayList<Achat> readAll() {
        String requete = "SELECT * FROM achat";
        ArrayList<Achat> list = new ArrayList<>();
        try {
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(requete);
            while (rs.next()) {
                Achat achat = new Achat(
                        rs.getString("image"),
                        rs.getString("type"),
                        rs.getString("description")
                );
                achat.setId(rs.getInt("id")); // Assuming you have setId method in your Achat class
                list.add(achat);
            }
        } catch (SQLException ex) {
            Logger.getLogger(AchatService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }

}
