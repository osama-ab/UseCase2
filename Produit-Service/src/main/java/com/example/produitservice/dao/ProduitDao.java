package com.example.produitservice.dao;

import com.example.produitservice.model.Produit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProduitDao extends JpaRepository<Produit,Integer> {
    
}
