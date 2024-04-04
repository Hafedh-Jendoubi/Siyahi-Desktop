package tn.esprit.siyahidesktop.services;

import jakarta.mail.*;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import tn.esprit.siyahidesktop.interfaces.IService;
import tn.esprit.siyahidesktop.models.User;
import tn.esprit.siyahidesktop.util.MaConnexion;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import java.security.SecureRandom;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class UserService implements IService<User> {
    //att
    Connection cnx = MaConnexion.getInstance().getCnx();
    final String username = "2e4ddfacdadc07";
    final String password = "08aa0241ca491b";

    //actions
    public static String hashPassword(String password) {
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        return passwordEncoder.encode(password);
    }

    private String generateRandomString() {
        int length = 8;
        String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        SecureRandom random = new SecureRandom();
        StringBuilder sb = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            int randomIndex = random.nextInt(CHARACTERS.length());
            char randomChar = CHARACTERS.charAt(randomIndex);
            sb.append(randomChar);
        }
        return sb.toString();
    }

    @Override
    public void add(User user) {
        String req = "INSERT INTO `user`(`email`, `roles`, `password`, `first_name`, `last_name`, `gender`, `address`, `phone_number`, `cin`, `created_at`, `image`, `old_email`, `activity`) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try {
            PreparedStatement ps = cnx.prepareStatement(req);
            String pass = generateRandomString();
            String email = user.getFirst_name() + "." + user.getLast_name() + "@siyahi.tn";
            ps.setString(1, email);
            ps.setString(2, "[\"ROLE_USER\"]");
            ps.setString(3, hashPassword(pass));
            ps.setString(4, user.getFirst_name());
            ps.setString(5, user.getLast_name());
            ps.setString(6, user.getGender());
            ps.setString(7, user.getAddress());
            ps.setInt(8, user.getPhone_number());
            ps.setInt(9, user.getCin());
            ps.setTimestamp(10, new Timestamp(System.currentTimeMillis()));
            if(user.getGender().equals("M"))
                ps.setString(11, "7f9183c93cb4803aefc8262447c4efc9.png");
            else
                ps.setString(11, "b56ef85920323ead69e5f0d1ca13a0cd.png");
            ps.setString(12, user.getOld_email());
            ps.setString(13, "T");

            /* --------- Mailing -----------*/
            String to = user.getOld_email();
            String from = "no-reply@siyahi.tn";
            String host = "smtp.mailtrap.io";
            Properties props = new Properties();
            props.put("mail.smtp.auth", "true");
            props.put("mail.smtp.starttls.enable", "true");
            props.put("mail.smtp.host", host);
            props.put("mail.smtp.port", "587");
            Session session = Session.getInstance(props,
                    new jakarta.mail.Authenticator() {
                        protected PasswordAuthentication getPasswordAuthentication() {
                            return new PasswordAuthentication(username, password);
                        }
                    });
            try {
                Message message = new MimeMessage(session);
                message.setFrom(new InternetAddress(from));
                message.setRecipient(Message.RecipientType.TO, new InternetAddress(to));
                message.setSubject("Siyahi - Account creation");
                message.setContent("<center><img src=\"https://i.imgur.com/u6bBjNg.png\"></center>\n" +
                        "\n" +
                        "<h1>Hello!</h1>\n" +
                        "\n" +
                        "<p>Congratulations! Your account creation has been completed successfully.</p>\n" +
                        "<p>These are your account information:</p>\n" +
                        "<p>Email: " + email + "</p>\n" +
                        "<p>Password: " + pass +"</p>\n" +
                        "\n" +
                        "<p>Cheers!</p>", "text/html");
                Transport.send(message);
            } catch (MessagingException e) {
                throw new RuntimeException(e);
            }
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
                "',`password`='" + hashPassword(user.getPassword()) +
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
    public User getOneByID(int id) {
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

    @Override
    public User getOneByEMAIL(String email) {
        User user = new User();
        try {
            String req = "SELECT * FROM `user` WHERE `email`=?";
            PreparedStatement ps = cnx.prepareStatement(req);
            ps.setString(1, email);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                user = getInformation(rs);
            }
        } catch (SQLException ex) {
            System.out.println("Failed to get user: " + ex.getMessage());
        }

        return user;
    }

    public boolean authentification(String email, String password){
        boolean result = false;
        String req = "SELECT * FROM `user` WHERE email='" + email + "'";
        try {
            Statement st = cnx.createStatement();
            ResultSet rs = st.executeQuery(req);
            if (rs.next()) {
                String hashedPassFromDB = rs.getString("password");
                BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
                result = passwordEncoder.matches(password, hashedPassFromDB);;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return result;
    }

    public void resetPassword(String email) {
        User user = getOneByEMAIL(email);
        if(user.getEmail() == null){
            System.out.println("User n'existe pas");
        }else{
            /* --------- Mailing -----------*/
            String to = user.getEmail();
            String from = "no-reply@siyahi.tn";
            String host = "smtp.mailtrap.io";
            Properties props = new Properties();
            props.put("mail.smtp.auth", "true");
            props.put("mail.smtp.starttls.enable", "true");
            props.put("mail.smtp.host", host);
            props.put("mail.smtp.port", "587");
            Session session = Session.getInstance(props, new jakarta.mail.Authenticator() {
                            protected PasswordAuthentication getPasswordAuthentication() {
                                return new PasswordAuthentication(username, password);
                            }
                        });
            try {
                Message message = new MimeMessage(session);
                message.setFrom(new InternetAddress(from));
                message.setRecipient(Message.RecipientType.TO, new InternetAddress(to));
                message.setSubject("Reset Password");
                message.setContent("<center><img src=\"https://i.imgur.com/u6bBjNg.png\"></center>\n" +
                        "\n" +
                        "<h1>Hello!</h1>\n" +
                        "\n" +
                        "<p>To reset your password, please visit the following link</p>\n" +
                        "\n" +
                        "<a href=\"http://127.0.0.1:8000/reset-password/\">Click here</a>\n" +
                        "\n" +
                        "<p>This link will expire in 1 hour.</p>\n" +
                        "\n" +
                        "<p>Cheers!</p>", "text/html");
                Transport.send(message);
            } catch (MessagingException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void enableUser(int id){
        User user = getOneByID(id);
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
                "',`activity`='" + "T" +
                "' WHERE `id`=" + user.getId();

        try {
            Statement st = cnx.createStatement();
            int rowsUpdated = st.executeUpdate(req);
            if (rowsUpdated > 0) {
                System.out.println("User has been activated successfully.");
            } else {
                System.out.println("Failed to enable user");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void disableUser(int id){
        User user = getOneByID(id);
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
                "',`activity`='" + "F" +
                "' WHERE `id`=" + user.getId();

        try {
            Statement st = cnx.createStatement();
            int rowsUpdated = st.executeUpdate(req);
            if (rowsUpdated > 0) {
                System.out.println("User has been deactivated successfully.");
            } else {
                System.out.println("Failed to disable user");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
