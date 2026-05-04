package org.example;

import org.example.dao.*;
import org.example.modeles.*;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {

    private static BookDAO livreDAO = new BookDAO();
    private static MemberDAO membreDAO = new MemberDAO();
    private static BorrowDAO empruntDAO = new BorrowDAO();
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        System.out.println("=== SYSTÈME DE GESTION DE BIBLIOTHÈQUE ===\n");

        int choix;
        do {
            afficherMenu();
            choix = lireEntier("Votre choix: ");

            switch (choix) {
                case 1: listerLivres(); break;
                case 2: chercherLivre(); break;
                case 3: ajouterLivre(); break;
                case 4: modifierLivre(); break;
                case 5: supprimerLivre(); break;
                case 6: listerMembres(); break;
                case 7: ajouterMembre(); break;
                case 8: supprimerMembre(); break;
                case 9: emprunterLivre(); break;
                case 10: retournerLivre(); break;
                case 11: voirEmprunts(); break;
                case 0: System.out.println("Au revoir !"); break;
                default: System.out.println("Choix invalide !");
            }
        } while (choix != 0);

        scanner.close();
    }

    private static void afficherMenu() {
        System.out.println("\n=== MENU PRINCIPAL ===");
        System.out.println("1. Lister les livres");
        System.out.println("2. Chercher un livre");
        System.out.println("3. Ajouter un livre");
        System.out.println("4. Modifier un livre");
        System.out.println("5. Supprimer un livre");
        System.out.println("6. Lister les membres");
        System.out.println("7. Ajouter un membre");
        System.out.println("8. Supprimer un membre");
        System.out.println("9. Emprunter un livre");
        System.out.println("10. Retourner un livre");
        System.out.println("11. Voir les emprunts");
        System.out.println("0. Quitter");
        System.out.print("Votre choix: ");
    }

    private static int lireEntier(String message) {
        System.out.print(message);
        while (!scanner.hasNextInt()) {
            System.out.print("Entrez un nombre valide: ");
            scanner.next();
        }
        int valeur = scanner.nextInt();
        scanner.nextLine();
        return valeur;
    }

    private static void listerLivres() {
        System.out.println("\n--- Liste des livres ---");
        ArrayList<Book> livres = livreDAO.findAll();
        if (livres.isEmpty()) {
            System.out.println("Aucun livre trouvé.");
        } else {
            for (Book livre : livres) {
                System.out.println(livre);
            }
        }
    }

    private static void chercherLivre() {
        System.out.print("\nEntrez le titre: ");
        String titre = scanner.nextLine();
        ArrayList<Book> livres = livreDAO.findByTitre(titre);
        if (livres.isEmpty()) {
            System.out.println("Aucun livre trouv�.");
        } else {
            for (Book livre : livres) {
                System.out.println(livre);
            }
        }
    }

    private static void ajouterLivre() {
        System.out.println("\n--- Ajouter un livre ---");
        System.out.print("Titre: ");
        String titre = scanner.nextLine();
        System.out.print("Auteur: ");
        String auteur = scanner.nextLine();
        System.out.print("Année: ");
        int annee = lireEntier("");

        Book livre = new Book(titre, auteur, annee, true);
        if (livreDAO.insert(livre)) {
            System.out.println("Livre ajout� !");
        } else {
            System.out.println("Erreur d'ajout.");
        }
    }

    private static void modifierLivre() {
        System.out.println("\n--- Modifier un livre ---");
        int id = lireEntier("ID du livre: ");

        Book livre = livreDAO.findById(id);
        if (livre == null) {
            System.out.println("Livre non trouv�.");
            return;
        }

        System.out.println("Livre actuel: " + livre);
        System.out.print("Nouveau titre (vide = pas de changement): ");
        String titre = scanner.nextLine();
        if (!titre.isEmpty()) livre.setTitre(titre);

        System.out.print("Nouvel auteur (vide = pas de changement): ");
        String auteur = scanner.nextLine();
        if (!auteur.isEmpty()) livre.setAuteur(auteur);

        System.out.print("Nouvelle ann�e (0 = pas de changement): ");
        int annee = lireEntier("");
        if (annee > 0) livre.setAnnee(annee);

        if (livreDAO.update(livre)) {
            System.out.println("Livre modifi� !");
        } else {
            System.out.println("Erreur de modification.");
        }
    }

    private static void supprimerLivre() {
        System.out.println("\n--- Supprimer un livre ---");
        int id = lireEntier("ID du livre: ");
        if (livreDAO.delete(id)) {
            System.out.println("Livre supprim� !");
        }
    }

    private static void listerMembres() {
        System.out.println("\n--- Liste des membres ---");
        ArrayList<Member> membres = membreDAO.findAll();
        if (membres.isEmpty()) {
            System.out.println("Aucun membre.");
        } else {
            for (Member membre : membres) {
                System.out.println(membre);
            }
        }
    }

    private static void ajouterMembre() {
        System.out.println("\n--- Ajouter un membre ---");
        System.out.print("Nom: ");
        String nom = scanner.nextLine();
        System.out.print("Email: ");
        String email = scanner.nextLine();

        Member membre = new Member(nom, email);
        if (membreDAO.insert(membre)) {
            System.out.println("Membre ajout� !");
        } else {
            System.out.println("Erreur d'ajout.");
        }
    }

    private static void supprimerMembre() {
        System.out.println("\n--- Supprimer un membre ---");
        int id = lireEntier("ID du membre: ");
        if (membreDAO.delete(id)) {
            System.out.println("Membre supprim� !");
        }
    }

    private static void emprunterLivre() {
        System.out.println("\n--- Emprunter un livre ---");

        System.out.println("\nLivres disponibles:");
        ArrayList<Book> livres = livreDAO.findAll();
        boolean dispo = false;
        for (Book livre : livres) {
            if (livre.isDisponible()) {
                System.out.println(livre);
                dispo = true;
            }
        }
        if (!dispo) {
            System.out.println("Aucun livre disponible.");
            return;
        }

        System.out.println("\nMembres:");
        ArrayList<Member> membres = membreDAO.findAll();
        if (membres.isEmpty()) {
            System.out.println("Aucun membre.");
            return;
        }
        for (Member membre : membres) {
            System.out.println(membre);
        }

        int idLivre = lireEntier("\nID du livre: ");
        int idMembre = lireEntier("ID du membre: ");

        java.time.LocalDate aujourdhui = java.time.LocalDate.now();
        String dateEmprunt = aujourdhui.format(java.time.format.DateTimeFormatter.ofPattern("dd/MM/yyyy"));

        empruntDAO.emprunter(idLivre, idMembre, dateEmprunt);
    }

    private static void retournerLivre() {
        System.out.println("\n--- Retourner un livre ---");
        ArrayList<String> emprunts = empruntDAO.findAll();
        if (emprunts.isEmpty()) {
            System.out.println("Aucun emprunt.");
            return;
        }

        System.out.println("Emprunts en cours:");
        for (String emprunt : emprunts) {
            System.out.println(emprunt);
        }

        int idEmprunt = lireEntier("\nID de l'emprunt: ");
        empruntDAO.retourner(idEmprunt);
    }

    private static void voirEmprunts() {
        System.out.println("\n--- Emprunts en cours ---");
        ArrayList<String> emprunts = empruntDAO.findAll();
        if (emprunts.isEmpty()) {
            System.out.println("Aucun emprunt.");
        } else {
            for (String emprunt : emprunts) {
                System.out.println(emprunt);
            }
        }
    }
}