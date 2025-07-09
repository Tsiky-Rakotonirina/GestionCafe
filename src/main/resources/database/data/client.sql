-- Tiers (personnes physiques)
INSERT INTO tiers (nom, prenom, id_genre, contact, email, image)
VALUES ('Randriamampionona', 'Jean', 1, '0321234567', 'jean.randria@email.com', 'jean.jpg'),
       ('Rakoto', 'Marie', 2, '0349876543', 'marie.rakoto@email.com', 'marie.jpg'),
       ('Andrianina', 'Paul', 1, '0331122334', 'paul.andrianina@email.com', 'paul.jpg'),
       ('Rasoanaivo', 'Sophie', 2, '0329988776', 'sophie.rasoanaivo@email.com', 'sophie.jpg'),
       ('Rakotomalala', 'Eric', 1, '0342233445', 'eric.rakoto@email.com', 'eric.jpg');

-- Clients
INSERT INTO client (id_tiers, date_adhesion, date_naissance)
VALUES (3, '2024-02-10', '1995-07-30'),
       (4, '2024-05-05', '1992-03-18'),
       (5, '2025-01-09', '1985-09-12');