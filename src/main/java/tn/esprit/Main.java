package org.example;
import org.example.entities.Credit;
import org.example.services.CreditService;
import org.example.entities.ReponseCredit;
import org.example.services.ReponseCreditService;

import org.example.util.MaConnexion;

import java.util.Calendar;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        CreditService Cs = new CreditService();
        // Création de la date correctement
        java.sql.Date date = new java.sql.Date(2026 - 1900, Calendar.DECEMBER, 12);


        //Ajout d'un credit
      /*

        try {

            // Ajout d'un crédit
            Credit credit = new Credit(0, "j en ai besoin", "", 1000, date);
            Cs.Insert(credit);
        } catch (IllegalArgumentException e) {
            System.out.println("Erreur : " + e.getMessage());
        }
     */
        //Récupération de tous les crédits
        //System.out.println(Cs.getAll());

        // Récupération d'un crédit par son id
    /*    Credit creditRecupere = Cs.getOne(23);
        if (creditRecupere != null) {
            System.out.println("Crédit récupéré : " + creditRecupere);
        } else {
            System.out.println("Aucun crédit trouvé pour l'identifiant spécifié.");
        }
    */
        // Mise à jour d'un crédit
      /*
        try {
        Credit creditToUpdate = new Credit(56,36, "j en ai tres besoin", "", 900, date);
        Cs.Update(creditToUpdate);
        } catch (IllegalArgumentException e) {
            System.out.println("Erreur : " + e.getMessage());
        }
        */

        // Suppression d'un crédit
        /*
        Credit creditToDelete = new Credit(55,22, "null", null, 100, date);
        Cs.Delete(creditToDelete);
        */
//-------------------------------------------------ReponseCredit---------------------------------------------------------

        ReponseCreditService reponseCreditService = new ReponseCreditService();

        // Création de la date correctement
        // java.sql.Date date = new java.sql.Date(2025 - 1900, Calendar.DECEMBER, 27);


        // Ajout d'une réponse de crédit
    /*
        ReponseCredit reponseCreditToAdd = new ReponseCredit(36, "description", 50000, date);
        reponseCreditService.Add(reponseCreditToAdd);
    */
        // Récupération de tous les réponses de crédit
    /*
        List<ReponseCredit> allReponsesCredit = reponseCreditService.getAll();
        System.out.println("Toutes les réponses de crédit : " + allReponsesCredit);
    */
        // Récupération d'une réponse de crédit par son id
    /*
        ReponseCredit reponseCreditById = reponseCreditService.getOne(28);

        if (reponseCreditById != null) {
            System.out.println("Réponse de crédit récupérée par id : " + reponseCreditById);
        } else {
            System.out.println("Aucune réponse de crédit trouvée pour l'identifiant spécifié.");
        }
    */
        // Mise à jour d'une réponse de crédit
    /*
        ReponseCredit reponseCreditToUpdate = new ReponseCredit(19,36, "nouvelle description", 60000, date);
        reponseCreditService.Update(reponseCreditToUpdate);
    */
        // Suppression d'une réponse de crédit
    /*
        ReponseCredit reponseCreditToDelete = new ReponseCredit(25,36, null, 0, null);
        reponseCreditService.Delete(reponseCreditToDelete);
    */
    }

}
