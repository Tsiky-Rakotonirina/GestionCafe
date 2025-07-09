package com.gestioncafe.repository;

import java.util.List;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.gestioncafe.model.MatierePremiere;

public interface MatierePremiereRepository extends JpaRepository<MatierePremiere, Long> {

    @Query("SELECT COUNT(m) FROM MatierePremiere m")
    Long countMatierePremiereEnRupture();


    @Query(value = "SELECT COALESCE(SUM(mp.stock * df.prix), 0) FROM matiere_premiere mp JOIN detail_fournisseur df ON mp.id = df.id_matiere_premiere WHERE df.date_modification = (SELECT MAX(df2.date_modification) FROM detail_fournisseur df2 WHERE df2.id_matiere_premiere = mp.id)", nativeQuery = true)
    int getTotalValeurStockMatierePremiere();

    @EntityGraph(attributePaths = {"detailsFournisseur", "detailsFournisseur.fournisseur"})
    List<MatierePremiere> findByNomContainingIgnoreCase(String nom);

    @EntityGraph(attributePaths = {"detailsFournisseur", "detailsFournisseur.fournisseur"})
    List<MatierePremiere> findAllByOrderByNomAsc();
}
