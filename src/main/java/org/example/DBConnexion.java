package org.example;

import org.example.config.ConfigLoader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnexion {

    public static Connection getConnexion() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            ConfigLoader config = new ConfigLoader();

            String url = config.getUrl();
            String username = config.getUsername();
            String password = config.getPassword();

            System.out.println("Tentative de connexion à : " + url);
            System.out.println("Utilisateur : " + username);

            Connection conn = DriverManager.getConnection(url, username, password);

            if (conn != null) {
                System.out.println("Connexion MySQL réussie !");
            }

            return conn;

        } catch (ClassNotFoundException e) {
            System.out.println("ERREUR: Driver MySQL non trouvé !");
            System.out.println("Vérifiez que mysql-connector-j est dans pom.xml");
            return null;
        } catch (SQLException e) {
            System.out.println("ERREUR de connexion MySQL !");
            System.out.println("Message: " + e.getMessage());
            System.out.println("\nSolutions possibles:");
            System.out.println("1. Vérifiez que MySQL est démarré (XAMPP/WAMP)");
            System.out.println("2. Vérifiez le mot de passe dans config/database.json");
            System.out.println("3. Vérifiez que la base de données 'bibliotheque' existe");
            return null;
        }
    }
}