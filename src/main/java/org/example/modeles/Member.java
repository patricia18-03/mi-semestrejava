package org.example.modeles;

public class Member {
    private int id;
    private String nom;
    private String email;

    public Member() {}

    public Member(int id, String nom, String email) {
        this.id = id;
        this.nom = nom;
        this.email = email;
    }

    public Member(String nom, String email) {
        this.nom = nom;
        this.email = email;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getNom() { return nom; }
    public void setNom(String nom) { this.nom = nom; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    @Override
    public String toString() {
        return "Member{id=" + id + ", nom='" + nom + "', email='" + email + "'}";
    }
}