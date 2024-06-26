package tn.esprit.services;

import jakarta.mail.*;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import tn.esprit.interfaces.IService;
import tn.esprit.models.User;
import tn.esprit.util.MaConnexion;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import java.security.SecureRandom;
import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;

public class UserService implements IService<User> {
    //att
    Connection cnx = MaConnexion.getInstance().getCnx();
    final String username = "2e4ddfacdadc07";
    final String password = "08aa0241ca491b";
    public static User connectedUser;

    //actions
    public static String hashPassword(String password) {
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        return passwordEncoder.encode(password);
    }

    public String generateRandomString() {
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
        String req = "INSERT INTO `user`(`email`, `roles`, `password`, `first_name`, `last_name`, `gender`, `address`, `phone_number`, `cin`, `created_at`, `image`, `old_email`, `activity`, `verified`) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try {
            PreparedStatement ps = cnx.prepareStatement(req);
            String pass = generateRandomString();
            String email = user.getFirst_name().toLowerCase() + "." + user.getLast_name().toLowerCase() + "@siyahi.tn";
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
            if(user.getImage() == null){ //Employee has not imported a User Profile Picture for Client
                if(user.getGender().equals("M"))
                    ps.setString(11, "7f9183c93cb4803aefc8262447c4efc9.png");
                else
                    ps.setString(11, "b56ef85920323ead69e5f0d1ca13a0cd.png");
            }else{
                ps.setString(11, user.getImage());
            }
            ps.setString(12, user.getOld_email());
            ps.setString(13, "T");
            ps.setInt(14, 0);

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
                        "<h2 style=\"color: red;\">You must activate your account by verifying the CIN by your CIN image</h2>", "text/html");
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
                "',`password`='" + user.getPassword() +
                "',`first_name`='" + user.getLast_name() +
                "',`last_name`='" + user.getFirst_name() +
                "',`gender`='" + user.getGender() +
                "',`address`='" + user.getAddress() +
                "',`phone_number`='" + user.getPhone_number() +
                "',`cin`='" + user.getCin() +
                "',`image`='" + user.getImage() +
                "',`old_email`='" + user.getOld_email() +
                "',`activity`='" + user.getActivity() +
                "',`verified`='" + user.getVerified() +
                "' WHERE `id`=" + user.getId();

        try {
            Statement st = cnx.createStatement();
            st.executeUpdate(req);
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
        String req = "SELECT * FROM `user` WHERE NOT JSON_CONTAINS(roles, '\"ROLE_SUPER_ADMIN\"', '$')";
        try {
            Statement st = cnx.createStatement();
            ResultSet res = st.executeQuery(req);
            while (res.next()){
                User user = new User();
                user = getInformation(res);
                if(user.getActivity().equals("T"))
                    user.setActivity("Active");
                else
                    user.setActivity("Inactive");
                if(user.getGender().equals("M"))
                    user.setGender("Male");
                else
                    user.setGender("Femelle");
                if(user.getRoles().equals("[\"ROLE_USER\"]"))
                    user.setRoles("Client");
                else if(user.getRoles().equals("[\"ROLE_STAFF\"]"))
                    user.setRoles("Employé(e)");
                else if(user.getRoles().equals("[\"ROLE_ADMIN\"]"))
                    user.setRoles("Admin");
                else
                    user.setRoles("Super Admin");
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

    public User getOneByCIN(int cin) {
        User user = new User();
        try {
            String req = "SELECT * FROM `user` WHERE `cin`=?";
            PreparedStatement ps = cnx.prepareStatement(req);
            ps.setInt(1, cin);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                user = getInformation(rs);
            }
        } catch (SQLException ex) {
            System.out.println("Failed to get user: " + ex.getMessage());
        }

        return user;
    }

    public User getOneByEMAIL(String email) {
        User user = null;
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

    public int getOneByRIB(long rib) {
        int result = -1;
        try {
            String req = "SELECT `user_id` FROM `compte_client` WHERE `rib`=?";
            PreparedStatement ps = cnx.prepareStatement(req);
            ps.setLong(1, rib);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                result = rs.getInt(1);
            }
        } catch (SQLException ex) {
            System.out.println("Failed to get RIB: " + ex.getMessage());
        }

        return result;
    }

    public int getOneByToken(String token) {
        int result = -1;
        try {
            String req = "SELECT `user_id` FROM `reset_password_request` WHERE `selector`=?";
            PreparedStatement ps = cnx.prepareStatement(req);
            ps.setString(1, token);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                result = rs.getInt(1);
            }
        } catch (SQLException ex) {
            System.out.println("Failed to get Token: " + ex.getMessage());
        }

        return result;
    }

    public List<Integer> getUserEmploye() {
        int nb_client = 0, nb_staff = 0, nb_admin = 0;
        List<Integer> result = new ArrayList<>();
        try {
            String req1 = "SELECT * FROM `user` WHERE `roles`=?";
            PreparedStatement ps1 = cnx.prepareStatement(req1);
            ps1.setString(1, "[\"ROLE_USER\"]");
            ResultSet rs1 = ps1.executeQuery();
            while (rs1.next()) {
                nb_client++;
            }

            String req2 = "SELECT * FROM `user` WHERE `roles`=?";
            PreparedStatement ps2 = cnx.prepareStatement(req2);
            ps2.setString(1, "[\"ROLE_STAFF\"]");
            ResultSet rs2 = ps2.executeQuery();
            while (rs2.next()) {
                nb_staff++;
            }

            String req3 = "SELECT * FROM `user` WHERE `roles`=?";
            PreparedStatement ps3 = cnx.prepareStatement(req3);
            ps3.setString(1, "[\"ROLE_ADMIN\"]");
            ResultSet rs3 = ps3.executeQuery();
            while (rs3.next()) {
                nb_admin++;
            }

            result.add(nb_client); result.add(nb_staff); result.add(nb_admin);
        } catch (SQLException ex) {
            System.out.println("Failed to get Token: " + ex.getMessage());
        }

        return result;
    }

    public User authentification(String email, String password){
        boolean result = false;
        User user = getOneByEMAIL(email);
        try {
            String pass = user.getPassword();
            BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
            result = passwordEncoder.matches(password, pass);
            if(result) {
                if(user.getActivity().equals("T"))
                    user.setActivity("Active");
                else
                    user.setActivity("Inactive");
                if(user.getGender().equals("M"))
                    user.setGender("Male");
                else
                    user.setGender("Femelle");
                if(user.getRoles().equals("[\"ROLE_USER\"]"))
                    user.setRoles("Client");
                else if(user.getRoles().equals("[\"ROLE_STAFF\"]"))
                    user.setRoles("Employé(e)");
                else if(user.getRoles().equals("[\"ROLE_ADMIN\"]"))
                    user.setRoles("Admin");
                else
                    user.setRoles("Super Admin");
                user.setPassword(password);
                connectedUser = user;
            }else
                connectedUser = null;
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }

        return connectedUser;
    }

    public void DoubleFactorAuth(User user, String code){
        if(getOneByToken(code) == -1) {
            String req = "INSERT INTO `reset_password_request`(`user_id`, `selector`, `hashed_token`, `requested_at`, `expires_at`) VALUES (?, ?, ?, ?, ?)";
            try {
                PreparedStatement ps = cnx.prepareStatement(req);
                ps.setInt(1, user.getId());
                ps.setString(2, code);
                ps.setString(3, hashPassword(code));
                ps.setTimestamp(4, new Timestamp(System.currentTimeMillis()));
                ps.setTimestamp(5, new Timestamp(System.currentTimeMillis() + 60 * 60 * 1000));
                ps.executeUpdate();
            } catch (SQLException e) {
                System.err.println(e);
            }
        }
    }

    public void DoubleFactorAuthOK(User user){
        String req = "DELETE FROM `reset_password_request` WHERE `user_id` = ?";
        try{
            PreparedStatement ps1 = cnx.prepareStatement(req);
            ps1.setInt(1, user.getId());
            ps1.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void RequestResetPassword(User user) {
        String token = generateRandomString();
        /* Query */
        String req = "INSERT INTO `reset_password_request`(`user_id`, `selector`, `hashed_token`, `requested_at`, `expires_at`) VALUES (?, ?, ?, ?, ?)";
        try {
            PreparedStatement ps = cnx.prepareStatement(req);
            ps.setInt(1, user.getId());
            ps.setString(2, token);
            ps.setString(3, hashPassword(token));
            ps.setTimestamp(4, new Timestamp(System.currentTimeMillis()));
            ps.setTimestamp(5, new Timestamp(System.currentTimeMillis() + 60 * 60 * 1000));
            ps.executeUpdate();
        } catch (SQLException e){System.err.println(e);}

        /* Mailing */
        String to = user.getEmail();
        String from = "no-reply@siyahi.tn";
        String host = "smtp.mailtrap.io";
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", host);
        props.put("mail.smtp.port", "587");
        Session session = Session.getInstance(props, new jakarta.mail.Authenticator() {
                protected PasswordAuthentication getPasswordAuthentication() {return new PasswordAuthentication(username, password);}
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
                    "<p>To reset your password, please type the following Token in Siyahi Bank Desktop Application.</p>\n" +
                    "\n" +
                    "<p>Token: " + token +"</p>\n" +
                    "\n" +
                    "<p>This token will expire in 1 hour.</p>\n" +
                    "\n" +
                    "<p>Cheers!</p>", "text/html");
            Transport.send(message);
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }

    public void resetPassword(int user_id, String pass){
        String req = "DELETE FROM `reset_password_request` WHERE `user_id` = ?";
        String req2 = "UPDATE `user` SET `password`= ? WHERE `id` = ?";
        try{
            PreparedStatement ps1 = cnx.prepareStatement(req);
            ps1.setInt(1, user_id);
            ps1.executeUpdate();

            PreparedStatement ps2 = cnx.prepareStatement(req2);
            ps2.setString(1, hashPassword(pass));
            ps2.setInt(2, user_id);
            ps2.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void enableUser(User user){
        String req = "UPDATE `user` SET " +
                "`activity`= 'T'" +
                " WHERE `id`= " + user.getId();

        try {
            Statement st = cnx.createStatement();
            st.executeUpdate(req);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void disableUser(User user){
        String req = "UPDATE `user` SET " +
                "`activity`= 'F'" +
                " WHERE `id`= " + user.getId();

        try {
            Statement st = cnx.createStatement();
            st.executeUpdate(req);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void verifyUser(User user){
        String req = "UPDATE `user` SET " +
                "`verified`= 1" +
                " WHERE `id`= " + user.getId();

        try {
            Statement st = cnx.createStatement();
            st.executeUpdate(req);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}