package tn.esprit.models;

import java.sql.Timestamp;

public class Reclamation {

    //attribut
    private int id;
    private String object;
    private String description;
    private Timestamp date_creation;
    private String auteur;
    private String email;
    private boolean status;

    //const


    public Reclamation() {
    }

    public Reclamation(String object, String description, Timestamp date_creation, String auteur, String email, boolean status) {
        setObject(object);
        setDescription(description);
        setAuteur(auteur); // Appeler la méthode setAuteur() pour valider et définir l'auteur
        setEmail(email); // Appeler la méthode setEmail() pour valider et définir l'email
        this.date_creation = date_creation;
        this.status = status;
    }

    public Reclamation(int id, String object, String description, Timestamp date_creation, String auteur, String email, boolean status) {
        this.id = id;
        this.object = object;
        this.description = description;
        this.date_creation = date_creation;
        this.auteur = auteur;
        this.email = email;
        this.status = status;
    }

    //getter setters

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getObject() {
        return object;
    }

    public void setObject(String object) {
        // Validation : Vérifier que l'objet n'est pas vide
        if (object != null && !object.trim().isEmpty()) {
            this.object = object;
        } else {
            throw new IllegalArgumentException("L'objet de la réclamation ne peut pas être vide.");
        }
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        // Validation : Vérifier que la description n'est pas vide
        if (description != null && !description.trim().isEmpty()) {
            this.description = description;
        } else {
            throw new IllegalArgumentException("La description de la réclamation ne peut pas être vide.");
        }
    }
    public Timestamp getDate_creation() {
        return date_creation;
    }

    public void setDate_creation(Timestamp date_creation) {
        this.date_creation = date_creation;
    }

    public String getauteur() {
        return auteur;
    }

    public void setAuteur(String auteur) {
        // Validation : Vérifier que l'auteur n'est pas vide
        if (auteur != null && !auteur.trim().isEmpty()) {
            this.auteur = auteur;
        } else {
            throw new IllegalArgumentException("L'auteur de la réclamation ne peut pas être vide.");
        }
    }
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        // Validation : Vérifier que l'email est valide (vous pouvez utiliser une expression régulière pour cela)
        if (email != null && email.matches("^\\S+@\\S+\\.\\S+$")) {
            this.email = email;
        } else {
            throw new IllegalArgumentException("Veuillez saisir une adresse email valide.");
        }
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    //display

    @Override
    public String toString() {
        return "Reclamation{" +
                "id=" + id +
                ", object='" + object + '\'' +
                ", description='" + description + '\'' +
                ", date_creation=" + date_creation +
                ", auteur='" + auteur + '\'' +
                ", status=" + status +
                ", email='" + email + '\'' +

                '}';
    }
}
