package tn.esprit.siyahidesktop.models;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.temporal.Temporal;

import tn.esprit.siyahidesktop.models.User;

public class Compte {
    private long rib;
    private int id;
    private Timestamp created_at;
    private Service service;
    private double solde;
    private User user;

    public Compte(long rib, Timestamp created_at, Service service, double solde,User user) {
        this.rib = rib;
        this.created_at = created_at;
        this.service = service;
        this.solde = solde;
        this.user = user;
    }

    public Compte() {
    }


    public long getRib() {
        return rib;
    }

    public void setRib(long rib) {
        this.rib = rib;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Timestamp getDate_creation() {
        return created_at;
    }

    public Temporal getExpirationDate() {
        if (created_at == null) {
            return null; // or handle this case as needed
        }
        LocalDateTime creationDateTime = created_at.toLocalDateTime();
        LocalDateTime expirationDateTime = creationDateTime.plusYears(4);
        return expirationDateTime;
    }

    public void setDate_creation(Timestamp date_creation) {
        this.created_at = date_creation;
    }

    public Service getService() {
        return service;
    }

    public void setService(Service service) {
        this.service = service;
    }

    public double getSolde() {
        return solde;
    }

    public void setSolde(double solde) {
        this.solde = solde;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "Compte{" +
                "rib=" + rib +
                ", id=" + id +
                ", date_creation=" + created_at +
                ", service=" + service +
                ", solde=" + solde +
                //", user=" + user +
                '}';
    }


}