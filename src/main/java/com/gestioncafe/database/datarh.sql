INSERT INTO genre (valeur, description) VALUES
('Homme', 'Genre masculin'),
('Femme', 'Genre féminin'),
('Autre', 'Genre non spécifié ou autre');



INSERT INTO candidat (nom, id_genre, date_naissance, date_candidature, contact, image, reference_cv, id_grade) VALUES
('Rakoto Jean', 1, '1995-04-10', '2024-06-01', '0321234567', 'rakoto.jpg', 'cv_rakoto.pdf', 1),
('Rasoa Marie', 2, '1998-08-21', '2024-06-05', '0349876543', 'rasoa.jpg', 'cv_rasoa.pdf', 2),
('Randria Alex', 1, '1992-02-15', '2024-06-10', '0331112222', 'randria.jpg', 'cv_randria.pdf', 3),
('Ramiako Claude', 3, '2000-12-01', '2024-06-12', '0381231234', 'claude.jpg', 'cv_claude.pdf', 2);


INSERT INTO employe (nom, id_genre, date_naissance, contact, date_recrutement, id_candidat, image, reference_cv) VALUES
('Rakoto Jean', 1, '1995-04-10', '0321234567', '2024-07-01', 1, 'rakoto.jpg', 'cv_rakoto.pdf'),
('Rasoa Marie', 2, '1998-08-21', '0349876543', '2024-07-03', 2, 'rasoa.jpg', 'cv_rasoa.pdf'),
('Randria Alex', 1, '1992-02-15', '0331112222', '2024-07-05', 3, 'randria.jpg', 'cv_randria.pdf');
