package tn.esprit.models;

import java.sql.Date;

public class Transaction {
    //Att
    private int id;
    private int idUserSent;
    private int idUserReceived;
    private float cash;
    private Date date;

    //Constructors
    public Transaction(){}

    public Transaction(int idUserSent, int idUserReceived, float cash, Date date) {
        this.idUserSent = idUserSent;
        this.idUserReceived = idUserReceived;
        this.cash = cash;
        this.date = date;
    }

    //Getters & Setters

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdUserSent() {
        return idUserSent;
    }

    public void setIdUserSent(int idUserSent) {
        this.idUserSent = idUserSent;
    }

    public int getIdUserReceived() {
        return idUserReceived;
    }

    public void setIdUserReceived(int idUserReceived) {
        this.idUserReceived = idUserReceived;
    }

    public float getCash() {
        return cash;
    }

    public void setCash(float cash) {
        this.cash = cash;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    //Display
    @Override
    public String toString() {
        return "Transaction{" +
                "id=" + id +
                ", idUserSent=" + idUserSent +
                ", idUserReceived=" + idUserReceived +
                ", cash=" + cash +
                ", date=" + date +
                '}';
    }
}
