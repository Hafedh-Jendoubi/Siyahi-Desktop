package tn.esprit.siyahidesktop.models;

import javafx.scene.control.DatePicker;

import java.sql.Date;
import java.time.LocalDate;
import java.util.Objects;

public class Service {
    private int id;
    private String nom,description;
    private double pricing; //monthly pricing
    private LocalDate expiration_date;
    private boolean isActive=true;

    public Service(String serviceName, String serviceDescription, Double fraisService, LocalDate expirationService) {
        this.id = -1;
        this.nom = serviceName;
        this.description = serviceDescription;
        this.pricing = fraisService;
        this.expiration_date = expirationService;

    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public Service()
    {

    }

    public Service(int id, String nom, String description,double pricing, LocalDate expiration_date,boolean isActive) {
        this.id = id;
        this.nom = nom;
        this.description = description;
        this.pricing = pricing;
        this.expiration_date = expiration_date;
        this.isActive = isActive;
    }

    public Service(String nom, String description, double pricing, LocalDate expiration_date,boolean isActive) {
        this.nom = nom;
        this.description = description;
        this.pricing = pricing;
        this.expiration_date = expiration_date;
        this.isActive = isActive;
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

    public double getPricing() {
        return pricing;
    }

    public void setPricing(double pricing) {
        this.pricing = pricing;
    }

    public LocalDate getExpiration_date() {

        return expiration_date;
    }

    public void setExpiration_date(LocalDate expiration_date) {
        this.expiration_date = expiration_date;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Service service = (Service) o;
        return id == service.id && Double.compare(pricing, service.pricing) == 0 && isActive == service.isActive && Objects.equals(nom, service.nom) && Objects.equals(description, service.description) && Objects.equals(expiration_date, service.expiration_date);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, nom, description, pricing, expiration_date, isActive);
    }

    @Override
    public String toString() {
        return "Service{" +
                "id=" + id +
                ", nom='" + nom + '\'' +
                ", description='" + description + '\'' +
                ", pricing=" + pricing +
                ", expiration_date=" + expiration_date +
                ", isActive=" + isActive +
                '}';
    }
}
