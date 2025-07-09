INSERT INTO administratif(nom, mot_de_passe)
VALUES ('admin', 'admin');

INSERT INTO categorie_unite (nom, norme)
VALUES ('boisson', 'gobelet de 45cl'),
       ('consommable', 'unite'),
       ('masse', 'kg'),
       ('volume', 'L'),
       ('temps', 'j');
-- exemple cookie

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