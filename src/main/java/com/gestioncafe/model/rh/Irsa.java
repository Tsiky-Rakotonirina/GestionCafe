package com.gestioncafe.model.rh;

import jakarta.persistence.*;
import java.math.BigDecimal;

import lombok.*;

@Entity
@Data // génère getters, setters, toString, equals, hashCode
@NoArgsConstructor // constructeur sans arguments
@AllArgsConstructor // constructeur avec tous les champs
public class Irsa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private BigDecimal taux;

    @Column(name = "salaire_min", nullable = false)
    private BigDecimal salaireMin;

    @Column(name = "salaire_max", nullable = false)
    private BigDecimal salaireMax;


}
