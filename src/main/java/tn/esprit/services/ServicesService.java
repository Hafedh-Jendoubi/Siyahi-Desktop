package tn.esprit.services;

import tn.esprit.models.Service;
import tn.esprit.util.MaConnexion;
import tn.esprit.interfaces.IService;



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
        String req = "INSERT INTO service (name, description) VALUES (?, ?)";
        try {
            PreparedStatement ps = cnx.prepareStatement(req);
            ps.setString(1, service.getNom());
            ps.setString(2, service.getDescription());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Failed to add service: " + e.getMessage());
        }
    }
    @Override
    public void update(Service service) {
        String req = "UPDATE service SET name=?, description=? WHERE id=?";
        try {
            PreparedStatement ps = cnx.prepareStatement(req);
            ps.setString(1, service.getNom());
            ps.setString(2, service.getDescription());
            ps.setInt(3, service.getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Failed to update service: " + e.getMessage());
        }
    }
    @Override
    public void delete(Service service) {
        String req = "DELETE FROM service WHERE id=?";
        try {
            PreparedStatement ps = cnx.prepareStatement(req);
            ps.setInt(1, service.getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Failed to delete service: " + e.getMessage());
        }
    }
    @Override
    public Service getOne(int id)
    {
        return null;
    }

    @Override
    public List<Service> getAll() {
        List<Service> services = new ArrayList<>();
        String req = "SELECT * FROM service";
        try {
            PreparedStatement ps = cnx.prepareStatement(req);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Service service = new Service();
                service.setId(rs.getInt("id"));
                service.setNom(rs.getString("name"));
                service.setDescription(rs.getString("description"));
                services.add(service);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Failed to get all services: " + e.getMessage());
        }
        return services;
    }

    public Service getById(int id) {
        String req = "SELECT * FROM service WHERE id=?";
        try {
            PreparedStatement ps = cnx.prepareStatement(req);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                Service service = new Service();
                service.setId(rs.getInt("id"));
                service.setNom(rs.getString("name"));
                service.setDescription(rs.getString("description"));
                return service;
            }
            return null; // If service with given ID is not found
        } catch (SQLException e) {
            throw new RuntimeException("Failed to get service by ID: " + e.getMessage());
        }
    }
}
