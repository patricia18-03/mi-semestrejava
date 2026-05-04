CREATE DATABASE IF NOT EXISTS bibliotheque;
USE bibliotheque;

CREATE TABLE IF NOT EXISTS livres (
                                      id INT PRIMARY KEY AUTO_INCREMENT,
                                      titre VARCHAR(200) NOT NULL,
    auteur VARCHAR(100) NOT NULL,
    annee INT NOT NULL,
    disponible BOOLEAN DEFAULT TRUE
    );

CREATE TABLE IF NOT EXISTS membres (
                                       id INT PRIMARY KEY AUTO_INCREMENT,
                                       nom VARCHAR(100) NOT NULL,
    email VARCHAR(100) UNIQUE NOT NULL
    );

CREATE TABLE IF NOT EXISTS emprunts (
                                        id INT PRIMARY KEY AUTO_INCREMENT,
                                        id_livre INT NOT NULL,
                                        id_membre INT NOT NULL,
                                        date_emprunt VARCHAR(20) NOT NULL,
    FOREIGN KEY (id_livre) REFERENCES livres(id) ON DELETE CASCADE,
    FOREIGN KEY (id_membre) REFERENCES membres(id) ON DELETE CASCADE
    );

INSERT INTO livres (titre, auteur, annee, disponible) VALUES
                                                          ('Le Petit Prince', 'Antoine de Saint-Exupéry', 1943, TRUE),
                                                          ('1984', 'George Orwell', 1949, TRUE),
                                                          ('Les Misérables', 'Victor Hugo', 1862, TRUE),
                                                          ('L''Étranger', 'Albert Camus', 1942, TRUE),
                                                          ('La Peste', 'Albert Camus', 1947, TRUE);

INSERT INTO membres (nom, email) VALUES
                                     ('Jean Dupont', 'jean.dupont@email.com'),
                                     ('Marie Martin', 'marie.martin@email.com'),
                                     ('Pierre Durand', 'pierre.durand@email.com');

INSERT INTO emprunts (id_livre, id_membre, date_emprunt) VALUES
    (1, 1, '10/12/2025');

UPDATE livres SET disponible = FALSE WHERE id = 1;