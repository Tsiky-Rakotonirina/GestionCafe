INSERT INTO categorie_unite (nom, norme)
VALUES ('boisson', 'gobelet de 45cl'),
       ('consommable', 'unite'),
       ('masse', 'kg'),
       ('volume', 'L'),
       ('temps', 'j');

INSERT INTO unite (nom, categorie_unite_id, valeur_pr_norme)
VALUES ('kg', 3, 1),
       ('g', 3, 0.001),
       ('mg', 3, 0.000001),
       ('L', 4, 1),
       ('cl', 4, 0.01),
       ('sachet', 2, 1),
       ('boîte', 2, 1),
       ('unite', 2, 1),
       ('gobelet', 1, '1');

-- Package (pour produit)
INSERT INTO package (nom, cout)
VALUES ('Standard', 500);

-- Produit (id_unite et id_package à adapter selon data-init.sql)
INSERT INTO produit (nom, description, stock, image, delai_peremption, id_unite, id_package)
VALUES ('Café noir', 'Café noir classique', 100, NULL, 30, 8, 1),
       ('Thé vert', 'Thé vert bio', 50, NULL, 20, 8, 1),
       ('Cappuccino', 'Café avec lait mousseux', 80, NULL, 20, 8, 1),
       ('Expresso', 'Petit café serré', 60, NULL, 15, 8, 1),
       ('Chocolat Chaud', 'Boisson chaude au chocolat', 70, NULL, 20, 8, 1);

-- Vente (id_client et id_employe à adapter selon les données ci-dessus)
INSERT INTO vente (date_vente, id_client, id_employe)
VALUES ('2024-06-01 09:00:00', 1, 1),
       ('2024-06-01 10:00:00', 2, 2),
       ('2024-06-02 11:00:00', 1, 1),
       ('2024-06-03 14:30:00', 2, 2),
       ('2024-06-04 09:15:00', 1, 1),
       ('2024-06-05 16:45:00', 2, 2),
       ('2024-06-06 10:20:00', 1, 1),
       ('2024-06-07 13:10:00', 2, 2),
       ('2024-06-08 15:00:00', 1, 1),
       ('2024-06-09 17:30:00', 2, 2);

-- Détails vente
INSERT INTO details_vente (id_vente, id_produit, quantite, prix_unitaire, montant)
VALUES (1, 1, 2, 1500, 3000),
       (1, 2, 1, 1200, 1200),
       (2, 1, 1, 1500, 1500),
       (3, 1, 1, 1500, 1500),
       (3, 2, 2, 1200, 2400),
       (4, 1, 3, 1500, 4500),
       (4, 2, 1, 1200, 1200),
       (5, 1, 2, 1500, 3000),
       (5, 2, 2, 1200, 2400),
       (6, 1, 1, 1500, 1500),
       (6, 2, 4, 1200, 4800),
       (7, 1, 5, 1500, 7500),
       (7, 2, 1, 1200, 1200),
       (8, 1, 2, 1500, 3000),
       (8, 2, 3, 1200, 3600),
       (9, 1, 1, 1500, 1500),
       (9, 2, 2, 1200, 2400),
       (10, 1, 3, 1500, 4500),
       (10, 2, 2, 1200, 2400);

-- Matières premières (exemples)
INSERT INTO matiere_premiere (nom, id_unite, id_categorie_unite_id, stock, image)
VALUES ('Café vert', 1, 3, 100, NULL), -- kg
       ('Sucre', 1, 3, 50, NULL),      -- kg
       ('Lait', 4, 4, 30, NULL),       -- L
       ('Chocolat', 6, 2, 40, NULL),   -- sachet
       ('Thé vert', 6, 2, 25, NULL),   -- sachet
       ('Crème', 4, 4, 15, NULL),      -- L
       ('Miel', 4, 4, 10, NULL),       -- L
       ('Cannelle', 7, 2, 8, NULL),    -- boîte
       ('Vanille', 7, 2, 5, NULL);
-- boîte

-- Recettes (liées aux produits)
INSERT INTO recette (id_produit, quantite_produite, temps_fabrication)
VALUES (1, 1, 5), -- Café noir
       (2, 1, 5), -- Thé vert
       (3, 1, 7), -- Cappuccino
       (4, 1, 4), -- Expresso
       (5, 1, 6);
-- Chocolat Chaud

-- Ingrédients (detail_recette) pour chaque recette/produit
-- Café noir : café vert + eau
INSERT INTO detail_recette (id_recette, id_matiere_premiere, id_unite, quantite)
VALUES (1, 1, 1, 0.01), -- 10g café vert
       (1, 3, 4, 0.2);
-- 0.2L lait

-- Thé vert : thé vert + sucre
INSERT INTO detail_recette (id_recette, id_matiere_premiere, id_unite, quantite)
VALUES (2, 5, 6, 1), -- 1 sachet thé vert
       (2, 2, 1, 0.005);
-- 5g sucre

-- Cappuccino : café vert + lait + sucre
INSERT INTO detail_recette (id_recette, id_matiere_premiere, id_unite, quantite)
VALUES (3, 1, 1, 0.01), -- 10g café vert
       (3, 3, 4, 0.15), -- 0.15L lait
       (3, 2, 1, 0.005);
-- 5g sucre

-- Expresso : café vert
INSERT INTO detail_recette (id_recette, id_matiere_premiere, id_unite, quantite)
VALUES (4, 1, 1, 0.008);
-- 8g café vert

-- Chocolat Chaud : chocolat + lait + sucre
INSERT INTO detail_recette (id_recette, id_matiere_premiere, id_unite, quantite)
VALUES (5, 4, 6, 1),   -- 1 sachet chocolat
       (5, 3, 4, 0.2), -- 0.2L lait
       (5, 2, 1, 0.01); -- 10g sucre