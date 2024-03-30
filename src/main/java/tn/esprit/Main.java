package tn.esprit;

import tn.esprit.models.Reclamation;
import tn.esprit.models.ReponseReclamation;
import tn.esprit.services.ReclamationService;
import tn.esprit.services.ReponseReclamationService;
import tn.esprit.util.MaConnexion;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;


public class Main {
    public static void main(String[] args) {
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());




        MaConnexion mac =  MaConnexion.getInstance();
        //********************test add:
        //ReclamationService rs=new ReclamationService();
        //Reclamation reclamation = new Reclamation("Objet de la réclamation", "Description de la réclamation", timestamp, "Auteur de la réclamation", "Email de l'auteur",true);
         //rs.add(reclamation);

        //************************test update

        //Reclamation reclamationToUpdate = new Reclamation(50,"aa","bb",new Timestamp(System.currentTimeMillis()),"aut","eml",true);

        //ReclamationService service = new ReclamationService();
        //service.update(reclamationToUpdate);

        //*********************test delete********************

        /*Reclamation reclamationToDelete = new Reclamation(51, null, null, null, null, null,true);
        ReclamationService service = new ReclamationService();
        service.delete(reclamationToDelete);*/


        //****test get all*****//

        /**ReclamationService service = new ReclamationService();
        // Appelez la méthode getAll()
        List<Reclamation> reclamations = service.getAll();
        //Parcourez la liste des réclamations et affichez-les ou traitez-les
        for (Reclamation reclamation : reclamations) {
            System.out.println(reclamation);
        }*/


        // Créer un objet ReponseReclamationService
        ReponseReclamationService service = new ReponseReclamationService();

        // Tester la méthode add()
        ReponseReclamation nouvelleReponse = new ReponseReclamation("Contenu de la nouvelle réponse", new Timestamp(System.currentTimeMillis()), "Auteur de la nouvelle réponse");
        service.add(nouvelleReponse);





    }

    }
