INSERT INTO genre (valeur, description)
VALUES ('Homme', 'Sexe masculin');
-- Supposons que cet enregistrement a pour id = 1

INSERT INTO grade (salaire, nom)
VALUES (350000, 'Serveur');
-- Supposons que cet enregistrement a pour id = 1


INSERT INTO candidat (
    date_candidature, nom, id_genre, date_naissance,  contact,
    image, reference_cv, id_grade
)
VALUES (
    '2025-06-01', 'Rakoto Jean', 1, '1990-05-12',  '0321234567',
    'rakoto.jpg', 'CV de Rakoto Jean en texte', 1
);
-- Supposons que cet enregistrement a pour id = 1

INSERT INTO employe (
    id_candidat, date_recrutement, nom, id_genre, date_naissance, contact, 
    image, reference_cv
)
VALUES (
    1, '2025-06-15', 'Rakoto Jean', 1, '1990-05-12', '0321234567',
    'rakoto.jpg', 'CV de Rakoto Jean en texte'
);


INSERT INTO raison_commission(valeur) VALUES ('sur-vente');

insert into commission(id_raison_commission, id_employe, date_commission, montant) 
values (1,1,'2025-05-20',50000);


INSERT INTO raison_avance(valeur) VALUES ('maladie');

insert into avance(id_raison_avance, id_employe, date_avance, montant) 
values (1,1,'2025-05-20',100000);

INSERT INTO statut
