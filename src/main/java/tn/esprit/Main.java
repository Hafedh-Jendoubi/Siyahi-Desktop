package tn.esprit;

import tn.esprit.models.User;
import tn.esprit.services.UserService;

public class Main {
    public static void main(String[] args) {
        UserService us = new UserService();
        User user = new User("a", "b", "M", "adr", 1, 3, "a.b@gmail.com");
        us.add(user);
    }
}