package tn.esprit.services;

import tn.esprit.interfaces.IService;
import tn.esprit.models.Transaction;
import tn.esprit.util.MaConnexion;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TransactionService implements IService<Transaction> {
    //att
    Connection cnx = MaConnexion.getInstance().getCnx();
    final String username = "2e4ddfacdadc07";
    final String password = "08aa0241ca491b";

    //actions
    @Override
    public void add(Transaction transaction) {
        String req = "INSERT INTO `transaction`(`ribSent`, `ribRec`, `cash`, `date`) VALUES (?, ?, ?, ?)";
        String req1 = "UPDATE `compte_client` SET `solde` = `solde` - ? WHERE `rib` = ?";
        String req2 = "UPDATE `compte_client` SET `solde` = `solde` + ? WHERE `rib` = ?";
        try{
            PreparedStatement ps = cnx.prepareStatement(req);
            ps.setLong(1, transaction.getRibUserSent());
            ps.setLong(2, transaction.getRibUserReceived());
            ps.setDouble(3, transaction.getCash());
            ps.setDate(4, transaction.getDate());
            ps.executeUpdate();

            PreparedStatement ps1 = cnx.prepareStatement(req1);
            ps1.setDouble(1, transaction.getCash());
            ps1.setLong(2, transaction.getRibUserSent());
            ps1.executeUpdate();

            PreparedStatement ps2 = cnx.prepareStatement(req2);
            ps2.setDouble(1, transaction.getCash());
            ps2.setLong(2, transaction.getRibUserReceived());
            ps2.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void update(Transaction transaction) {
        String req = "UPDATE `transaction` SET " +
                "`ribSent`='" + transaction.getRibUserSent() +
                "',`ribRec`='" + transaction.getRibUserReceived() +
                "',`cash`='" + transaction.getCash() +
                "',`date`='" + transaction.getDate() +
                "' WHERE `id`=" + transaction.getId();
        try {
            Statement st = cnx.createStatement();
            int rowsUpdated = st.executeUpdate(req);
            if (rowsUpdated > 0) {
                System.out.println("transaction updated successfully");
            } else {
                System.out.println("Failed to update transaction");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void delete(Transaction transaction) {
        String req = "DELETE FROM `transaction` WHERE id = ?";
        try {
            PreparedStatement ps = cnx.prepareStatement(req);
            ps.setInt(1, transaction.getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Transaction> getAll() {
        List<Transaction> transactions = new ArrayList<>();
        String req = "SELECT * FROM `transaction`";
        try {
            Statement st = cnx.createStatement();
            ResultSet res = st.executeQuery(req);
            while (res.next()){
                Transaction transaction = new Transaction();
                transaction.setId(res.getInt(1));
                transaction.setRibUserSent(res.getLong(2));
                transaction.setRibUserReceived(res.getLong(3));
                transaction.setCash(res.getDouble(4));
                transaction.setDate(res.getDate(5));
                transactions.add(transaction);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return transactions;
    }

    @Override
    public Transaction getOneByID(int id) {
        Transaction transaction = new Transaction();
        try {
            String req = "SELECT * FROM `transaction` WHERE `id`=?";
            PreparedStatement ps = cnx.prepareStatement(req);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                transaction.setId(id);
                transaction.setRibUserSent(rs.getInt(2));
                transaction.setRibUserReceived(rs.getInt(3));
                transaction.setCash(rs.getFloat(4));
                transaction.setDate(rs.getDate(5));
            }
        } catch (SQLException ex) {
            System.out.println("Failed to get transaction: " + ex.getMessage());
        }
        return transaction;
    }

    public List<Long> getOwnerOfRIBs(int id) {
        List<Long> ListRIB = new ArrayList<>();
        try {
            String req = "SELECT `rib` FROM `compte_client` WHERE `user_id`=?";
            PreparedStatement ps = cnx.prepareStatement(req);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                ListRIB.add(rs.getLong(1));
            }
        } catch (SQLException ex) {
            System.out.println("Failed to get RIB: " + ex.getMessage());
        }

        return ListRIB;
    }
}
