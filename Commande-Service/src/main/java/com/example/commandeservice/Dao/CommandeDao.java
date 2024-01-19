package com.example.commandeservice.Dao;

import com.example.commandeservice.Model.Commande;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommandeDao extends JpaRepository<Commande,Integer> {

}
