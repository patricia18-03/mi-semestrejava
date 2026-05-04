package org.example.modeles;

public class Book {
    private int id;
    private String titre;
    private String auteur;
    private int annee;
    private boolean disponible;

    public Book() {}

    public Book(int id, String titre, String auteur, int annee, boolean disponible) {
        this.id = id;
        this.titre = titre;
        this.auteur = auteur;
        this.annee = annee;
        this.disponible = disponible;
    }

    public Book(String titre, String auteur, int annee, boolean disponible) {
        this.titre = titre;
        this.auteur = auteur;
        this.annee = annee;
        this.disponible = disponible;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getTitre() { return titre; }
    public void setTitre(String titre) { this.titre = titre; }

    public String getAuteur() { return auteur; }
    public void setAuteur(String auteur) { this.auteur = auteur; }

    public int getAnnee() { return annee; }
    public void setAnnee(int annee) { this.annee = annee; }

    public boolean isDisponible() { return disponible; }
    public void setDisponible(boolean disponible) { this.disponible = disponible; }

    @Override
    public String toString() {
        return "Book{id=" + id + ", titre='" + titre + "', auteur='" + auteur +
                "', annee=" + annee + ", disponible=" + disponible + "}";
    }
}