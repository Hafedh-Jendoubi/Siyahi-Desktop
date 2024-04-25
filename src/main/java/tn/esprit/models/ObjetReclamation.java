package tn.esprit.models;

public class ObjetReclamation {

    private String nom;

    public ObjetReclamation(String nom) {
        this.nom = nom;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    @Override
    public String toString() {
        return nom;
    }
}
