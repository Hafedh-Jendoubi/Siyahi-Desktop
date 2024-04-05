package tn.esprit.models;
import java.sql.Timestamp;
import java.sql.Date;



public class Conge {
    //Att
    private int id;
    private String Description;
    private  Date Date_Debut;
    private Date Date_Fin;
    private boolean status;

    private Timestamp Date_demande;
    private String Type_conge;
    private String Justification;

    //Constructors
    public Conge(){}

    public Conge(int id ,String Description, Date Date_Debut, Date Date_Fin, Timestamp Date_demande,String Type_conge, String Justification,boolean status) {
        this.id=id;
        this.Description = Description;

        this.Date_Debut = Date_Debut;
        this.Date_Fin = Date_Fin;
        this.Date_demande = Date_demande;
        this.Justification = Justification;
        this.Type_conge = Type_conge;
        this.status = status;

    }
    //Getters & Setters

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        if (description == null || description.trim().length() < 2) {
            throw new IllegalArgumentException("La description doit comporter au moins 2 caractères.");
        }Description = description;
    }

    public Date getDate_Debut() {
        if (Date_Fin != null && Date_Debut != null && Date_Debut.after(Date_Fin)) {
            throw new IllegalArgumentException("La date de début doit être antérieure à la date de fin.");
        }
        return Date_Debut;
    }

    public void setDate_Debut(Date date_Debut) {
        Date_Debut = date_Debut ;
    }

    public Date getDate_Fin() {
        return Date_Fin;
    }

    public void setDate_Fin(Date date_Fin) {
        if (Date_Debut != null && date_Fin != null && Date_Debut.after(date_Fin)) {
            throw new IllegalArgumentException("La date de fin doit être postérieure à la date de début.");
        }
        Date_Fin = date_Fin;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public Timestamp getDate_demande() {
        return Date_demande;
    }

    public void setDate_demande(Timestamp date_demande) {
        Date_demande = date_demande;
    }

    public String getType_conge() {
        return Type_conge;
    }

    public void setType_conge(String type_conge) {
        Type_conge = type_conge;
    }

    public String getJustification() {
        return Justification;
    }

    public void setJustification(String justification) {
        Justification = justification;
    }


    //display


    @Override
    public String toString() {
        return "Conge{" +
                "id=" + id +
                ", Description='" + Description + '\'' +
                ", Date_Debut=" + Date_Debut +
                ", Date_Fin=" + Date_Fin +
                ", status=" + status +
                ", Date_demande=" + Date_demande +
                ", Type_conge='" + Type_conge + '\'' +
                ", Justification='" + Justification + '\'' +
                '}';
    }
}
