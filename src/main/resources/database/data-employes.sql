-- Genres
INSERT INTO genre (nom)
VALUES ('Homme'),
       ('Femme');

-- Tiers (personnes)
INSERT INTO tiers (nom, prenom, id_genre, contact, email, image)
VALUES ('Randriamampionona', 'Jean', 1, '0341234567', 'jean.randri@example.com', NULL),
       ('Rakotomalala', 'Marie', 2, '0347654321', 'marie.rakoto@example.com', NULL),
       ('Rasoa', 'Paul', 1, '0341112223', 'paul.rasoa@example.com', NULL);

-- Grades
INSERT INTO grade (salaire, nom)
VALUES (500000, 'Serveur'),
       (700000, 'Chef de salle');

-- Ne pas insérer de candidats ni d'employés ici pour éviter les doublons

-- Statuts
INSERT INTO statut (valeur, description)
VALUES ('Actif', 'Employé actuellement en poste'),
       ('En congé', 'Employé en congé'),
       ('Démissionnaire', 'Employé ayant quitté l entreprise');

-- Statut employé (historique) pour tester la gestion RH
-- À adapter selon les IDs déjà présents dans la base
INSERT INTO statut_employe (id_employe, date_statut, id_statut)
VALUES (1, '2023-01-01 08:00:00', 1),
       (1, '2023-06-01 08:00:00', 2);

-- Grade employé (historique)
INSERT INTO grade_employe (id_employe, id_grade, date_grade)
VALUES (1, 1, '2022-01-15');

-- Présences (quelques jours pour l'employé déjà existant)
INSERT INTO presence (id_employe, date_presence, date_arrivee, est_present)
VALUES (1, '2023-06-01', '2023-06-01 08:05:00', TRUE),
       (1, '2023-06-02', '2023-06-02 08:10:00', TRUE),
       (1, '2023-06-03', NULL, FALSE);

-- Clients (pour les ventes)
INSERT INTO client (id_tiers, date_adhesion, date_naissance)
VALUES (1, '2022-01-01', '1990-05-10');

-- Ventes (pour l'efficacité)
INSERT INTO vente (date_vente, id_client, id_employe)
VALUES ('2023-06-01 09:00:00', 1, 1),
       ('2023-06-02 11:00:00', 1, 1);

-- ...ajoutez d'autres données de test spécifiques si besoin...
       (1, '2023-06-02', '2023-06-02 08:10:00', TRUE),
       (2, '2023-06-01', '2023-06-01 08:00:00', TRUE),
       (2, '2023-06-02', NULL, FALSE),
       (3, '2023-06-01', '2023-06-01 08:20:00', TRUE);

-- Clients (pour les ventes)
INSERT INTO client (id_tiers, date_adhesion, date_naissance)
VALUES (1, '2022-01-01', '1990-05-10'),
       (2, '2023-03-01', '1995-08-20');

-- Ventes (pour l'efficacité)
INSERT INTO vente (date_vente, id_client, id_employe)
VALUES ('2023-06-01 09:00:00', 1, 1),
       ('2023-06-01 10:00:00', 2, 2),
       ('2023-06-02 11:00:00', 1, 1),
       ('2023-06-02 12:00:00', 2, 2),
       ('2023-06-01 13:00:00', 1, 3);

-- ...ajoutez d'autres données si besoin pour tester d'autres fonctionnalités...
