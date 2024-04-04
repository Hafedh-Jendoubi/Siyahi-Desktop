package org.example.entities;

import java.util.Date;

public class ReponseCredit {
    private int id,nbr_mois_paiement;

    private String description;
    private float solde_a_payer;
    private Date date_debut_paiement;

    public ReponseCredit() {
    }

    public ReponseCredit(int id, int nbr_mois_paiement, String description, float solde_a_payer, Date date_debut_paiement) {
        this.id = id;
        this.nbr_mois_paiement = nbr_mois_paiement;
        this.description = description;
        this.solde_a_payer = solde_a_payer;
        this.date_debut_paiement = date_debut_paiement;
    }

    public ReponseCredit(int nbr_mois_paiement, String description, float solde_a_payer, Date date_debut_paiement) {
        this.nbr_mois_paiement = nbr_mois_paiement;
        this.description = description;
        this.solde_a_payer = solde_a_payer;
        this.date_debut_paiement = date_debut_paiement;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getNbr_mois_paiement() {
        return nbr_mois_paiement;
    }

    public void setNbr_mois_paiement(int nbr_mois_paiement) {
        this.nbr_mois_paiement = nbr_mois_paiement;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public float getSolde_a_payer() {
        return solde_a_payer;
    }

    public void setSolde_a_payer(float solde_a_payer) {
        this.solde_a_payer = solde_a_payer;
    }

    public Date getDate_debut_paiement() {
        return date_debut_paiement;
    }

    public void setDate_debut_paiement(Date date_debut_paiement) {
        this.date_debut_paiement = date_debut_paiement;
    }

    @Override
    public String toString() {
        return "ReponseCredit{" +
                "id=" + id +
                ", nbr_mois_paiement=" + nbr_mois_paiement +
                ", description='" + description + '\'' +
                ", solde_a_payer=" + solde_a_payer +
                ", date_debut_paiement=" + date_debut_paiement +
                '}';
    }
}
