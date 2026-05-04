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

            Connection conn = DriverManager.getConnection(
                    config.getUrl(),
                    config.getUsername(),
                    config.getPassword()
            );
            return conn;

        } catch (ClassNotFoundException e) {
            System.out.println("Driver MySQL non trouvé !");
            return null;
        } catch (SQLException e) {
            System.out.println("Erreur de connexion MySQL !");
            return null;
        }
    }
}