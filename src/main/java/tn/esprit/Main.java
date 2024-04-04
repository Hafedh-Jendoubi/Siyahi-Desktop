package tn.esprit;
import tn.esprit.models.Conge;

import tn.esprit.services.CongeService;

import tn.esprit.util.MaConnexion;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;


public class Main {
    public static void main(String[] args) {
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());




        MaConnexion mac =  MaConnexion.getInstance();

        CongeService rs = new CongeService();
        Conge conge = new Conge("aahh",
                java.sql.Date.valueOf("2021-02-20"),
                java.sql.Date.valueOf("2021-02-20"),
                timestamp,
                "aaaaa",
                "",
                true);
        rs.add(conge);
    }
}