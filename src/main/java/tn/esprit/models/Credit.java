package tn.esprit.models;

import java.sql.Date;

public class Credit {
    private int id;
    private int nbr_mois_paiement;
    private String description;
    private String contrat;
    private float solde_demande;
    private Date date_debut_paiement;
    private int type_credit_id; // Nouvelle propriété pour la jointure avec TypeCredit
    private int user_id;
    private String nom_type_credit; // Nouvelle propriété pour le nom du type de crédit

    public Credit() {}

    public Credit(int id, int nbr_mois_paiement, String description, String contrat, float solde_demande, Date date_debut_paiement, int type_credit_id) {
        this.id = id;
        this.nbr_mois_paiement = nbr_mois_paiement;
        this.setDescription(description);
        this.setContrat(contrat);
        this.setSolde_demande(solde_demande);
        this.setDate_debut_paiement(date_debut_paiement);
        this.setType_credit_id(type_credit_id);
    }

    public Credit(int nbr_mois_paiement, String description, String contrat, float solde_demande, Date date_debut_paiement, int type_credit_id) {
        this.nbr_mois_paiement = nbr_mois_paiement;
        this.setDescription(description);
        this.setContrat(contrat);
        this.setSolde_demande(solde_demande);
        this.setDate_debut_paiement(date_debut_paiement);
        this.setType_credit_id(type_credit_id);
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
        if (nbr_mois_paiement <= 2) {
            throw new IllegalArgumentException("Le nombre de mois de paiement doit être supérieur à 2.");
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

    public String getContrat() {
        return contrat;
    }

    public void setContrat(String contrat) {
        this.contrat = contrat;
    }

    public float getSolde_demande() {
        return solde_demande;
    }

    public void setSolde_demande(float solde_demande) {
        if (solde_demande < 100) {
            throw new IllegalArgumentException("Le solde demandé doit être supérieur ou égal à 100.");
        }
        this.solde_demande = solde_demande;
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

    public int getType_credit_id() {
        return type_credit_id;
    }

    public void setType_credit_id(int type_credit_id) {
        this.type_credit_id = type_credit_id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getType_credit_nom() {
        return nom_type_credit;
    }

    public void setType_credit_nom(String nom_type_credit) {
        this.nom_type_credit = nom_type_credit;
    }

    @Override
    public String toString() {
        return "Credit{" +
                "id=" + id + '\'' +
                "User_id=" + user_id + '\'' +
                ", nbr_mois_paiement=" + nbr_mois_paiement + '\'' +
                ", description='" + description + '\'' +
                ", contrat='" + contrat + '\'' +
                ", solde_demande=" + solde_demande +"TND" +
                ", date_debut_paiement=" + date_debut_paiement +
                ", type_credit_id=" + type_credit_id +
                ", type_credit_nom='" + nom_type_credit + '\'' +
                '}';
    }
}
