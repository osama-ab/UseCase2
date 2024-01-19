package com.example.produitservice.controller;

import com.example.produitservice.Configuration.ApplicationPropertiesConfiguration;
import com.example.produitservice.dao.ProduitDao;
import com.example.produitservice.model.Produit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;


@RestController
public class ProduitController implements HealthIndicator {


    @Autowired
    ProduitDao produitDao ;

    @Autowired
    ApplicationPropertiesConfiguration appProperties ;


    @GetMapping(value = "/Produits")
    public List<Produit> listeDesProduits() {
        System.out.println(" ********* ProductController listeDesProduits() ");
        List<Produit> products = produitDao.findAll();
        if (products.isEmpty())
            System.out.println("Aucun produit n'est disponible à la vente");
        List<Produit> listeLimitee = products.subList(0, appProperties.getLimitDeProduits());
        return listeLimitee;
    }

    // Récuperer un produit par son id
    @GetMapping(value = "/Produits/{id}")
    public Optional<Produit> recupererUnProduit(@PathVariable int id) {
        System.out.println(" ********* ProductController recupererUnProduit(@PathVariable int id) ");
        Optional<Produit> produit = produitDao.findById(id);

        if (!produit.isPresent())
            System.out.println("Le produit correspondant à l'id "+ id + " n'existe pas");
        return produit;
    }
    @Override
    public Health health() {
        System.out.println("****** Actuator : ProductController health() ");
        List<Produit> products = produitDao.findAll();
        if (products.isEmpty()) {
            return Health.down().build();
        }
        return Health.up().build();
    }
}

