package tn.esprit.models;

import java.util.Objects;

public class Service {
    private int id;
    private String nom,description;

    public Service(int id, String nom, String description) {
        this.id = id;
        this.nom = nom;
        this.description = description;
    }

    public Service(String nom, String description) {
        this.nom = nom;
        this.description = description;
    }

    public Service() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Service service)) return false;
        return getId() == service.getId() && Objects.equals(getNom(), service.getNom()) && Objects.equals(getDescription(), service.getDescription());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getNom(), getDescription());
    }

    @Override
    public String toString() {
        return "Service{" +
                "id=" + id +
                ", nom='" + nom + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
