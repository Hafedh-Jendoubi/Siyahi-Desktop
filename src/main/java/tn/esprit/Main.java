package tn.esprit;

import tn.esprit.models.User;
import tn.esprit.services.UserService;

public class Main {
    public static void main(String[] args) {
        UserService us = new UserService();
        us.enableUser(43);
    }
}