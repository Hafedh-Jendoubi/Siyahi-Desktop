package tn.esprit.siyahidesktop.models;

import java.sql.Timestamp;

public class User {
    //Att
    private int id;
    private String email;
    private String roles;
    private String password;
    private String first_name;
    private String last_name;
    private String gender;
    private String address;
    private int phone_number;
    private int cin;
    private Timestamp date_creation_c;
    private String image;
    private String old_email;
    private String activity;

    //Constructors
    public User(){}

    public User(String first_name, String last_name, String gender, String address, int phone_number, int cin, String old_email) {
        this.first_name = first_name;
        this.last_name = last_name;
        this.gender = gender;
        this.address = address;
        this.phone_number = phone_number;
        this.cin = cin;
        this.old_email = old_email;
    }

    public User(String email, String roles, String password, String first_name, String last_name, String gender, String address, int phone_number, int cin, String image, String old_email, String activity) {
        this.email = email;
        this.roles = roles;
        this.password = password;
        this.first_name = first_name;
        this.last_name = last_name;
        this.gender = gender;
        this.address = address;
        this.phone_number = phone_number;
        this.cin = cin;
        this.image = image;
        this.old_email = old_email;
        this.activity = activity;
    }

    //Getters & Setters

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRoles() {
        return roles;
    }

    public void setRoles(String roles) {
        this.roles = roles;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getPhone_number() {
        return phone_number;
    }

    public void setPhone_number(int phone_number) {
        this.phone_number = phone_number;
    }

    public int getCin() {
        return cin;
    }

    public void setCin(int cin) {
        this.cin = cin;
    }

    public Timestamp getDate_creation_c() {
        return date_creation_c;
    }

    public void setDate_creation_c(Timestamp date_creation_c) {
        this.date_creation_c = date_creation_c;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getOld_email() {
        return old_email;
    }

    public void setOld_email(String old_email) {
        this.old_email = old_email;
    }

    public String getActivity() {
        return activity;
    }

    public void setActivity(String activity) {
        this.activity = activity;
    }

    //Display

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", email='" + email + '\'' +
                ", roles='" + roles + '\'' +
                ", password='" + password + '\'' +
                ", first_name='" + first_name + '\'' +
                ", last_name='" + last_name + '\'' +
                ", gender=" + gender +
                ", address='" + address + '\'' +
                ", phone_number=" + phone_number +
                ", cin=" + cin +
                ", date_creation_c=" + date_creation_c +
                ", image='" + image + '\'' +
                ", old_email='" + old_email + '\'' +
                ", activity=" + activity +
                '}';
    }
}
