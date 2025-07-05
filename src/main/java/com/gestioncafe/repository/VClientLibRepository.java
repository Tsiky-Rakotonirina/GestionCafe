package com.gestioncafe.repository;

import com.gestioncafe.model.VClientLib;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface VClientLibRepository extends JpaRepository<VClientLib, String> {
    
    @Query("SELECT v FROM VClientLib v WHERE " +
           "(:nom IS NULL OR LOWER(v.nom) LIKE LOWER(CONCAT('%', :nom, '%'))) AND " +
           "(:prenom IS NULL OR LOWER(v.prenom) LIKE LOWER(CONCAT('%', :prenom, '%'))) AND " +
           "(:ageMin IS NULL OR v.age >= :ageMin) AND " +
           "(:ageMax IS NULL OR v.age <= :ageMax) AND " +
           "(:dateAdhesion IS NULL OR v.dateAdhesion = :dateAdhesion)")
    List<VClientLib> searchClients(@Param("nom") String nom,
                                   @Param("prenom") String prenom,
                                   @Param("ageMin") Integer ageMin,
                                   @Param("ageMax") Integer ageMax,
                                   @Param("dateAdhesion") LocalDate dateAdhesion);
}