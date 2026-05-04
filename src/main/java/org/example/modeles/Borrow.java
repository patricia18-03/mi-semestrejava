package org.example.modeles;

public class Borrow {
    private int id;
    private int idLivre;
    private int idMembre;
    private String dateEmprunt;

    public Borrow() {}

    public Borrow(int id, int idLivre, int idMembre, String dateEmprunt) {
        this.id = id;
        this.idLivre = idLivre;
        this.idMembre = idMembre;
        this.dateEmprunt = dateEmprunt;
    }

    public Borrow(int idLivre, int idMembre, String dateEmprunt) {
        this.idLivre = idLivre;
        this.idMembre = idMembre;
        this.dateEmprunt = dateEmprunt;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getIdLivre() { return idLivre; }
    public void setIdLivre(int idLivre) { this.idLivre = idLivre; }

    public int getIdMembre() { return idMembre; }
    public void setIdMembre(int idMembre) { this.idMembre = idMembre; }

    public String getDateEmprunt() { return dateEmprunt; }
    public void setDateEmprunt(String dateEmprunt) { this.dateEmprunt = dateEmprunt; }

    @Override
    public String toString() {
        return "Borrow{id=" + id + ", livre=" + idLivre + ", membre=" + idMembre +
                ", date='" + dateEmprunt + "'}";
    }
}