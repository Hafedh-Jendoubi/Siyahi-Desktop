package tn.esprit.siyahidesktop.services;

import tn.esprit.siyahidesktop.interfaces.IService;
import tn.esprit.siyahidesktop.models.Compte;
import tn.esprit.siyahidesktop.models.Service;
import tn.esprit.siyahidesktop.models.User;
import tn.esprit.siyahidesktop.util.MaConnexion;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CompteService implements IService<Compte> {
    Timestamp timestamp = new Timestamp(System.currentTimeMillis());
    private Connection cnx = MaConnexion.getInstance().getCnx();

    // Actions
    @Override
    public void add(Compte compte) throws SQLException {
        String sql = "INSERT INTO compte_client (rib, created_at, service_id, solde, user_id) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement pstmt = cnx.prepareStatement(sql)) {
            pstmt.setLong(1, compte.getRib());
            pstmt.setTimestamp(2, compte.getDate_creation());
            pstmt.setInt(3, compte.getService().getId());
            pstmt.setDouble(4, compte.getSolde());
            pstmt.setInt(5, compte.getUser().getId());  // Include the user ID in the query
            pstmt.executeUpdate();
        }
    }

    @Override
    public void update(Compte compte) {
        String req = "UPDATE `compte_client` SET " +
                "`service_id`=?, `solde`=?, /*`user_id`=?*/ WHERE `id`=?";

        try {
            PreparedStatement ps = cnx.prepareStatement(req);
            ps.setInt(1, compte.getService().getId());
            ps.setDouble(2, compte.getSolde());
            ps.setInt(3, compte.getUser().getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void delete(Compte compte) {
        // Database connection and SQL logic to delete a compte
        try {
            String sql = "DELETE FROM compte_client WHERE id = ?";
            PreparedStatement statement = cnx.prepareStatement(sql);
            statement.setInt(1, compte.getId());
            int rowsDeleted = statement.executeUpdate();
            if (rowsDeleted > 0) {
                System.out.println("A compte was deleted successfully!");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Error deleting compte: " + e.getMessage());
        }
    }

    private Compte getInformation(ResultSet res) {
        Compte compte = new Compte();
        try {
            UserService us = new UserService();
            compte.setRib(res.getLong("rib"));
            compte.setId(res.getInt("id"));
            User user = us.getOne(res.getInt("user_id"));
            compte.setUser(user);
            compte.setDate_creation(res.getTimestamp("created_at"));
            // Handling Service
            Service service = new Service();
            service.setId(res.getInt("service_id"));
            compte.setService(service);
            // Assuming the rest of the attributes are for User
            compte.setSolde(res.getDouble("solde"));
            // Assuming User is handled similarly to Service
            /*User user = new User();
            user.setId(res.getInt("user_id"));
            compte.setUser(user);*/
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return compte;
    }

    @Override
    public List<Compte> getAll() {
        List<Compte> comptes = new ArrayList<>();
        String req = "SELECT * FROM `compte_client`";
        try {
            Statement st = cnx.createStatement();
            ResultSet res = st.executeQuery(req);
            while (res.next()) {
                Compte compte = getInformation(res);
                comptes.add(compte);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return comptes;
    }

    @Override
    public Compte getOne(int rib) {
        Compte compte = new Compte();
        try {
            String req = "SELECT * FROM `compte_client` WHERE `rib`=?";
            PreparedStatement statement = cnx.prepareStatement(req);
            statement.setLong(1, rib);
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                compte = getInformation(rs);
            }
        } catch (SQLException ex) {
            System.out.println("Failed to get compte: " + ex.getMessage());
        }

        return compte;
    }

    public Compte getOneByRib(long rib) {
        Compte compte = null;
        String req = "SELECT * FROM `compte_client` WHERE `rib`=?";
        try {
            PreparedStatement statement = cnx.prepareStatement(req);
            statement.setLong(1, rib);
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                compte = getInformation(rs);
            }
        } catch (SQLException ex) {
            throw new RuntimeException("Failed to get compte by RIB: " + ex.getMessage());
        }
        return compte;
    }

    public List<Compte> getManyByService(String service) {
        List<Compte> comptes = new ArrayList<>();
        String req = "SELECT * FROM `compte_client` INNER JOIN `service` ON compte_client.service_id = service.id WHERE service.nom=?";
        try {
            PreparedStatement statement = cnx.prepareStatement(req);
            statement.setString(1, service);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                Compte compte = getInformation(rs);
                comptes.add(compte);
            }
        } catch (SQLException ex) {
            throw new RuntimeException("Failed to get comptes by service: " + ex.getMessage());
        }
        return comptes;
    }


}
