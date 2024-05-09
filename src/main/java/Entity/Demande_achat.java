package Entity;

import java.time.LocalDate;

public class Demande_achat {
    private int id;
    private Integer user_id;
    private Integer achat_id;
    private String nom;
    private String prenom;
    private LocalDate date_demande;
    private String num_tel;
    private String type_paiement;
    private Integer cin;
    private String adresse;
    private String etatdemande;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Integer getUser_id() {
        return user_id;
    }

    public void setUser_id(Integer user_id) {
        this.user_id = user_id;
    }

    public Integer getAchat_id() {
        return achat_id;
    }

    public void setAchat_id(Integer achat_id) {
        this.achat_id = achat_id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public LocalDate getDate_demande() {
        return date_demande;
    }

    public void setDate_demande(LocalDate date_demande) {
        this.date_demande = date_demande;
    }

    public String getNum_tel() {
        return num_tel;
    }

    public void setNum_tel(String num_tel) {
        this.num_tel = num_tel;
    }

    public String getType_paiement() {
        return type_paiement;
    }

    public void setType_paiement(String type_paiement) {
        this.type_paiement = type_paiement;
    }

    public Integer getCin() {
        return cin;
    }

    public void setCin(Integer cin) {
        this.cin = cin;
    }

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public String getEtatdemande() {
        return etatdemande;
    }

    public void setEtatdemande(String etatdemande) {
        this.etatdemande = etatdemande;
    }

    public Demande_achat(Integer user_id, Integer achat_id, String nom, String prenom, LocalDate date_demande, String num_tel, String type_paiement, Integer cin, String adresse, String etatdemande) {
        this.user_id = user_id;
        this.achat_id = achat_id;
        this.nom = nom;
        this.prenom = prenom;
        this.date_demande = date_demande;
        this.num_tel = num_tel;
        this.type_paiement = type_paiement;
        this.cin = cin;
        this.adresse = adresse;
        this.etatdemande = etatdemande;
    }

    public Demande_achat() {
    }

    public Demande_achat(int id, Integer user_id, Integer achat_id, String nom, String prenom, LocalDate date_demande, String num_tel, String type_paiement, Integer cin, String adresse, String etatdemande) {
        this.id = id;
        this.user_id = user_id;
        this.achat_id = achat_id;
        this.nom = nom;
        this.prenom = prenom;
        this.date_demande = date_demande;
        this.num_tel = num_tel;
        this.type_paiement = type_paiement;
        this.cin = cin;
        this.adresse = adresse;
        this.etatdemande = etatdemande;
    }

    @Override
    public String toString() {
        return "Demande_achat{" +
                "id=" + id +
                ", user_id=" + user_id +
                ", achat_id=" + achat_id +
                ", nom='" + nom + '\'' +
                ", prenom='" + prenom + '\'' +
                ", date_demande=" + date_demande +
                ", num_tel='" + num_tel + '\'' +
                ", type_paiement='" + type_paiement + '\'' +
                ", cin=" + cin +
                ", adresse='" + adresse + '\'' +
                ", etatdemande='" + etatdemande + '\'' +
                '}';
    }
}
