package com.gestioncafe.repository;

import com.gestioncafe.model.Approvisionnement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface ApprovisionnementRepository extends JpaRepository<Approvisionnement, Long> {

    // Trouver les approvisionnements par matière première
    List<Approvisionnement> findByMatierePremiereId(Long matierePremiereId);

    // Trouver les approvisionnements par fournisseur
    List<Approvisionnement> findByDetailFournisseurFournisseurId(Long fournisseurId);

    // Trouver les approvisionnements entre deux dates
    List<Approvisionnement> findByDateApprovisionnementBetween(LocalDate startDate, LocalDate endDate);

    // Recherche par référence de facture
    List<Approvisionnement> findByReferenceFactureContainingIgnoreCase(String reference);

    // Recherche par nom de matière première
    @Query("SELECT a FROM Approvisionnement a WHERE LOWER(a.matierePremiere.nom) LIKE LOWER(concat('%', :nom, '%'))")
    List<Approvisionnement> findByMatierePremiereNomContainingIgnoreCase(String nom);

    // Trouver les approvisionnements par fournisseur et matière première
    List<Approvisionnement> findByDetailFournisseurFournisseurIdAndMatierePremiereId(Long fournisseurId, Long matierePremiereId);

    // Trouver les approvisionnements récents (les 30 derniers jours)
    @Query("SELECT a FROM Approvisionnement a WHERE a.dateApprovisionnement >= :date")
    List<Approvisionnement> findRecentApprovisionnements(LocalDate date);

    // Trouver le dernier approvisionnement pour une matière première
    @Query("SELECT a FROM Approvisionnement a WHERE a.matierePremiere.id = :matierePremiereId ORDER BY a.dateApprovisionnement DESC")
    List<Approvisionnement> findLastByMatierePremiereId(Long matierePremiereId);
}