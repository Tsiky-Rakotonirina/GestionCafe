INSERT INTO administratif(nom, mot_de_passe)
VALUES ('admin', 'admin');

INSERT INTO categorie_unite (nom, norme)
VALUES ('boisson', 'gobelet de 45cl'),
       ('consommable', 'unite');
-- exemple cookie

INSERT INTO unite (nom, categorie_unite_id, valeur_pr_norme)
VALUES ('gobelet', 1, '1');

-- Genres
INSERT INTO genre(id, nom)
VALUES (1, 'Homme'),
       (2, 'Femme');

-- Tiers (clients et employé)
INSERT INTO tiers(nom, prenom, id_genre, contact, email, image)
VALUES ('Client', 'Un', 1, '0123456789', 'client1@mail.com', NULL),
       ('Client', 'Deux', 2, '0987654321', 'client2@mail.com', NULL),
       ('Employe', 'Test', 1, '0112233445', 'employe@mail.com', NULL);

-- Clients
INSERT INTO client(id, id_tiers, date_adhesion, date_naissance)
VALUES (1, 1, '2024-01-01', '2000-01-01'),
       (2, 2, '2024-01-02', '2001-02-02');

-- Employé
INSERT INTO employe(id, id_tiers, date_naissance, date_recrutement, date_demission, reference_cv)
VALUES (1, 3, '1990-01-01', '2023-01-01', NULL, NULL);

-- Package (obligatoire pour produit)
INSERT INTO package(id, nom, cout)
VALUES (1, 'sachet plastique', 200.0);

-- Produits
INSERT INTO produit(id, nom, description, stock, image, delai_peremption, id_unite, id_package)
VALUES (1, 'Café Noir', 'Café noir classique', 100, NULL, 30, 1, 1),
       (2, 'Cappuccino', 'Café avec lait mousseux', 50, NULL, 20, 1, 1);

-- Prix de vente des produits
INSERT INTO prix_vente_produit(id, id_produit, prix_vente, date_application)
VALUES (1, 1, 2000, '2024-01-01'),
       (2, 2, 2500, '2024-01-01');

-- Ventes
INSERT INTO vente(date_vente, id_client, id_employe)
VALUES ('2025-06-23 10:00:00', 1, 1),
       ('2025-06-24 15:30:00', 2, 1),
       ('2025-05-15 09:00:00', 1, 1),
       ('2025-04-10 11:00:00', 2, 1),
       ('2025-03-05 14:00:00', 1, 1),
       ('2024-12-20 16:00:00', 2, 1),
       ('2024-11-18 08:30:00', 1, 1),
       ('2024-10-12 13:45:00', 2, 1),
       ('2024-07-07 17:00:00', 1, 1),
       ('2023-08-15 10:30:00', 2, 1),
       ('2023-02-20 12:00:00', 1, 1),
       ('2022-12-25 09:15:00', 2, 1);

-- Détails des ventes (produit, quantité, prix unitaire, montant)
INSERT INTO details_vente(id_vente, id_produit, quantite, prix_unitaire, montant)
VALUES (1, 1, 2, 2000, 4000), -- 2 Café Noir
       (1, 2, 1, 2500, 2500), -- 1 Cappuccino
       (2, 1, 1, 2000, 2000), -- 1 Café Noir
       (2, 2, 3, 2500, 7500), -- 3 Cappuccino
       (3, 1, 4, 2000, 8000),
       (3, 2, 2, 2500, 5000),
       (4, 1, 3, 2000, 6000),
       (4, 2, 1, 2500, 2500),
       (5, 1, 2, 2000, 4000),
       (5, 2, 2, 2500, 5000),
       (6, 1, 1, 2000, 2000),
       (6, 2, 4, 2500, 10000),
       (7, 1, 5, 2000, 10000),
       (7, 2, 1, 2500, 2500),
       (8, 1, 2, 2000, 4000),
       (8, 2, 3, 2500, 7500),
       (9, 1, 1, 2000, 2000),
       (9, 2, 2, 2500, 5000),
       (10, 1, 3, 2000, 6000),
       (10, 2, 2, 2500, 5000),
       (11, 1, 2, 2000, 4000),
       (11, 2, 1, 2500, 2500),
       (12, 1, 1, 2000, 2000),
       (12, 2, 2, 2500, 5000);