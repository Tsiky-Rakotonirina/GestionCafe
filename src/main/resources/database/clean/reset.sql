-- Script de réinitialisation complète de la base (PostgreSQL)
-- Supprime toutes les données et remet les séquences SERIAL à 1

-- Désactiver les contraintes de clé étrangère temporairement
DO $$ DECLARE
    r RECORD;
BEGIN
    FOR r IN (SELECT tablename FROM pg_tables WHERE schemaname = current_schema()) LOOP
        EXECUTE 'ALTER TABLE ' || quote_ident(r.tablename) || ' DISABLE TRIGGER ALL';
    END LOOP;
END $$;

-- Suppression de toutes les données (dans l'ordre inverse des dépendances)
TRUNCATE TABLE 
    utilisation_machine, machine, electricite, mouvement_stock, approvisionnement, mouvement_stock_produit, production, commande, details_vente, vente, detail_recette, recette, prix_vente_produit, produit, package, seuil_matiere_premiere, historique_estimation, matiere_premiere, unite, categorie_unite, detail_fournisseur, fournisseur, conge, type_conge, jour_ferie, presence, grade_employe, statut_employe, statut, employe, detail_candidat, candidat, langue, experience, formation, serie_bac, grade, client, genre, administratif, raison_avance, avance, raison_commission, commission, payement RESTART IDENTITY CASCADE;

-- Réactiver les contraintes de clé étrangère
DO $$ DECLARE
    r RECORD;
BEGIN
    FOR r IN (SELECT tablename FROM pg_tables WHERE schemaname = current_schema()) LOOP
        EXECUTE 'ALTER TABLE ' || quote_ident(r.tablename) || ' ENABLE TRIGGER ALL';
    END LOOP;
END $$;

-- Fin du script
