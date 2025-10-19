package ma.fstt.atelier_2.dao;

import ma.fstt.atelier_2.entities.Panier;
import ma.fstt.atelier_2.entities.Internaute;
import ma.fstt.atelier_2.entities.LignePanier;
import ma.fstt.atelier_2.entities.Produit;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.NoResultException;
import java.util.Date;
import java.util.List;

@ApplicationScoped
public class PanierDAO implements GenericDAO<Panier> {

    @PersistenceContext
    private EntityManager em;

    @Override
    public void save(Panier panier) {
        em.persist(panier);
    }

    @Override
    public Panier findById(Long id) {
        return em.find(Panier.class, id);
    }

    @Override
    public List<Panier> findAll() {
        return em.createQuery("SELECT p FROM Panier p", Panier.class)
                .getResultList();
    }

    @Override
    public void update(Panier panier) {
        em.merge(panier);
    }

    @Override
    public void delete(Panier panier) {
        em.remove(em.contains(panier) ? panier : em.merge(panier));
    }

    public Panier findByInternauteId(Long internauteId) {
        try {
            return em.createQuery(
                            "SELECT p FROM Panier p LEFT JOIN FETCH p.lignes WHERE p.internaute.id = :internauteId",
                            Panier.class)
                    .setParameter("internauteId", internauteId)
                    .getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    // SANS @Transactional - les transactions seront gérées dans la Servlet
    public void ajouterProduit(Long internauteId, Long produitId, Integer quantite) {
        Panier panier = findByInternauteId(internauteId);
        Produit produit = em.find(Produit.class, produitId);

        if (panier == null) {
            Internaute internaute = em.find(Internaute.class, internauteId);
            panier = new Panier();
            panier.setInternaute(internaute);
            panier.setDateCreation(new Date());
            em.persist(panier);
        }

        LignePanier ligneExistante = null;
        for (LignePanier ligne : panier.getLignes()) {
            if (ligne.getProduit().getId().equals(produitId)) {
                ligneExistante = ligne;
                break;
            }
        }

        if (ligneExistante != null) {
            ligneExistante.setQuantite(ligneExistante.getQuantite() + quantite);
        } else {
            LignePanier nouvelleLigne = new LignePanier();
            nouvelleLigne.setPanier(panier);
            nouvelleLigne.setProduit(produit);
            nouvelleLigne.setQuantite(quantite);
            panier.getLignes().add(nouvelleLigne);
            em.persist(nouvelleLigne);
        }
    }

    public void modifierQuantite(Long ligneId, Integer nouvelleQuantite) {
        LignePanier ligne = em.find(LignePanier.class, ligneId);
        if (ligne != null) {
            ligne.setQuantite(nouvelleQuantite);
        }
    }

    public void supprimerLigne(Long ligneId) {
        LignePanier ligne = em.find(LignePanier.class, ligneId);
        if (ligne != null) {
            Panier panier = ligne.getPanier();
            panier.getLignes().remove(ligne);
            em.remove(ligne);
        }
    }

    public void viderPanier(Long panierId) {
        Panier panier = findById(panierId);
        if (panier != null) {
            panier.getLignes().clear();
        }
    }
}