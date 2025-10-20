<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>E-Commerce - Connexion</title>
    <style>
        * {
            margin: 0;
            padding: 0;
            box-sizing: border-box;
        }

        body {
            font-family: 'Arial', sans-serif;
            background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
            min-height: 100vh;
            display: flex;
            justify-content: center;
            align-items: center;
        }

        .container {
            background: white;
            padding: 40px;
            border-radius: 10px;
            box-shadow: 0 10px 40px rgba(0,0,0,0.2);
            width: 400px;
        }

        h1 {
            text-align: center;
            color: #333;
            margin-bottom: 30px;
        }

        .tabs {
            display: flex;
            margin-bottom: 30px;
            border-bottom: 2px solid #eee;
        }

        .tab {
            flex: 1;
            padding: 10px;
            text-align: center;
            cursor: pointer;
            background: none;
            border: none;
            font-size: 16px;
            color: #666;
            transition: all 0.3s;
        }

        .tab.active {
            color: #667eea;
            border-bottom: 2px solid #667eea;
            font-weight: bold;
        }

        .form-container {
            display: none;
        }

        .form-container.active {
            display: block;
        }

        .form-group {
            margin-bottom: 20px;
        }

        label {
            display: block;
            margin-bottom: 5px;
            color: #333;
            font-weight: bold;
        }

        input[type="text"],
        input[type="email"],
        input[type="password"] {
            width: 100%;
            padding: 12px;
            border: 1px solid #ddd;
            border-radius: 5px;
            font-size: 14px;
            transition: border 0.3s;
        }

        input:focus {
            outline: none;
            border-color: #667eea;
        }

        button[type="submit"] {
            width: 100%;
            padding: 12px;
            background: #667eea;
            color: white;
            border: none;
            border-radius: 5px;
            font-size: 16px;
            cursor: pointer;
            transition: background 0.3s;
        }

        button[type="submit"]:hover {
            background: #5568d3;
        }

        .error {
            background: #f8d7da;
            color: #721c24;
            padding: 10px;
            border-radius: 5px;
            margin-bottom: 20px;
            text-align: center;
        }
    </style>
</head>
<body>
<div class="container">
    <h1>🛒 E-Commerce</h1>

    <%
        String error = request.getParameter("error");
        if ("1".equals(error)) {
    %>
    <div class="error">Email ou mot de passe incorrect !</div>
    <% } else if ("2".equals(error)) { %>
    <div class="error">Cet email existe déjà !</div>
    <% } %>

    <div class="tabs">
        <button class="tab active" onclick="showTab('login')">Connexion</button>
        <button class="tab" onclick="showTab('register')">Inscription</button>
    </div>

    <!-- Formulaire de Connexion -->
    <div id="login" class="form-container active">
        <form action="ecommerce" method="post">
            <input type="hidden" name="action" value="login">

            <div class="form-group">
                <label for="login-email">Email</label>
                <input type="email" id="login-email" name="email" required>
            </div>

            <div class="form-group">
                <label for="login-password">Mot de passe</label>
                <input type="password" id="login-password" name="motDePasse" required>
            </div>

            <button type="submit">Se connecter</button>
        </form>
    </div>

    <!-- Formulaire d'Inscription -->
    <div id="register" class="form-container">
        <form action="ecommerce" method="post">
            <input type="hidden" name="action" value="register">

            <div class="form-group">
                <label for="nom">Nom</label>
                <input type="text" id="nom" name="nom" required>
            </div>

            <div class="form-group">
                <label for="prenom">Prénom</label>
                <input type="text" id="prenom" name="prenom" required>
            </div>

            <div class="form-group">
                <label for="register-email">Email</label>
                <input type="email" id="register-email" name="email" required>
            </div>

            <div class="form-group">
                <label for="register-password">Mot de passe</label>
                <input type="password" id="register-password" name="motDePasse" required>
            </div>

            <button type="submit">S'inscrire</button>
        </form>
    </div>
</div>

<script>
    function showTab(tabName) {
        // Masquer tous les formulaires
        document.querySelectorAll('.form-container').forEach(form => {
            form.classList.remove('active');
        });

        // Désactiver tous les onglets
        document.querySelectorAll('.tab').forEach(tab => {
            tab.classList.remove('active');
        });

        // Afficher le formulaire sélectionné
        document.getElementById(tabName).classList.add('active');

        // Activer l'onglet cliqué
        event.target.classList.add('active');
    }
</script>
</body>
</html>