package ma.fstt.atelier_2.dao;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import ma.fstt.atelier_2.entities.Produit;

import java.util.List;

@ApplicationScoped
public class ProduitDAO implements GenericDAO<Produit> {

    @PersistenceContext
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

    public List<Produit> findDisponibles() {
        return em.createQuery("SELECT p FROM Produit p WHERE p.disponible = true", Produit.class)
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
}