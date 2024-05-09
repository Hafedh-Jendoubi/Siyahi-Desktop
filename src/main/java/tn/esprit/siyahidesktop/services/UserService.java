package tn.esprit.siyahidesktop.services;

import tn.esprit.siyahidesktop.interfaces.IService;
import tn.esprit.siyahidesktop.models.User;
import tn.esprit.siyahidesktop.util.MaConnexion;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserService implements IService<User> {

    Connection cnx = MaConnexion.getInstance().getCnx();

    //actions
    @Override
    public void add(User user) {
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        String req = "INSERT INTO `user`(`email`, `roles`, `password`, `first_name`, `last_name`, `gender`, `address`, `phone_number`, `cin`, `created_at`, `image`, `old_email`, `activity`) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try {
            PreparedStatement ps = cnx.prepareStatement(req);
            ps.setString(1, user.getEmail());
            ps.setString(2, user.getRoles());
            ps.setString(3, user.getPassword());
            ps.setString(4, user.getFirst_name());
            ps.setString(5, user.getLast_name());
            ps.setString(6, user.getGender());
            ps.setString(7, user.getAddress());
            ps.setInt(8, user.getPhone_number());
            ps.setInt(9, user.getCin());
            ps.setTimestamp(10, timestamp);
            ps.setString(11, user.getImage());
            ps.setString(12, user.getOld_email());
            ps.setString(13, user.getActivity());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void update(User user) {
        String req = "UPDATE `user` SET " +
                "`email`='" + user.getEmail() +
                "',`roles`='" + user.getRoles() +
                "',`password`='" + user.getPassword() +
                "',`first_name`='" + user.getFirst_name() +
                "',`last_name`='" + user.getLast_name() +
                "',`gender`='" + user.getGender() +
                "',`address`='" + user.getAddress() +
                "',`phone_number`='" + user.getPhone_number() +
                "',`cin`='" + user.getCin() +
                "',`image`='" + user.getImage() +
                "',`old_email`='" + user.getOld_email() +
                "',`activity`='" + user.getActivity() +
                "' WHERE `id`=" + user.getId();

        try {
            Statement st = cnx.createStatement();
            int rowsUpdated = st.executeUpdate(req);
            if (rowsUpdated > 0) {
                System.out.println("User updated successfully");
            } else {
                System.out.println("Failed to update user");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void delete(User user) {
        String req = "DELETE FROM `user` WHERE id = ?";
        try {
            PreparedStatement ps = cnx.prepareStatement(req);
            ps.setInt(1, user.getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    //This function is used to avoid the repetition.
    private User getInformation(ResultSet res){
        User user = new User();
        try {
            user.setId(res.getInt(1));
            user.setEmail(res.getString(2));
            user.setRoles(res.getString(3));
            user.setPassword(res.getString(4));
            user.setFirst_name(res.getString(5));
            user.setLast_name(res.getString(6));
            user.setGender(res.getString(7));
            user.setAddress(res.getString(8));
            user.setPhone_number(res.getInt(9));
            user.setCin(res.getInt(10));
            user.setDate_creation_c(res.getTimestamp(11));
            user.setImage(res.getString(12));
            user.setOld_email(res.getString(13));
            user.setActivity(res.getString(14));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return user;
    }

    @Override
    public List<User> getAll() {
        List<User> users = new ArrayList<>();
        String req = "SELECT * FROM `user`";
        try {
            Statement st = cnx.createStatement();
            ResultSet res = st.executeQuery(req);
            while (res.next()){
                User user = new User();
                user = getInformation(res);
                users.add(user);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return users;
    }

    @Override
    public User getOne(int id) {
        User user = new User();
        try {
            String req = "SELECT * FROM `user` WHERE `id`=?";
            PreparedStatement ps = cnx.prepareStatement(req);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                user = getInformation(rs);
            }
        } catch (SQLException ex) {
            System.out.println("Failed to get user: " + ex.getMessage());
        }

        return user;
    }
}
