package tn.esprit.models;

import java.sql.Date;
import java.sql.Timestamp;

public class ReponseConge {
    private int id;
    private String description;
    private Date date_debut;
    private Date date_fin;


    private Timestamp Date_creation;

    public ReponseConge() {
    }

    public ReponseConge(int id, String description, Date date_debut, Date date_fin, Timestamp date_creation) {
        this.id = id;
        this.description = description;
        this.date_debut = date_debut;
        this.date_fin = date_fin;
        Date_creation = date_creation;
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
        this.description = description;
    }

    public Date getDate_debut() {

        if (date_fin != null && date_debut != null && date_debut.after(date_fin)) {
            throw new IllegalArgumentException("La date de début doit être antérieure à la date de fin.");
        }return date_debut;
    }

    public void setDate_debut(Date date_debut) {
        this.date_debut = date_debut;
    }

    public Date getDate_fin() {
        return date_fin;
    }

    public void setDate_fin(Date date_fin) {
        this.date_fin = date_fin;
    }

    public Timestamp getDate_creation() {
        return Date_creation;
    }

    public void setDate_creation(Timestamp date_creation) {
        Date_creation = date_creation;
    }

    @Override
    public String toString() {
        return "ReponseConge{" +
                "id=" + id +
                ", description='" + description + '\'' +
                ", date_debut=" + date_debut +
                ", date_fin=" + date_fin +
                ", Date_creation=" + Date_creation +
                '}';
    }
}
