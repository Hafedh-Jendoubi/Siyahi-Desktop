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
        String req = "INSERT INTO `transaction`(`userSent`, `userReceived`, `cash`, `date`) VALUES (?, ?, ?, ?)";
        try{
            PreparedStatement ps = cnx.prepareStatement(req);
            ps.setInt(1, transaction.getIdUserSent());
            ps.setInt(2, transaction.getIdUserReceived());
            ps.setFloat(3, transaction.getCash());
            ps.setDate(4, transaction.getDate());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void update(Transaction transaction) {
        String req = "UPDATE `transaction` SET " +
                "`userSent`='" + transaction.getIdUserSent() +
                "',`userReceived`='" + transaction.getIdUserReceived() +
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
                transaction.setIdUserSent(res.getInt(2));
                transaction.setIdUserReceived(res.getInt(3));
                transaction.setCash(res.getFloat(4));
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
                transaction.setIdUserSent(rs.getInt(2));
                transaction.setIdUserReceived(rs.getInt(3));
                transaction.setCash(rs.getFloat(4));
                transaction.setDate(rs.getDate(5));
            }
        } catch (SQLException ex) {
            System.out.println("Failed to get transaction: " + ex.getMessage());
        }
        return transaction;
    }
}
