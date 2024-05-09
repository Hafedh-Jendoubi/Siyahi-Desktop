package tn.esprit.models;

import tn.esprit.models.ObjetReclamation;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class Reclamation {

    private int id;

    private int user_id;
    private ObjetReclamation object;
    private String description;
    private Timestamp date_creation;
    private String auteur;
    private String email;
    private boolean status;

    private List<ReponseReclamation> reponses;

    public List<ReponseReclamation> getReponses() {
        return reponses;
    }

    public void setReponses(List<ReponseReclamation> reponses) {
        this.reponses = reponses;
    }

    // Liste d'objets suggérés
    private static List<ObjetReclamation> objetsSuggérés = new ArrayList<>();

    static {
        // Ajoutez ici les objets suggérés, par exemple :
        objetsSuggérés.add(new ObjetReclamation("Frais Bancaires Inattendus"));
        objetsSuggérés.add(new ObjetReclamation("Blocage de Carte Bancaire"));
        objetsSuggérés.add(new ObjetReclamation("Virement Non Reçu"));
        objetsSuggérés.add(new ObjetReclamation("Demande Extrait"));
    }

    public Reclamation() {
    }

    public Reclamation(ObjetReclamation object, String description, Timestamp date_creation, String auteur, String email, boolean status, int user_id) {
        setObject(object);
        setDescription(description);
        setAuteur(auteur);
        setEmail(email);
        this.date_creation = date_creation;
        this.status = status;
        this.user_id = user_id;
    }

    public Reclamation(int id, ObjetReclamation object, String description, Timestamp date_creation, String auteur, String email, boolean status) {
        this.id = id;
        this.object = object;
        this.description = description;
        this.date_creation = date_creation;
        this.auteur = auteur;
        this.email = email;
        this.status = status;
    }

    public Reclamation(String nom, String description, Timestamp dateCreation, String auteur, String email, boolean status) {
    }

    public Reclamation(int id, String objet, String description, String dateCreation, String auteur, String email) {
    }

    public Reclamation(int id, String objet, String description, Timestamp dateCreation, String auteur, String email) {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public ObjetReclamation getObject() {
        return object;
    }

    public void setObject(ObjetReclamation object) {
        // Vous pouvez ajouter une validation ici si nécessaire
        this.object = object;
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

    public String getAuteur() {
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

    // Méthode pour obtenir la liste des objets suggérés
    public static List<ObjetReclamation> getObjetsSuggérés() {
        return objetsSuggérés;
    }

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
