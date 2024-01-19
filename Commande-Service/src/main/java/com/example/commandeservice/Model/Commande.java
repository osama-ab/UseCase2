package com.example.commandeservice.Model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Commande {

    @Id
    @GeneratedValue
    private Long id ;
    private String description ;
    private int quantite ;
    private LocalDate date ;
    private Double montant ;
    private int id_produit ;

}
