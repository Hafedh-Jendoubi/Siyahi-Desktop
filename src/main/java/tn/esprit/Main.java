package tn.esprit;
import tn.esprit.models.ReponseConge;
import tn.esprit.models.Conge;

import tn.esprit.services.CongeService;
import tn.esprit.services.ReponseCongeService;

import tn.esprit.util.MaConnexion;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;


public class Main {
    public static void main(String[] args) {
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());


        MaConnexion mac = MaConnexion.getInstance();

        CongeService rs = new CongeService();
      /* try {
            Conge conge = new Conge("aav",
                    java.sql.Date.valueOf("2021-02-20"),
                    java.sql.Date.valueOf("2021-02-30"),

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


        CongeService congeService = new CongeService();

        try {
            Conge congeToUpdate = new Conge("j en ai tres besoin", java.sql.Date.valueOf("2021-02-22"), java.sql.Date.valueOf("2021-02-30"), "malade", "aaa", true);
            congeService.update(congeToUpdate);
        } catch (IllegalArgumentException e) {
            System.out.println("Erreur : " + e.getMessage());
            // Suppression d'un crédit
        /*Conge congeToDelete = new Conge(14, null, null, null, null, null,null,true);

        rs.delete(congeToDelete);
        */
            /*****************************************************************************************/
            ReponseCongeService rc = new ReponseCongeService();




       /*try {

           ReponseConge reponseconge = new ReponseConge("abbbn",
                   java.sql.Date.valueOf("2021-02-20"),
                   java.sql.Date.valueOf("2021-02-30"),
                   timestamp);
            rc.add(reponseconge);
            System.out.println("Reponseconge ajoutée avec succès.");
        } catch (IllegalArgumentException e) {
            System.out.println("Erreur : " + e.getMessage());
        }*/
            //Récupération de tous les congés
            /* System.out.println(rc.getAll());*/

        /*try {
        ReponseConge congeToUpdate = new ReponseConge( 1,"j en ai tres besoin",  java.sql.Date.valueOf("2021-02-22"),java.sql.Date.valueOf("2021-02-30"),new Timestamp(System.currentTimeMillis()));
        rc.update(congeToUpdate);
        } catch (IllegalArgumentException e) {
            System.out.println("Erreur : " + e.getMessage());
        }*/

        }
    }}

