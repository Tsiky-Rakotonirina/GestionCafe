
CREATE TABLE lieu (
    id_lieu SERIAL PRIMARY KEY,
    nom VARCHAR(100) NOT NULL
);

CREATE TABLE marketing (
    id SERIAL PRIMARY KEY,
    id_genre INTEGER NOT NULL,
    id_lieu INTEGER NOT NULL,
    age INTEGER NOT NULL CHECK (age >= 0),
    id_produit INTEGER NOT NULL,
    budget_moyen DECIMAL(10, 2) NOT NULL CHECK (budget_moyen >= 0),
    estimation_prix_produit DECIMAL(10, 2) NOT NULL CHECK (estimation_prix_produit >= 0),

    -- Clés étrangères
    FOREIGN KEY (id_genre) REFERENCES genre(id),
    FOREIGN KEY (id_lieu) REFERENCES lieux(id_lieu),
    FOREIGN KEY (id_produit) REFERENCES produit(id)
);

