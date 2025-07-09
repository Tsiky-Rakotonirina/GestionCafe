package com.gestioncafe.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.gestioncafe.model.Fournisseur;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;

public interface FournisseurRepository extends JpaRepository<Fournisseur, Long> {
    @Query("SELECT f FROM Fournisseur f WHERE LOWER(f.nom) LIKE LOWER(concat('%', :searchTerm, '%')) OR LOWER(f.email) LIKE LOWER(concat('%', :searchTerm, '%'))")
    List<Fournisseur> search(@Param("searchTerm") String searchTerm);

    List<Fournisseur> findAllByOrderByNomAsc();
    List<Fournisseur> findAllByOrderByFraisAsc();
}