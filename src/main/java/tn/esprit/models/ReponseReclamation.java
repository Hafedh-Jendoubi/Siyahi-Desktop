package tn.esprit.models;

import java.sql.Timestamp;

public class ReponseReclamation {

    private int id;
    private String description;
    private int reclamation_id;
    private int user_id;
    private Timestamp date_creation;
    private String auteur;

    private Reclamation reclamation;

    public Reclamation getReclamation() {
        return reclamation;
    }

    public void setReclamation(Reclamation reclamation) {
        this.reclamation = reclamation;
    }


    public ReponseReclamation() {
    }

    public ReponseReclamation(String description, Timestamp date_creation, String auteur) {
    setDescription(description);
    this.date_creation = date_creation;
    setAuteur(auteur);    }

    public ReponseReclamation(String description, int reclamation_id, int user_id, Timestamp date_creation, String auteur) {
        this.description = description;
        this.reclamation_id = reclamation_id;
        this.user_id = user_id;
        this.date_creation = date_creation;
        this.auteur = auteur;
    }

    public ReponseReclamation(int id, String description, int reclamation_id, int user_id, Timestamp date_creation, String auteur) {
        this.id = id;
        this.description = description;
        this.reclamation_id = reclamation_id;
        this.user_id = user_id;
        this.date_creation = date_creation;
        this.auteur = auteur;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        if (description != null && !description.trim().isEmpty()) {
            this.description = description;
        } else {
            throw new IllegalArgumentException("La description de la réponse ne peut pas être vide.");
        }
    }
    

    public int getReclamation_id() {
        return reclamation_id;
    }

    public void setReclamation_id(int reclamation_id) {
        this.reclamation_id = reclamation_id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public Timestamp getDate_creation() {
        return date_creation;
    }

    public void setDate_creation(Timestamp date_creation) {
        this.date_creation = date_creation;
    }

    public String getAuteur() {
        return auteur;
    }

    public void setAuteur(String auteur) {
        if (auteur != null && !auteur.trim().isEmpty()) {
            this.auteur = auteur;
        } else {
            throw new IllegalArgumentException("L'auteur de la réponse ne peut pas être vide.");
        }
    }


    @Override
    public String toString() {
        return "ReponseReclamation{" +
                "id=" + id +
                ", description='" + description + '\'' +
                ", reclamation_id=" + reclamation_id +
                ", user_id=" + user_id +
                ", date_creation=" + date_creation +
                ", auteur='" + auteur + '\'' +
                '}';
    }
}
