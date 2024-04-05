package tn.esprit;
import tn.esprit.models.ReponseConge;

import tn.esprit.services.CongeService;
import tn.esprit.services.ReponseCongeService;

import tn.esprit.util.MaConnexion;

import java.sql.Timestamp;


public class Main {
    public static void main(String[] args) {
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());


        MaConnexion mac = MaConnexion.getInstance();

        CongeService rs = new CongeService();
       /* try {
            Conge conge = new Conge("aahh",
                    java.sql.Date.valueOf("2021-02-20"),
                    java.sql.Date.valueOf("2021-02-30"),
                    timestamp,
                    "aaaaa",
                    "",
                    true);
            rs.add(conge);
        } catch (IllegalArgumentException e) {
            System.out.println("Erreur : " + e.getMessage());
        }
*/
        //Récupération de tous les congés
       /* System.out.println(rs.getAll());

        // Récupération d'un conge par son id
        Conge congerecupere = rs.getOne(1);
        if (congerecupere != null) {
            System.out.println("Conge récupéré : " + congerecupere);
        } else {
            System.out.println("Aucun conge trouvé pour l'identifiant spécifié.");
        }
*/
        // Mise à jour d'un conge


       /* CongeService congeService = new CongeService();

        // Création d'un objet SimpleDateFormat pour analyser les chaînes de date
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        // Création d'un objet Conge avec les nouvelles valeurs à mettre à jour
        Conge congeToUpdate = new Conge();
        congeToUpdate.setId(1);  // ID de l'objet à mettre à jour
        congeToUpdate.setDescription("Nouvelle description");
        try {
            congeToUpdate.setDate_Debut(new java.sql.Date(dateFormat.parse("2024-04-01").getTime())); // Nouvelle date de début
            congeToUpdate.setDate_Fin(new java.sql.Date(dateFormat.parse("2024-04-05").getTime()));   // Nouvelle date de fin
        } catch (ParseException e) {
            e.printStackTrace();
        }
        congeToUpdate.setDate_demande(new Timestamp(System.currentTimeMillis())); // Nouvelle date de demande
        congeToUpdate.setType_conge("Nouveau type de congé");
        congeToUpdate.setJustification("Nouvelle justification");
        congeToUpdate.setStatus(true);  // Nouveau statut

        // Appel de la méthode update
        congeService.update(congeToUpdate);

        // Affichage du résultat
        System.out.println("Congé mis à jour avec succès !");
*/
        // Suppression d'un crédit
        /*Conge congeToDelete = new Conge(14, null, null, null, null, null,null,true);

        rs.delete(congeToDelete);
        */
        /*****************************************************************************************/
        ReponseCongeService rc = new ReponseCongeService();




       /* try {
            ReponseConge reponseconge = new ReponseConge(
            );
            rc.add(reponseconge);
            System.out.println("Reponseconge ajoutée avec succès.");
        } catch (IllegalArgumentException e) {
            System.out.println("Erreur : " + e.getMessage());
        }*/
        //Récupération de tous les congés
        System.out.println(rc.getAll());
    }
        }

