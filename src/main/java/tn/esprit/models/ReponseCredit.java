package tn.esprit.models;

import java.sql.Date;

public class ReponseCredit {
    private int id;
    private int user_id;
    private int nbr_mois_paiement;
    private String description;
    private float solde_a_payer;
    private float auto_financement;

    private Date date_debut_paiement;
    private int credit_id; // Champ pour la référence au crédit

    public ReponseCredit() {
    }

    public ReponseCredit(int id, int nbr_mois_paiement, String description, float solde_a_payer,float auto_financement, Date date_debut_paiement, int credit_id) {
        this.id = id;
        this.setNbr_mois_paiement(nbr_mois_paiement);
        this.setDescription(description);
        this.setSolde_a_payer(solde_a_payer);
        this.setauto_financement(auto_financement);
        this.setDate_debut_paiement(date_debut_paiement);
        this.setCredit_id(credit_id);
    }

    public ReponseCredit(int nbr_mois_paiement, String description, float solde_a_payer,float auto_financement, Date date_debut_paiement, int credit_id, int user_id) {
        this.setNbr_mois_paiement(nbr_mois_paiement);
        this.setDescription(description);
        this.setSolde_a_payer(solde_a_payer);
        this.setauto_financement(auto_financement);
        this.setDate_debut_paiement(date_debut_paiement);
        this.setCredit_id(credit_id);
        this.setUser_id(user_id);
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
        if (nbr_mois_paiement <= 0) {
            throw new IllegalArgumentException("Le nombre de mois de paiement doit être strictement positif.");
        }
        this.nbr_mois_paiement = nbr_mois_paiement;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        if (description == null || description.trim().length() < 2) {
            throw new IllegalArgumentException("La description doit comporter au moins 2 caractères.");
        }
        this.description = description;
    }

    public float getSolde_a_payer() {
        return solde_a_payer;
    }

    public void setSolde_a_payer(float solde_a_payer) {
        if (solde_a_payer < 100) {
            throw new IllegalArgumentException("Le solde à payer doit être supérieur ou égal à 100.");
        }
        this.solde_a_payer = solde_a_payer;
    }
    public float getauto_financement() {
        return auto_financement;
    }

    public void setauto_financement(float auto_financement) {

        this.auto_financement = auto_financement;
    }

    public Date getDate_debut_paiement() {
        return date_debut_paiement;
    }

    public void setDate_debut_paiement(Date date_debut_paiement) {
        Date currentDate = new Date(System.currentTimeMillis()); // Obtient la date actuelle
        if (date_debut_paiement == null || date_debut_paiement.before(currentDate)) {
            throw new IllegalArgumentException("La date de début de paiement doit être dans le futur.");
        }
        this.date_debut_paiement = date_debut_paiement;
    }

    public int getCredit_id() {
        return credit_id;
    }

    public void setCredit_id(int credit_id) {
        this.credit_id = credit_id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    @Override
    public String toString() {
        return "ReponseCredit{" +
                "id=" + id +
                "user_id=" + user_id + '\'' +
                ", nbr_mois_paiement=" + nbr_mois_paiement +
                ", description='" + description + '\'' +
                ", solde_a_payer=" + solde_a_payer +"TND" +
                ", auto_financement=" + auto_financement +"TND" +
                ", date_debut_paiement=" + date_debut_paiement +
                ", credit_id=" + credit_id +
                '}';
    }
}
