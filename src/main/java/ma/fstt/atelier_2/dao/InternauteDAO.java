package ma.fstt.atelier_2.dao;

import ma.fstt.atelier_2.entities.Internaute;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.NoResultException;
import java.util.List;

@ApplicationScoped  // ✅
public class InternauteDAO implements GenericDAO<Internaute> {

    @PersistenceContext(unitName = "ecommercePU")  // ✅
    private EntityManager em;
    @Override
    public void save(Internaute internaute) {
        em.persist(internaute);
    }

    @Override
    public Internaute findById(Long id) {
        return em.find(Internaute.class, id);
    }

    @Override
    public List<Internaute> findAll() {
        return em.createQuery("SELECT i FROM Internaute i", Internaute.class)
                .getResultList();
    }

    @Override
    public void update(Internaute internaute) {
        em.merge(internaute);
    }

    @Override
    public void delete(Internaute internaute) {
        em.remove(em.contains(internaute) ? internaute : em.merge(internaute));
    }

    // Méthodes spécifiques
    public Internaute findByEmail(String email) {
        try {
            return em.createQuery(
                            "SELECT i FROM Internaute i WHERE i.email = :email",
                            Internaute.class)
                    .setParameter("email", email)
                    .getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    public Internaute authenticate(String email, String motDePasse) {
        try {
            return em.createQuery(
                            "SELECT i FROM Internaute i WHERE i.email = :email AND i.motDePasse = :motDePasse",
                            Internaute.class)
                    .setParameter("email", email)
                    .setParameter("motDePasse", motDePasse)
                    .getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    public boolean emailExists(String email) {
        Long count = em.createQuery(
                        "SELECT COUNT(i) FROM Internaute i WHERE i.email = :email",
                        Long.class)
                .setParameter("email", email)
                .getSingleResult();
        return count > 0;
    }
}