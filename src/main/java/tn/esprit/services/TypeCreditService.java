package tn.esprit.services;

import tn.esprit.interfaces.CRService;
import tn.esprit.models.TypeCredit;
import tn.esprit.interfaces.IService;
import tn.esprit.util.MaConnexion;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TypeCreditService implements CRService<TypeCredit> {
    //attributes
    Connection cnx = MaConnexion.getInstance().getCnx();

    //actions
    @Override
    public void Add(TypeCredit typeCredit) {
        String req = "INSERT INTO `type_credit`(`nom_type_credit`, `taux_crédit_direct`) VALUES (?, ?)";
        try {
            PreparedStatement st = cnx.prepareStatement(req);
            st.setString(1, typeCredit.getNomTypeCredit());
            st.setFloat(2, typeCredit.getTauxCreditDirect());
            st.executeUpdate();
            System.out.println("Type de crédit ajouté avec succès !");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void Update(TypeCredit typeCredit) {
        String req = "UPDATE `type_credit` SET `nom_type_credit`=?, `taux_crédit_direct`=? WHERE `id`=?";
        try {
            PreparedStatement st = cnx.prepareStatement(req);
            st.setString(1, typeCredit.getNomTypeCredit());
            st.setFloat(2, typeCredit.getTauxCreditDirect());
            st.setInt(3, typeCredit.getId());
            int rowsUpdated = st.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("Type de crédit mis à jour avec succès !");
            } else {
                System.out.println("Aucun type de crédit mis à jour !");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    @Override
    public void Delete(TypeCredit typeCredit) {
        String req = "DELETE FROM `type_credit` WHERE `id`=?";
        try {
            PreparedStatement st = cnx.prepareStatement(req);
            st.setInt(1, typeCredit.getId());
            int rowsDeleted = st.executeUpdate();
            if (rowsDeleted > 0) {
                System.out.println("Type de crédit supprimé avec succès !");
            } else {
                System.out.println("Aucun type de crédit supprimé !");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<TypeCredit> getAll() {
        List<TypeCredit> typeCredits = new ArrayList<>();
        String req = "SELECT * FROM type_credit";

        try (Statement st = cnx.createStatement();
             ResultSet res = st.executeQuery(req)) {
            while (res.next()) {
                TypeCredit typeCredit = new TypeCredit();
                typeCredit.setId(res.getInt("id"));
                typeCredit.setNomTypeCredit(res.getString("nom_type_credit"));
                typeCredit.setTauxCreditDirect(res.getFloat("taux_crédit_direct"));

                typeCredits.add(typeCredit);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return typeCredits;
    }

    @Override
    public TypeCredit getOne(int id) {
        String req = "SELECT * FROM type_credit WHERE id = ?";
        TypeCredit typeCredit = null;
        try (PreparedStatement st = cnx.prepareStatement(req)) {
            st.setInt(1, id);
            try (ResultSet res = st.executeQuery()) {
                if (res.next()) {
                    typeCredit = new TypeCredit();
                    typeCredit.setId(res.getInt("id"));
                    typeCredit.setNomTypeCredit(res.getString("nom_type_credit"));
                    typeCredit.setTauxCreditDirect(res.getFloat("taux_crédit_direct"));

                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return typeCredit;
    }
}
