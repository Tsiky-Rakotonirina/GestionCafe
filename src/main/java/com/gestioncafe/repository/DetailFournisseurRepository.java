package com.gestioncafe.repository;

import com.gestioncafe.controller.stock.ApprovisionnementController;
import com.gestioncafe.model.DetailFournisseur;
import com.gestioncafe.model.Fournisseur;
import com.gestioncafe.model.MatierePremiere;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface DetailFournisseurRepository extends JpaRepository<DetailFournisseur, Integer> {
    List<DetailFournisseur> findByMatierePremiere(MatierePremiere matierePremiere);

    List<DetailFournisseur> findByFournisseur(Fournisseur fournisseur);

    List<DetailFournisseur> findByFournisseurOrderByMatierePremiereNomAsc(Fournisseur fournisseur);

    List<DetailFournisseur> findByFournisseurOrderByPrixAsc(Fournisseur fournisseur);

    List<DetailFournisseur> findByFournisseurOrderByDateModificationDesc(Fournisseur fournisseur);

    // Nouvelle méthode pour trouver par ID de fournisseur et ID de matière première
    Optional<DetailFournisseur> findByFournisseurIdAndMatierePremiereId(Long fournisseurId, Long matierePremiereId);

    // Version alternative qui retourne une liste (au cas où un fournisseur aurait plusieurs prix pour la même matière)
    List<DetailFournisseur> findByFournisseurIdAndMatierePremiereIdOrderByDateModificationDesc(Long fournisseurId, Long matierePremiereId);

    // Méthode de base
    List<DetailFournisseur> findByMatierePremiereId(Long matierePremiereId);

    // Version avec tri par prix croissant
    List<DetailFournisseur> findByMatierePremiereIdOrderByPrixAsc(Long matierePremiereId);

    // Version avec projection personnalisée
    @Query("SELECT df FROM DetailFournisseur df WHERE df.matierePremiere.id = ?1 ")
    List<DetailFournisseur> findFournisseursByMatierePremiereId(Long matierePremiereId);

    @Query("SELECT NEW ApprovisionnementController.FournisseurPrix(" +
        "df.fournisseur.id, df.fournisseur.nom, df.prix, df.fournisseur.frais) " +
        "FROM DetailFournisseur df WHERE df.matierePremiere.id = ?1 " +
        "ORDER BY df.prix ASC")
    List<ApprovisionnementController.FournisseurPrix> findFournisseurPrixByMatierePremiereId(Long matierePremiereId);

}
