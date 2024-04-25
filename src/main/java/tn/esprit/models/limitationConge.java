package tn.esprit.models;

import tn.esprit.interfaces.IService;

public class limitationConge  {
    private int id;
    private int annee;
    private String mois;
    private int nbr_jours;

    public limitationConge(){}
    public limitationConge(int annee, String mois, int nbr_jours) {
        this.annee = annee;
        this.mois = mois;
        this.nbr_jours = nbr_jours;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getAnnee() {
        return annee;
    }

    public void setAnnee(int annee) {
        this.annee = annee;
    }

    public String getMois() {
        return mois;
    }

    public void setMois(String mois) {
        this.mois = mois;
    }

    public int getNbr_jours() {
        return nbr_jours;
    }

    public void setNbr_jours(int nbr_jours) {
        this.nbr_jours = nbr_jours;
    }

    @Override
    public String toString() {
        return "limitation_conge{" +
                "id=" + id +
                ", annee=" + annee +
                ", mois='" + mois + '\'' +
                ", nbr_jours=" + nbr_jours +
                '}';
    }
}
