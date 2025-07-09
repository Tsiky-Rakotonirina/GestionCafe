package com.gestioncafe.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import com.gestioncafe.model.*;


@Repository
public interface AdministratifRepository extends JpaRepository<Administratif, Long> {
    public Administratif findByNomAndMotDePasse(String nom, String motDePasse);
}

