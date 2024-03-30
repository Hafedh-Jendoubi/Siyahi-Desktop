package tn.esprit.models;

import java.sql.Timestamp;

public class Reclamation {

    //attribut
    private int id;
    private String object;
    private String description;
    private Timestamp date_creation;
    private String auteur;
    private String email;
    private boolean status;

    //const

    public Reclamation(String objetDeLaRéclamation, String descriptionDeLaRéclamation, java.util.Date date, String auteurDeLaRéclamation, String s) {
    }

    public Reclamation(String object, String description, Timestamp date_creation, String auteur, String email, boolean status) {
        this.object = object;
        this.description = description;
        this.date_creation = date_creation;
        this.auteur = auteur;
        this.email = email;
        this.status = status;
    }

    public Reclamation(int id, String object, String description, Timestamp date_creation, String auteur, String email, boolean status) {
        this.id = id;
        this.object = object;
        this.description = description;
        this.date_creation = date_creation;
        this.auteur = auteur;
        this.email = email;
        this.status = status;
    }

    //getter setters

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getObject() {
        return object;
    }

    public void setObject(String object) {
        this.object = object;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Timestamp getDate_creation() {
        return date_creation;
    }

    public void setDate_creation(Timestamp date_creation) {
        this.date_creation = date_creation;
    }

    public String getauteur() {
        return auteur;
    }

    public void setauteur(String auteur) {
        this.auteur = auteur;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    //display

    @Override
    public String toString() {
        return "Reclamation{" +
                "id=" + id +
                ", object='" + object + '\'' +
                ", description='" + description + '\'' +
                ", date_creation=" + date_creation +
                ", auteur='" + auteur + '\'' +
                ", status=" + status +
                ", email='" + email + '\'' +

                '}';
    }
}
