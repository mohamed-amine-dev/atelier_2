# Rapport - Application E-Commerce avec JPA et MVC2

## 📋 Informations Générales

**Projet :** Application Web E-Commerce  
**Technologies :** JPA, Servlets, JSP, MySQL, CDI  
**Serveur :** Wildfly 37  
**Gestionnaire :** Maven  

---

## 🎯 Objectif du Projet

Développer une application web e-commerce utilisant l'API JPA (Java Persistence API) avec une architecture MVC2, où une seule Servlet gère toutes les actions d'une fonctionnalité spécifique.

---

## 🏗️ Architecture du Projet

### 1. Diagramme de Classes

```
┌─────────────┐          ┌──────────┐          ┌──────────────┐
│  Internaute │ 1──────1 │  Panier  │ 1──────* │ LignePanier  │
└─────────────┘          └──────────┘          └──────────────┘
      │ 1                                              │
      │                                                │
      │ *                                              │ *
┌─────────────┐                                ┌──────────────┐
│  Commande   │ 1──────* LigneCommande         │   Produit    │
└─────────────┘                                └──────────────┘
```

### 2. Structure du Projet

```
atelier_2/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── ma.fstt.atelier_2/
│   │   │       ├── entities/
│   │   │       │   ├── Internaute.java
│   │   │       │   ├── Panier.java
│   │   │       │   ├── LignePanier.java
│   │   │       │   ├── Produit.java
│   │   │       │   ├── Commande.java
│   │   │       │   ├── LigneCommande.java
│   │   │       │   └── StatutCommande.java
│   │   │       ├── dao/
│   │   │       │   ├── GenericDAO.java
│   │   │       │   ├── InternauteDAO.java
│   │   │       │   ├── PanierDAO.java
│   │   │       │   └── ProduitDAO.java
│   │   │       └── PrincipalServlet.java
│   │   ├── resources/
│   │   │   └── META-INF/
│   │   │       └── persistence.xml
│   │   └── webapp/
│   │       ├── WEB-INF/
│   │       │   ├── views/
│   │       │   │   ├── vitrine.jsp
│   │       │   │   ├── panier.jsp
│   │       │   │   └── confirmation.jsp
│   │       │   ├── beans.xml
│   │       │   └── web.xml
│   │       └── index.jsp
└── pom.xml
```

---

## 💾 Modèle de Données

### Entités JPA

#### 1. **Internaute**
- Représente l'utilisateur du site
- Attributs : id, nom, prenom, email, motDePasse
- Relations : 
  - 1 Panier (OneToOne)
  - Plusieurs Commandes (OneToMany)

#### 2. **Panier**
- Gère le panier d'un internaute
- Attributs : id, dateCreation
- Relations :
  - 1 Internaute (OneToOne)
  - Plusieurs LignePanier (OneToMany)

#### 3. **Produit**
- Représente un article en vente
- Attributs : id, nom, description, prix, stock, image, disponible

#### 4. **LignePanier**
- Détail d'un produit dans le panier
- Attributs : id, quantite
- Relations :
  - 1 Panier (ManyToOne)
  - 1 Produit (ManyToOne)

#### 5. **Commande**
- Enregistre les commandes validées
- Attributs : id, dateCommande, montantTotal, statut, adresseLivraison
- Relations :
  - 1 Internaute (ManyToOne)
  - Plusieurs LigneCommande (OneToMany)

#### 6. **LigneCommande**
- Détail d'un produit dans la commande
- Attributs : id, quantite, prixUnitaire
- Relations :
  - 1 Commande (ManyToOne)
  - 1 Produit (ManyToOne)

---

## 🔧 Configuration Technique

### 1. persistence.xml

```xml
<persistence-unit name="ecommercePU" transaction-type="JTA">
    <provider>org.hibernate.jpa.HibernatePersistenceProvider</provider>
    
    <!-- Entités -->
    <class>ma.fstt.atelier_2.entities.Internaute</class>
    <class>ma.fstt.atelier_2.entities.Panier</class>
    <class>ma.fstt.atelier_2.entities.LignePanier</class>
    <class>ma.fstt.atelier_2.entities.Produit</class>
    <class>ma.fstt.atelier_2.entities.Commande</class>
    <class>ma.fstt.atelier_2.entities.LigneCommande</class>
    
    <properties>
        <property name="jakarta.persistence.jdbc.url" 
                  value="jdbc:mysql://localhost:3306/ecommerce_db"/>
        <property name="hibernate.hbm2ddl.auto" value="update"/>
        <property name="hibernate.show_sql" value="true"/>
    </properties>
</persistence-unit>
```

### 2. Dépendances Maven (pom.xml)

```xml
<dependencies>
    <!-- MySQL -->
    <dependency>
        <groupId>mysql</groupId>
        <artifactId>mysql-connector-java</artifactId>
        <version>8.0.33</version>
    </dependency>
    
    <!-- Hibernate -->
    <dependency>
        <groupId>org.hibernate</groupId>
        <artifactId>hibernate-core</artifactId>
        <version>6.6.26.Final</version>
    </dependency>
    
    <!-- JSTL -->
    <dependency>
        <groupId>jakarta.servlet.jsp.jstl</groupId>
        <artifactId>jakarta.servlet.jsp.jstl-api</artifactId>
        <version>3.0.0</version>
    </dependency>
    
    <!-- CDI -->
    <dependency>
        <groupId>jakarta.enterprise</groupId>
        <artifactId>jakarta.enterprise.cdi-api</artifactId>
        <version>4.0.1</version>
    </dependency>
</dependencies>
```

---

## 🎨 Architecture MVC2

### Pattern MVC2 Implémenté

```
Client → PrincipalServlet (Contrôleur)
              ↓
         DAOs (Modèle) ← Injection CDI
              ↓
         Entités JPA
              ↓
         JSP (Vue)
```

### Actions Gérées par la Servlet

**GET :**
- `vitrine` : Afficher les produits
- `panier` : Afficher le panier
- `logout` : Déconnexion

**POST :**
- `login` : Connexion utilisateur
- `register` : Inscription
- `ajouterPanier` : Ajouter un produit au panier
- `modifierQuantite` : Modifier la quantité
- `supprimerLigne` : Supprimer un produit
- `validerCommande` : Finaliser la commande

---

## 🚀 Fonctionnalités Implémentées

### 1. Gestion des Utilisateurs
- ✅ Inscription
- ✅ Connexion
- ✅ Déconnexion
- ✅ Sessions utilisateur

### 2. Gestion de la Vitrine
- ✅ Affichage des produits disponibles
- ✅ Détails des produits
- ✅ Filtrage par disponibilité

### 3. Gestion du Panier
- ✅ Ajouter des produits
- ✅ Modifier les quantités
- ✅ Supprimer des produits
- ✅ Calcul du total
- ✅ Persistance du panier

### 4. Gestion des Commandes
- ✅ Validation de commande
- ✅ Sauvegarde des commandes
- ✅ Historique des commandes
- ✅ Page de confirmation

---

## 🧪 Tests Effectués

### Scénarios de Test

1. **Inscription d'un nouvel utilisateur**
   - ✅ Succès avec données valides
   - ✅ Erreur si email existe déjà

2. **Connexion**
   - ✅ Succès avec identifiants corrects
   - ✅ Erreur avec identifiants incorrects

3. **Ajout au panier**
   - ✅ Ajout d'un nouveau produit
   - ✅ Mise à jour de la quantité si déjà présent

4. **Modification du panier**
   - ✅ Changement de quantité
   - ✅ Suppression d'une ligne

5. **Validation de commande**
   - ✅ Création de la commande
   - ✅ Vidage du panier
   - ✅ Affichage de la confirmation

---

## 🐛 Difficultés Rencontrées et Solutions

### 1. **Problème : Mélange javax et jakarta**
**Symptôme :** Erreur "ClassNotFoundException"  
**Solution :** Utiliser uniquement `jakarta.persistence.*` pour Wildfly 37+

### 2. **Problème : Entité non reconnue**
**Symptôme :** "not an @Entity type"  
**Solution :** 
- Vérifier l'annotation `@Entity`
- Lister les entités dans `persistence.xml`
- Uniformiser les imports

### 3. **Problème : Gestion des transactions**
**Symptôme :** "No EntityManager with actual transaction available"  
**Solution :** Utiliser `transaction-type="JTA"` dans persistence.xml pour Wildfly

### 4. **Problème : Lazy Loading Exception**
**Symptôme :** "LazyInitializationException"  
**Solution :** Utiliser `LEFT JOIN FETCH` dans les requêtes JPQL

### 5. **Problème : Injection CDI**
**Symptôme :** `@Inject` ne fonctionne pas  
**Solution :** Ajouter `beans.xml` dans WEB-INF

---

## 📊 Captures d'Écran

### 1. Page d'Accueil (index.jsp)
![Connexion/Inscription]

### 2. Vitrine des Produits
![Liste des produits disponibles]

### 3. Panier d'Achat
![Détails du panier avec total]

### 4. Confirmation de Commande
![Message de succès]

---

## 📚 Connaissances Acquises

### Techniques
- ✅ Maîtrise de l'API JPA et ses annotations
- ✅ Gestion des relations entre entités (OneToOne, OneToMany, ManyToOne)
- ✅ Pattern DAO pour la couche d'accès aux données
- ✅ Injection de dépendances avec CDI
- ✅ Architecture MVC2 avec une seule Servlet
- ✅ Utilisation de JSTL dans les JSP


## 🎓 Conclusion

Ce projet a permis de mettre en pratique les concepts de JPA dans un contexte réel d'application web. L'architecture MVC2 avec une seule Servlet par gestion permet de centraliser la logique de contrôle tout en gardant une séparation claire entre les couches.

Les principales difficultés rencontrées concernaient la configuration de JPA avec Wildfly et la gestion des relations entre entités. Ces problèmes ont été résolus en comprenant mieux le cycle de vie des entités JPA et l'importance de la configuration correcte.

Le projet est fonctionnel et peut servir de base solide pour une application e-commerce plus complète.

---


---

**Fin du Rapport**
