INSERT INTO genre (valeur, description) VALUES
('Homme', 'Sexe masculin'),
('Femme', 'Sexe féminin');


INSERT INTO grade (salaire, nom) VALUES
(500000.00, 'Développeur Junior'),
(800000.00, 'Développeur Senior'),
(1200000.00, 'Chef de Projet');


INSERT INTO serie_bac (valeur, description) VALUES
('C', 'Scientifique'),
('D', 'Biologie'),
('A2', 'Littéraire');


INSERT INTO formation (valeur, description) VALUES
('Licence Informatique', 'Bac +3'),
('Master Réseaux', 'Bac +5'),
('BTS Gestion', 'Bac +2');


INSERT INTO experience (valeur, description) VALUES
('0-1 an', 'Débutant'),
('2-4 ans', 'Intermédiaire'),
('5+ ans', 'Expérimenté');


INSERT INTO langue (valeur, description) VALUES
('Français', 'Langue principale'),
('Anglais', 'Langue étrangère'),
('Malagasy', 'Langue locale');


INSERT INTO candidat (date_candidature, nom, id_genre, date_naissance, contact, image, reference_cv, id_grade) VALUES
('2025-06-01', 'RABE Jean', 1, '1995-04-12', '0321234567', NULL, 'CV001', 1),
('2025-06-03', 'RAKOTO Marie', 2, '1992-10-05', '0337654321', NULL, 'CV002', 2);


INSERT INTO detail_candidat (id_candidat, id_serie_bac, id_formation, id_langue, id_experience) VALUES
(1, 1, 1, 2, 2),
(2, 2, 2, 1, 3);


INSERT INTO employe (id_candidat, date_recrutement, nom, id_genre, date_naissance, contact, image, reference_cv) VALUES
(1, '2025-06-15', 'RABE Jean', 1, '1995-04-12', '0321234567', NULL, 'CV001'),
(2, '2025-06-20', 'RAKOTO Marie', 2, '1992-10-05', '0337654321', NULL, 'CV002');


INSERT INTO statut (valeur, description) VALUES
('Actif', 'Employé actuellement en poste'),
('Suspendu', 'Contrat temporairement suspendu'),
('Licencié', 'Employé licencié');


INSERT INTO statut_employe (id_employe, date_statut, id_statut) VALUES
(1, NOW(), 1),
(2, NOW(), 1);


INSERT INTO grade_employe (id_employe, id_grade, date_grade) VALUES
(1, 1, '2025-06-15'),
(2, 2, '2025-06-20');


INSERT INTO presence (id_employe, date_presence, date_arrivee, est_present) VALUES
(1, CURRENT_DATE, NOW(), TRUE),
(2, CURRENT_DATE, NOW(), TRUE);


INSERT INTO jour_ferie (nom, date_ferie) VALUES
('Indépendance', '2025-06-26'),
('Noël', '2025-12-25');


INSERT INTO type_conge (nom, nb_jour, description) VALUES
('Congé annuel', 30, 'Congé payé annuel'),
('Maladie', 10, 'Congé pour maladie');


INSERT INTO conge (id_type_conge, date_debut, date_fin, duree, id_employe) VALUES
(1, '2025-07-01', '2025-07-15', 15, 1),
(2, '2025-07-10', '2025-07-15', 5, 2);


INSERT INTO irsa (taux, salaire_min, salaire_max) VALUES
(0.00, 0.00, 350000.00),
(20.00, 350001.00, 500000.00),
(25.00, 500001.00, 1000000.00);


INSERT INTO cotisation_sociale (nom, taux) VALUES
('CNAPS', 1.00),
('OSTIE', 1.50);


INSERT INTO raison_avance (valeur, description) VALUES
('Santé', 'Dépense médicale'),
('Urgence familiale', 'Besoin familial urgent');


INSERT INTO avance (id_raison_avance, id_employe, date_avance, montant) VALUES
(1, 1, '2025-06-18', 100000.00),
(2, 2, '2025-06-19', 150000.00);


INSERT INTO raison_commission (valeur, description) VALUES
('Vente exceptionnelle', 'Bonus de performance'),
('Mission spéciale', 'Mission réalisée');


INSERT INTO commission (id_raison_commission, id_employe, date_commission, montant) VALUES
(1, 1, '2025-06-21', 80000.00),
(2, 2, '2025-06-22', 100000.00);


INSERT INTO payement (id_employe, date_payement, montant, reference_payement, mois_reference) VALUES
(1, '2025-06-30', 500000.00, 'PAY001', '2025-06-01'),
(2, '2025-06-30', 800000.00, 'PAY002', '2025-06-01');
