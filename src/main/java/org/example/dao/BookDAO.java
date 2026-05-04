package org.example.dao;

import org.example.modeles.Book;
import org.example.DBConnexion;
import java.sql.*;
import java.util.ArrayList;

public class BookDAO {

    public ArrayList<Book> findAll() {
        ArrayList<Book> livres = new ArrayList<>();
        String sql = "SELECT id, titre, auteur, annee, disponible FROM livres ORDER BY id";

        try (Connection conn = DBConnexion.getConnexion();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Book livre = new Book();
                livre.setId(rs.getInt("id"));
                livre.setTitre(rs.getString("titre"));
                livre.setAuteur(rs.getString("auteur"));
                livre.setAnnee(rs.getInt("annee"));
                livre.setDisponible(rs.getBoolean("disponible"));
                livres.add(livre);
            }
        } catch (SQLException e) {
            System.out.println("Erreur findAll: " + e.getMessage());
        }
        return livres;
    }

    public Book findById(int id) {
        String sql = "SELECT id, titre, auteur, annee, disponible FROM livres WHERE id = ?";

        try (Connection conn = DBConnexion.getConnexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                Book livre = new Book();
                livre.setId(rs.getInt("id"));
                livre.setTitre(rs.getString("titre"));
                livre.setAuteur(rs.getString("auteur"));
                livre.setAnnee(rs.getInt("annee"));
                livre.setDisponible(rs.getBoolean("disponible"));
                return livre;
            }
        } catch (SQLException e) {
            System.out.println("Erreur findById: " + e.getMessage());
        }
        return null;
    }

    public ArrayList<Book> findByTitre(String titre) {
        ArrayList<Book> livres = new ArrayList<>();
        String sql = "SELECT id, titre, auteur, annee, disponible FROM livres WHERE titre LIKE ?";

        try (Connection conn = DBConnexion.getConnexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, "%" + titre + "%");
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Book livre = new Book();
                livre.setId(rs.getInt("id"));
                livre.setTitre(rs.getString("titre"));
                livre.setAuteur(rs.getString("auteur"));
                livre.setAnnee(rs.getInt("annee"));
                livre.setDisponible(rs.getBoolean("disponible"));
                livres.add(livre);
            }
        } catch (SQLException e) {
            System.out.println("Erreur findByTitre: " + e.getMessage());
        }
        return livres;
    }

    public boolean insert(Book livre) {
        String sql = "INSERT INTO livres (titre, auteur, annee, disponible) VALUES (?, ?, ?, ?)";

        try (Connection conn = DBConnexion.getConnexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, livre.getTitre());
            stmt.setString(2, livre.getAuteur());
            stmt.setInt(3, livre.getAnnee());
            stmt.setBoolean(4, livre.isDisponible());

            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Erreur insert: " + e.getMessage());
            return false;
        }
    }

    public boolean update(Book livre) {
        String sql = "UPDATE livres SET titre = ?, auteur = ?, annee = ? WHERE id = ?";

        try (Connection conn = DBConnexion.getConnexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, livre.getTitre());
            stmt.setString(2, livre.getAuteur());
            stmt.setInt(3, livre.getAnnee());
            stmt.setInt(4, livre.getId());

            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Erreur update: " + e.getMessage());
            return false;
        }
    }

    public boolean updateDisponibilite(int id, boolean disponible) {
        String sql = "UPDATE livres SET disponible = ? WHERE id = ?";

        try (Connection conn = DBConnexion.getConnexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setBoolean(1, disponible);
            stmt.setInt(2, id);

            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Erreur updateDisponibilite: " + e.getMessage());
            return false;
        }
    }

    public boolean delete(int id) {
        String checkSql = "SELECT COUNT(*) FROM emprunts WHERE id_livre = ?";

        try (Connection conn = DBConnexion.getConnexion()) {
            PreparedStatement checkStmt = conn.prepareStatement(checkSql);
            checkStmt.setInt(1, id);
            ResultSet rs = checkStmt.executeQuery();
            rs.next();

            if (rs.getInt(1) > 0) {
                System.out.println("Impossible: ce livre est emprunté !");
                return false;
            }

            String deleteSql = "DELETE FROM livres WHERE id = ?";
            PreparedStatement deleteStmt = conn.prepareStatement(deleteSql);
            deleteStmt.setInt(1, id);
            return deleteStmt.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("Erreur delete: " + e.getMessage());
            return false;
        }
    }
}