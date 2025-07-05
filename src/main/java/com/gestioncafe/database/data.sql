-- Insertion des genres
INSERT INTO genre (nom) VALUES 
('Homme'),
('Femme'),
('Autre');

-- Insertion des tiers (informations personnelles)
INSERT INTO tiers (nom, prenom, id_genre, contact, email, image) VALUES 
('Rakoto', 'Jean', 1, '0341234567', 'jean.rakoto@email.com', 'jean_rakoto.jpg'),
('Rabe', 'Marie', 2, '0341234568', 'marie.rabe@email.com', 'marie_rabe.jpg'),
('Randria', 'Paul', 1, '0341234569', 'paul.randria@email.com', 'paul_randria.jpg'),
('Razafy', 'Sophie', 2, '0341234570', 'sophie.razafy@email.com', 'sophie_razafy.jpg'),
('Andrianaina', 'Michel', 1, '0341234571', 'michel.andrianaina@email.com', 'michel_andrianaina.jpg'),
('Rasoamanana', 'Claire', 2, '0341234572', 'claire.rasoamanana@email.com', 'claire_rasoamanana.jpg'),
('Rajaonarison', 'David', 1, '0341234573', 'david.rajaonarison@email.com', 'david_rajaonarison.jpg'),
('Ramanantsoa', 'Anne', 2, '0341234574', 'anne.ramanantsoa@email.com', 'anne_ramanantsoa.jpg'),
('Ratsimandresy', 'Pierre', 1, '0341234575', 'pierre.ratsimandresy@email.com', 'pierre_ratsimandresy.jpg'),
('Raharison', 'Julie', 2, '0341234576', 'julie.raharison@email.com', 'julie_raharison.jpg'),
('Andriamampionona', 'Lucas', 1, '0341234577', 'lucas.andriamampionona@email.com', 'lucas_andriamampionona.jpg'),
('Ranaivoson', 'Emma', 2, '0341234578', 'emma.ranaivoson@email.com', 'emma_ranaivoson.jpg');

-- Insertion des clients
INSERT INTO client (id_tiers, date_adhesion, date_naissance) VALUES 
(1, '2020-01-15', '1985-03-20'),
(2, '2020-02-20', '1990-07-15'),
(3, '2020-03-10', '1982-11-30'),
(4, '2021-01-05', '1995-05-10'),
(5, '2021-02-14', '1988-09-25'),
(6, '2021-03-22', '1992-12-08'),
(7, '2022-01-18', '1980-04-12'),
(8, '2022-02-25', '1997-08-03'),
(9, '2022-03-30', '1983-06-18'),
(10, '2023-01-12', '1993-10-22'),
(11, '2023-02-28', '1987-01-14'),
(12, '2023-03-15', '1991-11-05');

-- Insertion des grades pour les employés
INSERT INTO grade (salaire, nom) VALUES 
(800000, 'Vendeur Junior'),
(1200000, 'Vendeur Senior'),
(1500000, 'Chef de vente');

-- Insertion des employés (pour les ventes)
INSERT INTO employe (id_tiers, date_naissance, date_recrutement) VALUES 
(1, '1985-03-20', '2019-01-01'),  -- Jean sera aussi employé
(3, '1982-11-30', '2019-06-01');  -- Paul sera aussi employé

-- Insertion des catégories d'unités
INSERT INTO categorie_unite (nom, norme) VALUES 
('Volume', 'litre'),
('Masse', 'kg'),
('Quantité', 'pièce');

-- Insertion des unités
INSERT INTO unite (nom, categorie_unite_id, valeur_pr_norme) VALUES 
('litre', 1, 1.00),
('ml', 1, 0.001),
('kg', 2, 1.00),
('g', 2, 0.001),
('pièce', 3, 1.00);

-- Insertion des packages
INSERT INTO package (nom, cout) VALUES 
('Package Standard', 1000.00),
('Package Premium', 2000.00);

-- Insertion des produits
INSERT INTO produit (nom, description, stock, image, delai_peremption, id_unite, id_package) VALUES 
('Café Americano', 'Café noir classique', 50.00, 'americano.jpg', 2.00, 5, 1),
('Café Latte', 'Café au lait crémeux', 30.00, 'latte.jpg', 1.50, 5, 2),
('Espresso', 'Café court et intense', 40.00, 'espresso.jpg', 1.00, 5, 1),
('Cappuccino', 'Café avec mousse de lait', 35.00, 'cappuccino.jpg', 1.50, 5, 2),
('Croissant', 'Viennoiserie française', 20.00, 'croissant.jpg', 0.50, 5, 1);

-- Insertion des ventes
INSERT INTO vente (date_vente, id_client, id_employe) VALUES 
('2023-01-15 08:30:00', 1, 1),
('2023-01-16 09:15:00', 2, 2),
('2023-01-17 10:00:00', 3, 1),
('2023-01-18 11:30:00', 4, 2),
('2023-01-19 14:20:00', 5, 1),
('2023-02-01 08:45:00', 1, 1),  -- Jean achète encore
('2023-02-02 09:30:00', 2, 2),  -- Marie achète encore
('2023-02-03 10:15:00', 6, 1),
('2023-02-04 11:00:00', 7, 2),
('2023-02-05 15:30:00', 8, 1),
('2023-03-01 08:20:00', 1, 1),  -- Jean achète une 3ème fois
('2023-03-02 09:45:00', 9, 2),
('2023-03-03 10:30:00', 10, 1),
('2023-03-04 11:15:00', 2, 2),  -- Marie achète une 3ème fois
('2023-03-05 14:00:00', 11, 1);

-- Insertion des détails de vente
INSERT INTO details_vente (id_vente, id_produit, quantite, prix_unitaire, montant) VALUES 
-- Vente 1 (Jean)
(1, 1, 2, 3000.00, 6000.00),
(1, 5, 1, 2000.00, 2000.00),
-- Vente 2 (Marie)
(2, 2, 1, 4500.00, 4500.00),
(2, 4, 1, 4000.00, 4000.00),
-- Vente 3 (Paul)
(3, 3, 3, 2500.00, 7500.00),
-- Vente 4 (Sophie)
(4, 1, 1, 3000.00, 3000.00),
(4, 2, 1, 4500.00, 4500.00),
-- Vente 5 (Michel)
(5, 4, 2, 4000.00, 8000.00),
(5, 5, 2, 2000.00, 4000.00),
-- Vente 6 (Jean - 2ème achat)
(6, 2, 1, 4500.00, 4500.00),
(6, 3, 1, 2500.00, 2500.00),
-- Vente 7 (Marie - 2ème achat)
(7, 1, 2, 3000.00, 6000.00),
-- Vente 8 (Claire)
(8, 5, 3, 2000.00, 6000.00),
-- Vente 9 (David)
(9, 3, 2, 2500.00, 5000.00),
(9, 4, 1, 4000.00, 4000.00),
-- Vente 10 (Anne)
(10, 1, 1, 3000.00, 3000.00),
(10, 2, 1, 4500.00, 4500.00),
-- Vente 11 (Jean - 3ème achat)
(11, 4, 1, 4000.00, 4000.00),
-- Vente 12 (Pierre)
(12, 1, 2, 3000.00, 6000.00),
(12, 5, 1, 2000.00, 2000.00),
-- Vente 13 (Julie)
(13, 2, 1, 4500.00, 4500.00),
(13, 3, 1, 2500.00, 2500.00),
-- Vente 14 (Marie - 3ème achat)
(14, 5, 2, 2000.00, 4000.00),
-- Vente 15 (Lucas)
(15, 1, 1, 3000.00, 3000.00);