package tn.esprit.models;

import java.sql.Date;

public class Transaction {
    //Att
    private int id;
    private long ribUserSent;
    private long ribUserReceived;
    private double cash;
    private Date date;

    //Constructors
    public Transaction(){}

    public Transaction(long ribUserSent, long ribUserReceived, double cash, Date date) {
        this.ribUserSent = ribUserSent;
        this.ribUserReceived = ribUserReceived;
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

    public long getRibUserSent() {return ribUserSent;}

    public void setRibUserSent(long ribUserSent) {this.ribUserSent = ribUserSent;}

    public long getRibUserReceived() {return ribUserReceived;}

    public void setRibUserReceived(long ribUserReceived) {this.ribUserReceived = ribUserReceived;}

    public double getCash() {
        return cash;
    }

    public void setCash(double cash) {
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
                ", idUserSent=" + ribUserSent +
                ", idUserReceived=" + ribUserReceived +
                ", cash=" + cash +
                ", date=" + date +
                '}';
    }
}
