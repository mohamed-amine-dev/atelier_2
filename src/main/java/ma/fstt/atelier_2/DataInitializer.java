package ma.fstt.atelier_2;

import ma.fstt.atelier_2.dao.ProduitDAO;
import ma.fstt.atelier_2.entities.Produit;

import jakarta.annotation.PostConstruct;
import jakarta.ejb.Singleton;
import jakarta.ejb.Startup;
import jakarta.inject.Inject;

@Singleton
@Startup
public class DataInitializer {

    @Inject
    private ProduitDAO produitDAO;

    @PostConstruct
    public void init() {
        // Ajouter des produits de test
        Produit p1 = new Produit();
        p1.setNom("Laptop HP");
        p1.setDescription("Ordinateur portable 15 pouces");
        p1.setPrix(699.99);
        p1.setStock(10);
        p1.setDisponible(true);
        produitDAO.save(p1);

        Produit p2 = new Produit();
        p2.setNom("Souris Logitech");
        p2.setDescription("Souris sans fil");
        p2.setPrix(29.99);
        p2.setStock(50);
        p2.setDisponible(true);
        produitDAO.save(p2);

        System.out.println("✅ Données de test créées !");
    }
}