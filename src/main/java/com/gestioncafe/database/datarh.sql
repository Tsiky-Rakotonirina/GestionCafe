INSERT INTO genre (valeur, description)
VALUES ('Homme', 'Sexe masculin');
-- Supposons que cet enregistrement a pour id = 1


INSERT INTO candidat (
    nom, id_genre, date_naissance, date_candidature, contact,
    image, reference_cv, id_grade
)
VALUES (
    'Rakoto Jean', 1, '1990-05-12', '2025-06-01', '0321234567',
    'rakoto.jpg', 'CV de Rakoto Jean en texte', 2
);
-- Supposons que cet enregistrement a pour id = 1

INSERT INTO employe (
    nom, id_genre, date_naissance, contact, date_recrutement,
    id_candidat, image, reference_cv
)
VALUES (
    'Rakoto Jean', 1, '1990-05-12', '0321234567', '2025-06-15',
    1, 'rakoto.jpg', 'CV de Rakoto Jean en texte'
);
