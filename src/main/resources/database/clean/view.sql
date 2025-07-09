DROP VIEW IF EXISTS v_client_lib CASCADE;
CREATE OR REPLACE VIEW v_client_lib AS
SELECT c.id,
       c.nom,
       c.prenom,
       g.nom                        AS nom_genre,
       c.contact,
       c.email,
       NULL::varchar                AS image,
       CASE
           WHEN c.date_naissance IS NOT NULL THEN
               EXTRACT(YEAR FROM AGE(CURRENT_DATE, c.date_naissance))
           ELSE
               EXTRACT(YEAR FROM AGE(CURRENT_DATE, c.date_adhesion))
           END                      AS age,
       c.date_adhesion,
       COALESCE(SUM(dv.montant), 0) AS chiffre_affaire_total,
       COUNT(DISTINCT v.id)         AS nb_vente_total
FROM client c
         INNER JOIN genre g ON c.id_genre = g.id
         LEFT JOIN vente v ON c.id = v.id_client
         LEFT JOIN details_vente dv ON v.id = dv.id_vente
GROUP BY c.id,
         c.nom,
         c.prenom,
         g.nom,
         c.contact,
         c.email,
         c.date_naissance,
         c.date_adhesion;
