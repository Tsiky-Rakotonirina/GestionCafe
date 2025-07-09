CREATE or replace VIEW  v_client_lib AS
SELECT 
    c.id,
    t.nom,
    t.prenom,
    g.nom as nom_genre,
    t.contact,
    t.email,
    t.image,
    CASE 
        WHEN c.date_naissance IS NOT NULL THEN 
            EXTRACT(YEAR FROM AGE(CURRENT_DATE, c.date_naissance))
        ELSE 
            EXTRACT(YEAR FROM AGE(CURRENT_DATE, c.date_adhesion))
    END as age,
    c.date_adhesion,
    COALESCE(SUM(dv.montant), 0) as chiffre_affaire_total,
    COUNT(DISTINCT v.id) as nb_vente_total
FROM client c
INNER JOIN tiers t ON c.id_tiers = t.id
INNER JOIN genre g ON t.id_genre = g.id
LEFT JOIN vente v ON c.id = v.id_client
LEFT JOIN details_vente dv ON v.id = dv.id_vente
GROUP BY 
    c.id, 
    t.nom, 
    t.prenom, 
    g.nom, 
    t.contact, 
    t.email, 
    t.image, 
    c.date_naissance, 
    c.date_adhesion;