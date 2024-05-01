package tn.esprit.services;

import tn.esprit.interfaces.IService;
import tn.esprit.models.ObjetReclamation;
import tn.esprit.models.Reclamation;
import tn.esprit.util.MaConnexion;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ReclamationService implements IService<Reclamation> {

    //ATT
    Connection cnx = MaConnexion.getInstance().getCnx();
    private List<Reclamation> reclamations;

    //act

    // ...

    private static ReclamationService instance;
    public ReclamationService() {
        // Private constructor to prevent direct instantiation
    }

    public static ReclamationService getInstance() {
        if (instance == null) {
            instance = new ReclamationService();
        }
        return instance;
    }



    @Override
    public void add(Reclamation reclamation) {
        String req = "INSERT INTO reclamation (Object, Description, Date_Creation, auteur,Email) VALUES (?, ?, ?, ?,?)";

// Assuming you have a PreparedStatement object named 'ps' and a Reclamation object named 'reclamation'

        try {
            PreparedStatement ps = cnx.prepareStatement(req);
            ObjetReclamation objReclamation = reclamation.getObject();



            ps.setString(1, objReclamation.getNom());
            ps.setString(2, reclamation.getDescription());
            ps.setTimestamp(3, reclamation.getDate_creation());
            ps.setString(4, reclamation.getAuteur());
            ps.setString(5, reclamation.getEmail());


            // Execute the statement
            ps.executeUpdate();
            System.out.println("reclamation added successfully");

        } catch (SQLException e) {
            e.printStackTrace();
            // Handle exceptions appropriately
        }
    }

        @Override
    public void update(Reclamation reclamation) {

            String req = "UPDATE reclamation SET Object = ?, Description = ?, Date_Creation = ?, Auteur = ?, Email = ? WHERE id = ?";

            try {
                PreparedStatement ps = cnx.prepareStatement(req);
                ObjetReclamation objReclamation = reclamation.getObject();


                ps.setString(1, objReclamation.getNom());
                ps.setString(2, reclamation.getDescription());
                ps.setTimestamp(3, reclamation.getDate_creation());
                ps.setString(4, reclamation.getAuteur());
                ps.setString(5, reclamation.getEmail());
                ps.setInt(6, reclamation.getId());

                int rowsUpdated = ps.executeUpdate();
                if (rowsUpdated > 0) {
                    System.out.println("Reclamation updated successfully");
                } else {
                    System.out.println("No reclamation found with ID: " + reclamation.getId());
                }
            } catch (SQLException e) {
                e.printStackTrace();
                // Handle exceptions appropriately
            }
        }

    // ...


    @Override
    public void delete(Reclamation reclamation) {
        String req = "DELETE FROM reclamation WHERE id = ?";

        try {
            PreparedStatement ps = cnx.prepareStatement(req);

            ps.setInt(1, reclamation.getId());

            int rowsDeleted = ps.executeUpdate();
            if (rowsDeleted > 0) {
                System.out.println("Reclamation deleted successfully");
            } else {
                System.out.println("No reclamation found with ID: " + reclamation.getId());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
    // Méthode pour obtenir l'objet Reclamation à partir de son nom
    private ObjetReclamation getObjectReclamationByName(String objectName) {
        List<ObjetReclamation> objetsSuggérés = Reclamation.getObjetsSuggérés();
        for (ObjetReclamation obj : objetsSuggérés) {
            if (obj.getNom().equals(objectName)) {
                return obj;
            }
        }
        return null;
    }

    @Override
    public List<Reclamation> getAll() {
        List<Reclamation> reclamations = new ArrayList<>();

        String req = "SELECT * FROM reclamation";

        try {
            PreparedStatement ps = cnx.prepareStatement(req);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("id");
                String objectName = rs.getString("Object");
                String description = rs.getString("Description");
                Timestamp dateCreation = rs.getTimestamp("Date_Creation");
                String auteur = rs.getString("auteur");
                String email = rs.getString("Email");
                boolean status = rs.getBoolean("status");

                // Obtenez l'objet Reclamation à partir de son nom
                ObjetReclamation objReclamation = getObjectReclamationByName(objectName);

                Reclamation reclamation = new Reclamation(id,objReclamation, description, dateCreation, auteur, email, status);
                reclamations.add(reclamation);
            }

            rs.close();
            ps.close();
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle exceptions appropriately
        }

        return reclamations;
    }


    @Override
    public Reclamation getOne(int id) {
        for (Reclamation reclamation : reclamations) {
            if (reclamation.getId() == id) {
                return reclamation;
            }
        }
        return null;
    }
}
