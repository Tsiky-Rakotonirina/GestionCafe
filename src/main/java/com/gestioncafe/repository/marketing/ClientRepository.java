package com.gestioncafe.repository.marketing;

import com.gestioncafe.model.marketing.Client;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClientRepository extends JpaRepository<Client, Integer> {}
