package org.example.dao;

import org.example.DBConnexion;
import java.sql.*;
import java.util.ArrayList;

public class BorrowDAO {
    private BookDAO bookDAO = new BookDAO();

    public boolean emprunter(int idLivre, int idMembre, String dateEmprunt) {
        var livre = bookDAO.findById(idLivre);

        if (livre == null) {
            System.out.println("Livre non trouvé !");
            return false;
        }

        if (!livre.isDisponible()) {
            System.out.println("Ce livre n'est pas disponible !");
            return false;
        }

        String sqlInsert = "INSERT INTO emprunts (id_livre, id_membre, date_emprunt) VALUES (?, ?, ?)";

        try (Connection conn = DBConnexion.getConnexion()) {
            conn.setAutoCommit(false);

            PreparedStatement insertStmt = conn.prepareStatement(sqlInsert);
            insertStmt.setInt(1, idLivre);
            insertStmt.setInt(2, idMembre);
            insertStmt.setString(3, dateEmprunt);
            int insertResult = insertStmt.executeUpdate();

            boolean updateResult = bookDAO.updateDisponibilite(idLivre, false);

            if (insertResult > 0 && updateResult) {
                conn.commit();
                System.out.println("Emprunt r�ussi !");
                return true;
            } else {
                conn.rollback();
                return false;
            }
        } catch (SQLException e) {
            System.out.println("Erreur emprunt: " + e.getMessage());
            return false;
        }
    }

    public boolean retourner(int idEmprunt) {
        String selectSql = "SELECT id_livre FROM emprunts WHERE id = ?";
        String deleteSql = "DELETE FROM emprunts WHERE id = ?";

        try (Connection conn = DBConnexion.getConnexion()) {
            conn.setAutoCommit(false);

            PreparedStatement selectStmt = conn.prepareStatement(selectSql);
            selectStmt.setInt(1, idEmprunt);
            ResultSet rs = selectStmt.executeQuery();

            if (!rs.next()) {
                System.out.println("Emprunt non trouvé !");
                conn.rollback();
                return false;
            }

            int idLivre = rs.getInt("id_livre");

            PreparedStatement deleteStmt = conn.prepareStatement(deleteSql);
            deleteStmt.setInt(1, idEmprunt);
            int deleteResult = deleteStmt.executeUpdate();

            boolean updateResult = bookDAO.updateDisponibilite(idLivre, true);

            if (deleteResult > 0 && updateResult) {
                conn.commit();
                System.out.println("Retour r�ussi !");
                return true;
            } else {
                conn.rollback();
                return false;
            }
        } catch (SQLException e) {
            System.out.println("Erreur retour: " + e.getMessage());
            return false;
        }
    }

    public ArrayList<String> findAll() {
        ArrayList<String> empruntsList = new ArrayList<>();
        String sql = "SELECT e.id, l.titre AS titre_livre, m.nom AS nom_membre, e.date_emprunt " +
                "FROM emprunts e " +
                "JOIN livres l ON e.id_livre = l.id " +
                "JOIN membres m ON e.id_membre = m.id " +
                "ORDER BY e.id";

        try (Connection conn = DBConnexion.getConnexion();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                String ligne = "Emprunt #" + rs.getInt("id") +
                        " | " + rs.getString("titre_livre") +
                        " | " + rs.getString("nom_membre") +
                        " | " + rs.getString("date_emprunt");
                empruntsList.add(ligne);
            }
        } catch (SQLException e) {
            System.out.println("Erreur findAll emprunts: " + e.getMessage());
        }
        return empruntsList;
    }
}