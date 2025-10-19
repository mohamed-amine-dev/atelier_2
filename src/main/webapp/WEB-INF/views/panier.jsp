<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html>
<head>
    <title>Mon Panier</title>
    <link rel="stylesheet" href="css/style.css">
</head>
<body>
<header>
    <h1>Mon Panier</h1>
    <a href="ecommerce?action=vitrine">Continuer mes achats</a>
</header>

<main>
    <c:choose>
        <c:when test="${empty panier.lignes}">
            <p>Votre panier est vide.</p>
        </c:when>
        <c:otherwise>
            <table class="panier-table">
                <thead>
                <tr>
                    <th>Produit</th>
                    <th>Prix unitaire</th>
                    <th>Quantité</th>
                    <th>Sous-total</th>
                    <th>Actions</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach items="${panier.lignes}" var="ligne">
                    <tr>
                        <td>${ligne.produit.nom}</td>
                        <td><fmt:formatNumber value="${ligne.produit.prix}" type="currency" currencySymbol="€"/></td>
                        <td>
                            <form method="post" action="ecommerce" style="display:inline;">
                                <input type="hidden" name="action" value="modifierQuantite">
                                <input type="hidden" name="ligneId" value="${ligne.id}">
                                <input type="number" name="quantite" value="${ligne.quantite}" min="1">
                                <button type="submit">Modifier</button>
                            </form>
                        </td>
                        <td><fmt:formatNumber value="${ligne.sousTotal}" type="currency" currencySymbol="€"/></td>
                        <td>
                            <form method="post" action="ecommerce" style="display:inline;">
                                <input type="hidden" name="action" value="supprimerLigne">
                                <input type="hidden" name="ligneId" value="${ligne.id}">
                                <button type="submit" class="btn-supprimer">Supprimer</button>
                            </form>
                        </td>
                    </tr>
                </c:forEach>
                </tbody>
                <tfoot>
                <tr>
                    <td colspan="3"><strong>Total :</strong></td>
                    <td colspan="2"><strong><fmt:formatNumber value="${panier.total}" type="currency" currencySymbol="€"/></strong></td>
                </tr>
                </tfoot>
            </table>

            <form method="post" action="ecommerce">
                <input type="hidden" name="action" value="validerCommande">
                <button type="submit" class="btn-valider">Valider la commande</button>
            </form>
        </c:otherwise>
    </c:choose>
</main>
</body>
</html>