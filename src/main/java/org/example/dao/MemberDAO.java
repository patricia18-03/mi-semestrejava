package org.example.dao;

import org.example.modeles.Member;
import org.example.DBConnexion;
import java.sql.*;
import java.util.ArrayList;

public class MemberDAO {

    public ArrayList<Member> findAll() {
        ArrayList<Member> membres = new ArrayList<>();
        String sql = "SELECT id, nom, email FROM membres ORDER BY nom";

        try (Connection conn = DBConnexion.getConnexion();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Member membre = new Member();
                membre.setId(rs.getInt("id"));
                membre.setNom(rs.getString("nom"));
                membre.setEmail(rs.getString("email"));
                membres.add(membre);
            }
        } catch (SQLException e) {
            System.out.println("Erreur findAll membres: " + e.getMessage());
        }
        return membres;
    }

    public Member findById(int id) {
        String sql = "SELECT id, nom, email FROM membres WHERE id = ?";

        try (Connection conn = DBConnexion.getConnexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                Member membre = new Member();
                membre.setId(rs.getInt("id"));
                membre.setNom(rs.getString("nom"));
                membre.setEmail(rs.getString("email"));
                return membre;
            }
        } catch (SQLException e) {
            System.out.println("Erreur findById membre: " + e.getMessage());
        }
        return null;
    }

    public boolean insert(Member membre) {
        String sql = "INSERT INTO membres (nom, email) VALUES (?, ?)";

        try (Connection conn = DBConnexion.getConnexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, membre.getNom());
            stmt.setString(2, membre.getEmail());

            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Erreur insert membre: " + e.getMessage());
            return false;
        }
    }

    public boolean delete(int id) {
        String checkSql = "SELECT COUNT(*) FROM emprunts WHERE id_membre = ?";

        try (Connection conn = DBConnexion.getConnexion()) {
            PreparedStatement checkStmt = conn.prepareStatement(checkSql);
            checkStmt.setInt(1, id);
            ResultSet rs = checkStmt.executeQuery();
            rs.next();

            if (rs.getInt(1) > 0) {
                System.out.println("Impossible: ce membre a des emprunts en cours !");
                return false;
            }

            String deleteSql = "DELETE FROM membres WHERE id = ?";
            PreparedStatement deleteStmt = conn.prepareStatement(deleteSql);
            deleteStmt.setInt(1, id);
            return deleteStmt.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("Erreur delete membre: " + e.getMessage());
            return false;
        }
    }
}