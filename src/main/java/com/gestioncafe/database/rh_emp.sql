
-- Table employe
CREATE TABLE employe (
    id               SERIAL       PRIMARY KEY,
    nom                 VARCHAR(255) NOT NULL,
    id_genre            INTEGER      NOT NULL,
    date_naissance      DATE         NOT NULL,
    contact             VARCHAR(20)  NOT NULL,
    date_recrutement DATE         NOT NULL,
    id_candidat      INTEGER      NOT NULL,
    image            VARCHAR(255) NULL,
    reference_cv     TEXT         NULL,

    CONSTRAINT fk_employe_candidat
        FOREIGN KEY (id_candidat)
        REFERENCES candidat(id),

    CONSTRAINT fk_employe_genre
        FOREIGN KEY (id_genre)
        REFERENCES genre(id)
);

CREATE TABLE statut (
    id          SERIAL        PRIMARY KEY,
    valeur      VARCHAR(255)  NOT NULL,
    description VARCHAR(500)  NULL
);

CREATE TABLE statut_employe (
    id            SERIAL       PRIMARY KEY,
    id_employe    INTEGER      NOT NULL,
    date_statut   TIMESTAMP    NOT NULL,
    id_statut     INTEGER      NOT NULL,

    CONSTRAINT fk_statut_employe_statut
        FOREIGN KEY (id_statut)
        REFERENCES statut(id),

    CONSTRAINT fk_activite_employe_employe
        FOREIGN KEY (id_employe)
        REFERENCES employe(id)
);

-- Table grade_employe
CREATE TABLE grade_employe (
    id          SERIAL      PRIMARY KEY,
    id_employe  INTEGER     NOT NULL,
    id_grade    INTEGER     NOT NULL,
    date_grade  DATE        NOT NULL,

    CONSTRAINT fk_grade_employe_employe
        FOREIGN KEY (id_employe)
        REFERENCES employe(id),

    CONSTRAINT fk_grade_employe_grade
        FOREIGN KEY (id_grade)
        REFERENCES grade(id)
);

delete from grade_employe;
delete from statut_employe;
delete from employe;

-- Insertion des statuts dans la table statut
INSERT INTO statut (valeur, description) VALUES
('Actif', 'L employé est actuellement actif dans l entreprise.'),
('Inactif', 'L employé n est pas actuellement actif dans l entreprise.');