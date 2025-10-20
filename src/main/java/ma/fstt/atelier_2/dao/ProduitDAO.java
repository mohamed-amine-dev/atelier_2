package ma.fstt.atelier_2.dao;

import ma.fstt.atelier_2.entities.Produit;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.util.List;

@ApplicationScoped  // ✅ OBLIGATOIRE pour CDI
public class ProduitDAO implements GenericDAO<Produit> {

    @PersistenceContext(unitName = "ecommercePU")  // ✅ Spécifier le nom
    private EntityManager em;

    @Override
    public void save(Produit produit) {
        em.persist(produit);
    }

    @Override
    public Produit findById(Long id) {
        return em.find(Produit.class, id);
    }

    @Override
    public List<Produit> findAll() {
        return em.createQuery("SELECT p FROM Produit p", Produit.class)
                .getResultList();
    }

    @Override
    public void update(Produit produit) {
        em.merge(produit);
    }

    @Override
    public void delete(Produit produit) {
        em.remove(em.contains(produit) ? produit : em.merge(produit));
    }

    public List<Produit> findDisponibles() {
        return em.createQuery("SELECT p FROM Produit p WHERE p.disponible = true", Produit.class)
                .getResultList();
    }
}