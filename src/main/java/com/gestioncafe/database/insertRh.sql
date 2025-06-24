drop table candidat cascade;
drop table genre cascade;
drop table serie_bac cascade;
drop table langue cascade;
drop table formation cascade;
drop table experience cascade;
drop table grade cascade;
drop table langue cascade;

-- Table genre
CREATE TABLE genre (
    id           SERIAL         PRIMARY KEY,
    valeur       VARCHAR(255)   NOT NULL,
    description  VARCHAR(500)   NULL
);

-- Table grade
CREATE TABLE grade (
    id       SERIAL        PRIMARY KEY,
    salaire  DECIMAL(10,2) NOT NULL,
    nom      VARCHAR(255)  NOT NULL
);

-- Table serie_bac
CREATE TABLE serie_bac (
    id          SERIAL        PRIMARY KEY,
    valeur      VARCHAR(255)  NOT NULL,
    description VARCHAR(500)  NULL
);

-- Table formation
CREATE TABLE formation (
    id          SERIAL        PRIMARY KEY,
    valeur      VARCHAR(255)  NOT NULL,
    description VARCHAR(500)  NULL
);

-- Table experience
CREATE TABLE experience (
    id          SERIAL        PRIMARY KEY,
    valeur      VARCHAR(255)  NOT NULL,
    description VARCHAR(500)  NULL
);

-- Table langue
CREATE TABLE langue (
    id          SERIAL        PRIMARY KEY,
    valeur      VARCHAR(255)  NOT NULL,
    description VARCHAR(500)  NULL
);

CREATE TABLE candidat (
    id                  SERIAL       PRIMARY KEY,
    nom                 VARCHAR(255) NOT NULL,
    id_genre            INTEGER      NOT NULL,
    date_naissance      DATE         NOT NULL,
    date_candidature    DATE         NOT NULL,
    contact             VARCHAR(20)  NOT NULL,
    image               VARCHAR(255) NULL,
    reference_cv        TEXT         NULL,
    id_grade            INTEGER      NOT NULL,

    CONSTRAINT fk_candidat_genre
        FOREIGN KEY (id_genre)
        REFERENCES genre(id)
);

-- Table detail_candidat
CREATE TABLE detail_candidat (
    id               SERIAL  PRIMARY KEY,
    id_candidat      INTEGER NOT NULL,
    id_serie_bac     INTEGER NULL,
    id_formation     INTEGER NULL,
    id_langue        INTEGER NULL,
    id_experience    INTEGER NULL,

    CONSTRAINT fk_detail_candidat_candidat
        FOREIGN KEY (id_candidat)
        REFERENCES candidat(id),

    CONSTRAINT fk_detail_candidat_serie_bac
        FOREIGN KEY (id_serie_bac)
        REFERENCES serie_bac(id),

    CONSTRAINT fk_detail_candidat_formation
        FOREIGN KEY (id_formation)
        REFERENCES formation(id),

    CONSTRAINT fk_detail_candidat_langue
        FOREIGN KEY (id_langue)
        REFERENCES langue(id),

    CONSTRAINT fk_detail_candidat_experience
        FOREIGN KEY (id_experience)
        REFERENCES experience(id)
);

delete from candidat;
delete from genre;
delete from serie_bac;
delete from langue;
delete from formation;
delete from experience; 
delete from grade;
delete from detail_candidat;

INSERT INTO genre (valeur, description) VALUES 
('Homme', 'Candidat de sexe masculin'),
('Femme', 'Candidat de sexe feminin');

INSERT INTO serie_bac (valeur, description) VALUES 
('L', 'Serie litteraire'),
('S', 'Serie scientifique'),
('C', 'Serie technique');


INSERT INTO langue (valeur, description) VALUES 
('Francais', 'Parle et ecrit couramment le francais'),
('Anglais', 'Parle et ecrit couramment l anglais');


INSERT INTO formation (valeur, description) VALUES 
('Licence Informatique', '3 ans d etudes en informatique et programmation'),
('Master Gestion', 'Formation specialisee en gestion des entreprises'),
('BTS Hotellerie', 'Diplôme de technicien superieur en hôtellerie et restauration'),
('Licence Marketing', 'Formation en marketing, communication et vente'),
('Master RH', 'Master en ressources humaines et management du personnel');

INSERT INTO experience (valeur, description) VALUES 
('Debutant', 'Moins de 1 an d experience'),
('Junior', '1 à 3 ans d experience'),
('Senior', 'Plus de 5 ans d experience');

INSERT INTO grade (salaire, nom) VALUES
(900.00, 'Agent de service'),
(1200.00, 'Commis de cuisine'),
(1500.00, 'Cuisinier'),
(2000.00, 'Chef de partie'),
(2700.00, 'Chef de cuisine');



-- Insérer des candidats
INSERT INTO candidat (nom, id_genre, date_naissance, date_candidature, contact, image, reference_cv, id_grade) VALUES
('Alice Dupont', 2, '1995-05-14', '2025-06-01', '0123456789', 'candidatCv01.png', 'CV d Alice', 3),
('Bob Martin', 1, '1990-03-22', '2025-06-02', '0987654321', 'candidatCv01.png', 'CV de Bob', 4),
('Claire Petit', 2, '1988-11-30', '2025-06-03', '0147253698', 'candidatCv01.png', 'CV de Claire', 2),
('David Moreau', 1, '1992-07-19', '2025-06-04', '0165894321', 'candidatCv01.png', 'CV de David', 5),
('Eva Leroy', 2, '1994-01-10', '2025-06-05', '0176543210', 'candidatCv01.png', 'CV d Eva', 1);

-- Insérer des détails des candidats
INSERT INTO detail_candidat (id_candidat, id_serie_bac, id_formation, id_langue, id_experience) VALUES
(1, 1, 1, 1, 1),
(2, 2, 2, 2, 2),
(3, 1, 3, 1, 1),
(4, 3, 4, 2, 3),
(5, 2, 5, 1, 2);

