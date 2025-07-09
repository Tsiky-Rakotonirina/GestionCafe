-- Genre
INSERT INTO genre (nom)
VALUES ('Homme'),
       ('Femme');

-- Client
INSERT INTO client (nom, prenom, id_genre, contact, email, date_adhesion, date_naissance)
VALUES ('Randria', 'Jean', 1, '0321234567', 'jean.randria@email.com', '2023-01-10', '1990-05-15'),
       ('Rakoto', 'Marie', 2, '0349876543', 'marie.rakoto@email.com', '2023-02-20', '1988-11-30');

-- Grade (pour employé)
INSERT INTO grade (salaire, nom)
VALUES (500000, 'Serveur'),
       (800000, 'Barista');

-- Candidat (pour employé)
INSERT INTO candidat (date_candidature, nom, id_genre, date_naissance, contact, id_grade)
VALUES ('2023-01-01', 'Andrianina', 1, '1995-03-10', '0321112233', 1),
       ('2023-01-02', 'Rasoanaivo', 2, '1992-07-22', '0342223344', 2);

-- Employé
INSERT INTO employe (id_candidat, date_recrutement, nom, id_genre, date_naissance, contact)
VALUES (1, '2023-02-01', 'Andrianina', 1, '1995-03-10', '0321112233'),
       (2, '2023-02-01', 'Rasoanaivo', 2, '1992-07-22', '0342223344');

-- Fournisseur
INSERT INTO fournisseur (nom, contact, frais, email)
VALUES ('Fournisseur A', '0320000001', 1000, 'fournisseurA@email.com'),
       ('Fournisseur B', '0320000002', 1500, 'fournisseurB@email.com');

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