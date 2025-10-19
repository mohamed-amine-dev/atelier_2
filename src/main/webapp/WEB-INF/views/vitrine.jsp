<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html>
<head>
    <title>Vitrine - E-Commerce</title>
    <link rel="stylesheet" href="css/style.css">
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
</head>
<body>
<header>
    <h1>Boutique E-Commerce</h1>
    <nav>
        <a href="ecommerce?action=vitrine">Vitrine</a>
        <a href="ecommerce?action=panier">Panier (<span id="panierCount">0</span>)</a>
    </nav>
</header>

<main class="produits-container">
    <h2>Nos Produits</h2>
    <div class="produits-grid">
        <c:forEach items="${produits}" var="produit">
            <div class="produit-card">
                <img src="${produit.image}" alt="${produit.nom}">
                <h3>${produit.nom}</h3>
                <p class="description">${produit.description}</p>
                <p class="prix"><fmt:formatNumber value="${produit.prix}" type="currency" currencySymbol="€"/></p>
                <form method="post" action="ecommerce" class="ajout-panier-form">
                    <input type="hidden" name="action" value="ajouterPanier">
                    <input type="hidden" name="produitId" value="${produit.id}">
                    <input type="number" name="quantite" value="1" min="1" max="${produit.stock}">
                    <button type="submit">Ajouter au panier</button>
                </form>
            </div>
        </c:forEach>
    </div>
</main>

<script>
    $(document).ready(function() {
        $('.ajout-panier-form').submit(function(e) {
            e.preventDefault();
            $.post($(this).attr('action'), $(this).serialize(), function() {
                alert('Produit ajouté au panier');
                // Mettre à jour le compteur du panier
            });
        });
    });
</script>
</body>
</html>