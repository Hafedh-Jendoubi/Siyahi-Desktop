package tn.esprit;

import tn.esprit.models.Compte;
import tn.esprit.models.Service;
import tn.esprit.services.CompteService;
import tn.esprit.services.ServicesService;

import java.sql.Timestamp;
import java.util.List;
import java.util.Scanner;

public class Main {
    private static ServicesService serviceService = new ServicesService();
    private static CompteService compteService = new CompteService();
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        while (true) {
            System.out.println("Main Menu:");
            System.out.println("1. Service functions");
            System.out.println("2. Compte functions");
            System.out.println("3. Exit");
            System.out.print("Enter your choice: ");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    serviceMenu();
                    break;
                case 2:
                    compteMenu();
                    break;
                case 3:
                    System.out.println("Exiting...");
                    System.exit(0);
                default:
                    System.out.println("Invalid choice! Please enter a number between 1 and 3.");
            }
        }
    }

    public static void serviceMenu() {
        while (true) {
            System.out.println("\nService Menu:");
            System.out.println("1. Add service");
            System.out.println("2. Update service");
            System.out.println("3. Delete service");
            System.out.println("4. Get all services");
            System.out.println("5. Get one service");
            System.out.println("6. Return to main menu");
            System.out.print("Enter your choice: ");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    addService();
                    break;
                case 2:
                    updateService();
                    break;
                case 3:
                    deleteService();
                    break;
                case 4:
                    getAllServices();
                    break;
                case 5:
                    getOneService();
                    break;
                case 6:
                    return;
                default:
                    System.out.println("Invalid choice! Please enter a number between 1 and 6.");
            }
        }
    }

    public static void compteMenu() {
        while (true) {

            System.out.println("\nCompte Menu:");
            System.out.println("1. Add compte");
            System.out.println("2. Update compte");
            System.out.println("3. Delete compte");
            System.out.println("4. Get all comptes");
            System.out.println("5. Get one compte");
            System.out.println("6. Return to main menu");
            System.out.print("Enter your choice: ");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    addCompte();
                    break;
                case 2:
                    updateCompte();
                    break;
                case 3:
                    deleteCompte();
                    break;
                case 4:
                    getAllComptes();
                    break;
                case 5:
                    getOneCompte();
                    break;
                case 6:
                    return;
                default:
                    System.out.println("Invalid choice! Please enter a number between 1 and 6.");
            }
        }
    }

    public static void addService() {
        String nom;
        while (true) {
            System.out.println("Enter Service Name (must be alphabetic and not longer than 15 characters):");
            nom = scanner.nextLine();
            if (nom.matches("[a-zA-Z]+") && nom.length() <= 15) {
                break;
            } else {
                System.out.println("Invalid Service Name! Service Name must be alphabetic and not longer than 15 characters.");
            }
        }

        System.out.println("Enter Service Description:");
        String description = scanner.nextLine();

        Service service = new Service();
        service.setNom(nom);
        service.setDescription(description);

        serviceService.add(service);
        System.out.println("Service added successfully!");
    }


    public static void updateService() {
        System.out.println("Enter Service ID to update:");
        int id = scanner.nextInt();
        scanner.nextLine();

        // si le service existe:
        Service existingService = serviceService.getOne(id);
        if (existingService == null) {
            System.out.println("Service with ID " + id + " does not exist.");
            return;
        }

        String nom;
        while (true) {
            System.out.println("Enter New Service Name (must be alphabetic and not longer than 15 characters):");
            nom = scanner.nextLine();
            if (nom.matches("[a-zA-Z]+") && nom.length() <= 15) {
                break;
            } else {
                System.out.println("Invalid Service Name! Service Name must be alphabetic and not longer than 15 characters.");
            }
        }

        System.out.println("Enter New Service Description:");
        String description = scanner.nextLine();

        Service service = new Service();
        service.setId(id);
        service.setNom(nom);
        service.setDescription(description);

        serviceService.update(service);
        System.out.println("Service updated successfully!");
    }



    public static void deleteService() {
        System.out.println("Enter Service ID to delete:");
        int serviceId = scanner.nextInt();
        scanner.nextLine();

        Service service = new Service();
        service.setId(serviceId);

        serviceService.delete(service);
        System.out.println("Service deleted successfully!");
    }

    public static void getAllServices() {
        List<Service> services = serviceService.getAll();
        for (Service service : services) {
            System.out.println(service);
        }
    }

    public static void getOneService() {
        System.out.println("Enter Service ID to get:");
        int serviceId = scanner.nextInt();
        scanner.nextLine();

        Service service = serviceService.getOne(serviceId);
        System.out.println(service);
    }

    public static void addCompte() {
        long rib;
        while (true) {
            System.out.println("Enter RIB (must be 16 digits):");
            rib = scanner.nextLong();
            scanner.nextLine();
            if (String.valueOf(rib).length() == 16) {
                break;
            } else {
                System.out.println("Invalid RIB! RIB must be exactly 16 digits.");
            }
        }

        System.out.println("Enter ID:");
        int id = scanner.nextInt();
        scanner.nextLine();
        System.out.println("Enter Service ID:");
        int serviceId = scanner.nextInt();
        scanner.nextLine();

        double solde;
        while (true) {
            System.out.println("Enter Solde:");
            if (scanner.hasNextDouble()) {
                solde = scanner.nextDouble();
                if (solde >= 0) {
                    break;
                } else {
                    System.out.println("Invalid Solde! Solde must be a non-negative number.");
                }
            } else {
                System.out.println("Invalid input! Please enter a valid number.");
                scanner.next();
            }
        }
        scanner.nextLine();

        Service service = new Service();
        service.setId(serviceId);

        Compte compte = new Compte();
        compte.setRib(rib);
        compte.setId(id);
        compte.setService(service);
        compte.setSolde(solde);

        compteService.add(compte);
        System.out.println("Compte added successfully!");
    }


    public static void updateCompte() {
        System.out.println("Enter Compte RIB to update:");
        long rib = scanner.nextLong();
        scanner.nextLine();

        // si le compte existe:
        Compte existingCompte = compteService.getOneByRib(rib);
        if (existingCompte == null) {
            System.out.println("Compte with RIB " + rib + " does not exist.");
            return;
        }

        System.out.println("Enter New Compte ID:");
        int id = scanner.nextInt();
        scanner.nextLine();
        System.out.println("Enter New Compte Service ID:");
        int serviceId = scanner.nextInt();
        scanner.nextLine();

        double solde;
        while (true) {
            System.out.println("Enter New Compte Solde:");
            if (scanner.hasNextDouble()) {
                solde = scanner.nextDouble();
                if (solde >= 0) {
                    break;
                } else {
                    System.out.println("Invalid Solde! Solde must be a non-negative number.");
                }
            } else {
                System.out.println("Invalid input! Please enter a valid number.");
                scanner.next();
            }
        }
        scanner.nextLine();
        Service existingService = serviceService.getOne(serviceId);
        if (existingService == null) {
            System.out.println("Service with ID " + serviceId + " does not exist.");
            return;
        }

        Service service = new Service();
        service.setId(serviceId);

        Compte compte = new Compte();
        compte.setRib(rib);
        compte.setId(id);
        compte.setService(service);
        compte.setSolde(solde);

        compteService.update(compte);
        System.out.println("Compte updated successfully!");
    }



    public static void deleteCompte() {
        System.out.println("Enter Compte RIB to delete:");
        long rib = scanner.nextLong();
        scanner.nextLine();

        Compte compte = new Compte();
        compte.setRib(rib);

        compteService.delete(compte);
        System.out.println("Compte deleted successfully!");
    }

    public static void getAllComptes() {
        List<Compte> comptes = compteService.getAll();
        for (Compte compte : comptes) {
            System.out.println(compte);
        }
    }

    public static void getOneCompte() {
        System.out.println("Enter Compte RIB to get:");
        long rib = scanner.nextLong();
        scanner.nextLine();

        Compte compte = compteService.getOneByRib(rib);
        System.out.println(compte);
    }
}
