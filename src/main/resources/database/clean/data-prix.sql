-- Prix par fournisseur (detail_fournisseur) - cohérent avec les matières premières et unités
INSERT INTO detail_fournisseur(id_fournisseur, id_matiere_premiere, quantite, id_unite, prix, date_modification)
VALUES
    -- Café vert (kg)
    (1, 1, 50, 1, 12000, '2024-06-01'),
    (2, 1, 100, 1, 23000, '2024-06-10'),
    -- Sucre (kg)
    (1, 2, 25, 1, 4000, '2024-06-05'),
    (2, 2, 50, 1, 7900, '2024-06-12'),
    -- Lait (L)
    (1, 3, 20, 4, 9000, '2024-06-08'),
    (2, 3, 30, 4, 13500, '2024-06-15'),
    -- Chocolat (sachet)
    (1, 4, 10, 6, 2500, '2024-06-09'),
    (2, 4, 20, 6, 4800, '2024-06-16'),
    -- Thé vert (sachet)
    (1, 5, 15, 6, 1800, '2024-06-11'),
    (2, 5, 25, 6, 2950, '2024-06-18'),
    -- Crème (L)
    (1, 6, 10, 4, 3500, '2024-06-13'),
    (2, 6, 15, 4, 5100, '2024-06-20'),
    -- Miel (L)
    (1, 7, 5, 4, 4000, '2024-06-14'),
    (2, 7, 10, 4, 7900, '2024-06-21'),
    -- Cannelle (boîte)
    (1, 8, 2, 7, 1200, '2024-06-17'),
    (2, 8, 4, 7, 2300, '2024-06-22'),
    -- Vanille (boîte)
    (1, 9, 1, 7, 900, '2024-06-19'),
    (2, 9, 2, 7, 1700, '2024-06-23');

-- Historique d'estimation des prix des matières premières
INSERT INTO historique_estimation(id_matiere_premiere, prix, quatite, id_unite, date_application)
VALUES
    (1, 12000, 50, 1, '2024-06-01'), -- Café vert, 50kg à 12000, unité kg
    (1, 12500, 60, 1, '2024-06-15'), -- Café vert, 60kg à 12500, unité kg
    (2, 4000, 25, 1, '2024-06-05'),  -- Sucre, 25kg à 4000, unité kg
    (2, 4200, 30, 1, '2024-06-20'),  -- Sucre, 30kg à 4200, unité kg
    (3, 9000, 20, 4, '2024-06-08'),  -- Lait, 20L à 9000, unité L
    (3, 9500, 25, 4, '2024-06-22'),  -- Lait, 25L à 9500, unité L
    (4, 2500, 10, 6, '2024-06-09'),  -- Chocolat, 10 sachets à 2500, unité sachet
    (5, 1800, 15, 6, '2024-06-11'),  -- Thé vert, 15 sachets à 1800, unité sachet
    (6, 3500, 10, 4, '2024-06-13'),  -- Crème, 10L à 3500, unité L
    (7, 4000, 5, 4, '2024-06-14'),   -- Miel, 5L à 4000, unité L
    (8, 1200, 2, 7, '2024-06-17'),   -- Cannelle, 2 boîtes à 1200, unité boîte
    (9, 900, 1, 7, '2024-06-19');    -- Vanille, 1 boîte à 900, unité boîte