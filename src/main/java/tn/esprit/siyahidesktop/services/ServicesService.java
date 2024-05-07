package tn.esprit.siyahidesktop.services;

import tn.esprit.siyahidesktop.interfaces.IService;
import tn.esprit.siyahidesktop.models.Compte;
import tn.esprit.siyahidesktop.models.Service;
import tn.esprit.siyahidesktop.util.MaConnexion;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ServicesService implements IService<Service> {
    private Connection cnx;

    public ServicesService() {
        this.cnx = MaConnexion.getInstance().getCnx();
    }

    @Override
    public void add(Service service) {
        String req = "INSERT INTO service (name, description, frais, expiration) VALUES (?, ?, ?, ?)";
        try (PreparedStatement ps = cnx.prepareStatement(req)) {
            ps.setString(1, service.getNom());
            ps.setString(2, service.getDescription());
            ps.setDouble(3, service.getPricing());
            ps.setDate(4, java.sql.Date.valueOf(service.getExpiration_date()));
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Failed to add service: " + e.getMessage());
        }
    }

    @Override
    public void update(Service service) {
        String req = "UPDATE service SET name=?, description=?, frais=?, expiration=? WHERE id=?";
        try (PreparedStatement ps = cnx.prepareStatement(req)) {
            ps.setString(1, service.getNom());
            ps.setString(2, service.getDescription());
            ps.setDouble(3, service.getPricing());
            ps.setDate(4, java.sql.Date.valueOf(service.getExpiration_date()));
            ps.setInt(5, service.getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Failed to update service: " + e.getMessage());
        }
    }



    @Override
    public void delete(Service service) {
        String req = "DELETE FROM service WHERE id=?";
        try (PreparedStatement ps = cnx.prepareStatement(req)) {
            ps.setInt(1, service.getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Failed to delete service: " + e.getMessage());
        }
    }

    @Override
    public List<Service> getAll() {
        List<Service> services = new ArrayList<>();
        String query = "SELECT * FROM service"; // Adjust table name as necessary
        try (PreparedStatement ps = cnx.prepareStatement(query);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Service service = new Service();
                service.setId(rs.getInt("id"));
                service.setNom(rs.getString("name"));
                service.setDescription(rs.getString("description"));
                service.setPricing(rs.getDouble("frais"));
                services.add(service);
            }
        } catch (SQLException e) {
            System.err.println("Error fetching services: " + e.getMessage());
        }
        return services;
    }

    @Override
    public Service getOne(int id) {
        String req = "SELECT * FROM service WHERE id=?";
        try (PreparedStatement ps = cnx.prepareStatement(req)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return extractServiceFromResultSet(rs);
            }
            return null; // If service with given ID is not found
        } catch (SQLException e) {
            throw new RuntimeException("Failed to get service by ID: " + e.getMessage());
        }
    }

    private Service extractServiceFromResultSet(ResultSet rs) throws SQLException {
        Service service = new Service();
        service.setId(rs.getInt("id"));
        service.setNom(rs.getString("name"));
        service.setDescription(rs.getString("description"));
        service.setPricing(rs.getDouble("frais"));
        service.setExpiration_date(rs.getDate("expiration") != null ? rs.getDate("expiration").toLocalDate() : null);
        return service;
    }
}
