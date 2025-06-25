INSERT INTO administratif(nom, mot_de_passe) VALUES ('admin', 'admin');

-- Genres
INSERT INTO genre(id, nom) VALUES (1, 'Homme'), (2, 'Femme');

-- Tiers (clients et employé)
INSERT INTO tiers(id, nom, prenom, id_genre, contact, email, image) VALUES
  (1, 'Client', 'Un', 1, '0123456789', 'client1@mail.com', NULL),
  (2, 'Client', 'Deux', 2, '0987654321', 'client2@mail.com', NULL),
  (3, 'Employe', 'Test', 1, '0112233445', 'employe@mail.com', NULL);

-- Clients
INSERT INTO client(id, id_tiers, date_adhesion, date_naissance) VALUES
  (1, 1, '2024-01-01', '2000-01-01'),
  (2, 2, '2024-01-02', '2001-02-02');

-- Employé
INSERT INTO employe(id, id_tiers, date_naissance, date_recrutement, date_demission, reference_cv) VALUES
  (1, 3, '1990-01-01', '2023-01-01', NULL, NULL);

-- Package (obligatoire pour produit)
INSERT INTO package(id, nom, valeur, estimation) VALUES (1, 'Paquet Test', 'PT', 1.0);

-- Produits
INSERT INTO produit(id, nom, description, stock, image, delai_peremption, id_package) VALUES
  (1, 'Café Noir', 'Café noir classique', 100, NULL, 30, 1),
  (2, 'Cappuccino', 'Café avec lait mousseux', 50, NULL, 20, 1);

-- Prix de vente des produits
INSERT INTO pv_produit(id, id_produit, prix_vente, date_application) VALUES
  (1, 1, 2000, '2024-01-01'),
  (2, 2, 2500, '2024-01-01');

-- Ventes
INSERT INTO vente(id, date_vente, id_client, id_employe) VALUES
  (1, '2025-06-23 10:00:00', 1, 1),
  (2, '2025-06-24 15:30:00', 2, 1);

-- Détails des ventes (produit, quantité, prix unitaire, montant)
INSERT INTO details_vente(id, id_vente, id_produit, quantite, prix_unitaire, montant) VALUES
  (1, 1, 1, 2, 2000, 4000), -- 2 Café Noir
  (2, 1, 2, 1, 2500, 2500), -- 1 Cappuccino
  (3, 2, 1, 1, 2000, 2000), -- 1 Café Noir
  (4, 2, 2, 3, 2500, 7500); -- 3 Cappuccino