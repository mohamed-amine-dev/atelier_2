package ma.fstt.atelier_2;

import ma.fstt.atelier_2.dao.InternauteDAO;
import ma.fstt.atelier_2.dao.PanierDAO;
import ma.fstt.atelier_2.dao.ProduitDAO;
import ma.fstt.atelier_2.entities.Internaute;
import ma.fstt.atelier_2.entities.Panier;
import ma.fstt.atelier_2.entities.Produit;

import jakarta.inject.Inject;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "PrincipalServlet", urlPatterns = {"/ecommerce"})
public class PrincipalServlet extends HttpServlet {

    @Inject
    private ProduitDAO produitDAO;

    @Inject
    private PanierDAO panierDAO;

    @Inject
    private InternauteDAO internauteDAO;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String action = request.getParameter("action");

        if (action == null || action.isEmpty()) {
            action = "vitrine";
        }

        switch (action) {
            case "vitrine":
                afficherVitrine(request, response);
                break;
            case "panier":
                afficherPanier(request, response);
                break;
            case "detailProduit":
                afficherDetailProduit(request, response);
                break;
            case "logout":
                logout(request, response);
                break;
            default:
                afficherVitrine(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String action = request.getParameter("action");

        switch (action) {
            case "login":
                login(request, response);
                break;
            case "register":
                register(request, response);
                break;
            case "ajouterPanier":
                ajouterAuPanier(request, response);
                break;
            case "modifierQuantite":
                modifierQuantite(request, response);
                break;
            case "supprimerLigne":
                supprimerLignePanier(request, response);
                break;
            case "validerCommande":
                validerCommande(request, response);
                break;
            default:
                response.sendRedirect("ecommerce?action=vitrine");
        }
    }

    // ========== MÉTHODES GET ==========

    private void afficherVitrine(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        List<Produit> produits = produitDAO.findAll();
        request.setAttribute("produits", produits);
        request.getRequestDispatcher("/WEB-INF/views/vitrine.jsp").forward(request, response);
    }

    private void afficherPanier(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();
        Long internauteId = (Long) session.getAttribute("internauteId");

        if (internauteId == null) {
            response.sendRedirect("index.jsp");
            return;
        }

        Panier panier = panierDAO.findByInternauteId(internauteId);
        request.setAttribute("panier", panier);
        request.getRequestDispatcher("/WEB-INF/views/panier.jsp").forward(request, response);
    }

    private void afficherDetailProduit(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        Long produitId = Long.parseLong(request.getParameter("id"));
        Produit produit = produitDAO.findById(produitId);

        request.setAttribute("produit", produit);
        request.getRequestDispatcher("/WEB-INF/views/detailProduit.jsp").forward(request, response);
    }

    private void logout(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        request.getSession().invalidate();
        response.sendRedirect("index.jsp");
    }

    // ========== MÉTHODES POST ==========

    private void login(HttpServletRequest request, HttpServletResponse response)
            throws IOException {

        String email = request.getParameter("email");
        String motDePasse = request.getParameter("motDePasse");

        Internaute internaute = internauteDAO.authenticate(email, motDePasse);

        if (internaute != null) {
            HttpSession session = request.getSession();
            session.setAttribute("internauteId", internaute.getId());
            session.setAttribute("internauteNom", internaute.getNom() + " " + internaute.getPrenom());
            response.sendRedirect("ecommerce?action=vitrine");
        } else {
            response.sendRedirect("index.jsp?error=1");
        }
    }

    private void register(HttpServletRequest request, HttpServletResponse response)
            throws IOException {

        String nom = request.getParameter("nom");
        String prenom = request.getParameter("prenom");
        String email = request.getParameter("email");
        String motDePasse = request.getParameter("motDePasse");

        if (internauteDAO.emailExists(email)) {
            response.sendRedirect("index.jsp?error=2");
            return;
        }

        Internaute internaute = new Internaute();
        internaute.setNom(nom);
        internaute.setPrenom(prenom);
        internaute.setEmail(email);
        internaute.setMotDePasse(motDePasse);

        internauteDAO.save(internaute);

        HttpSession session = request.getSession();
        session.setAttribute("internauteId", internaute.getId());
        session.setAttribute("internauteNom", internaute.getNom() + " " + internaute.getPrenom());

        response.sendRedirect("ecommerce?action=vitrine");
    }

    private void ajouterAuPanier(HttpServletRequest request, HttpServletResponse response)
            throws IOException {

        HttpSession session = request.getSession();
        Long internauteId = (Long) session.getAttribute("internauteId");

        if (internauteId == null) {
            response.sendRedirect("index.jsp");
            return;
        }

        Long produitId = Long.parseLong(request.getParameter("produitId"));
        Integer quantite = Integer.parseInt(request.getParameter("quantite"));

        panierDAO.ajouterProduit(internauteId, produitId, quantite);

        response.sendRedirect("ecommerce?action=panier");
    }

    private void modifierQuantite(HttpServletRequest request, HttpServletResponse response)
            throws IOException {

        Long ligneId = Long.parseLong(request.getParameter("ligneId"));
        Integer quantite = Integer.parseInt(request.getParameter("quantite"));

        panierDAO.modifierQuantite(ligneId, quantite);

        response.sendRedirect("ecommerce?action=panier");
    }

    private void supprimerLignePanier(HttpServletRequest request, HttpServletResponse response)
            throws IOException {

        Long ligneId = Long.parseLong(request.getParameter("ligneId"));
        panierDAO.supprimerLigne(ligneId);

        response.sendRedirect("ecommerce?action=panier");
    }

    private void validerCommande(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();
        Long internauteId = (Long) session.getAttribute("internauteId");

        if (internauteId == null) {
            response.sendRedirect("index.jsp");
            return;
        }

        Panier panier = panierDAO.findByInternauteId(internauteId);

        if (panier != null && !panier.getLignes().isEmpty()) {
            // Vider le panier après validation
            panierDAO.viderPanier(panier.getId());

            request.setAttribute("message", "Commande validée avec succès!");
            request.getRequestDispatcher("/WEB-INF/views/confirmation.jsp").forward(request, response);
        } else {
            response.sendRedirect("ecommerce?action=panier");
        }
    }
}