package MainTest;

import Entity.Achat;
import Entity.Demande_achat;
import Service.AchatService;
import Service.Demande_achatService;


public class mainTest {
    public static void main(String[] args) {
        Achat pro1 = new Achat(6, "image_updated", "Appartement", "test java maven 2");
        AchatService as = new AchatService();
        Demande_achatService das = new Demande_achatService();
        Achat aa = as.readById(1);
//        System.out.println(aa.toString());
//        System.out.println("############# \n");
//
//        for (Achat x: as.readAll()){
//            System.out.println(x.toString());
//        }
        Demande_achat da = das.readById(2);
        System.out.println(da.toString());
        for (Demande_achat x : das.readAll()) {
            System.out.println(x.toString());
        }


        }
    }
