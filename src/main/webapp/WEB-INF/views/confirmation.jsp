<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <title>Confirmation de Commande</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            max-width: 800px;
            margin: 50px auto;
            padding: 20px;
            text-align: center;
        }
        .success-icon {
            font-size: 72px;
            color: #28a745;
        }
        .message {
            font-size: 24px;
            margin: 20px 0;
        }
        .btn {
            display: inline-block;
            padding: 10px 20px;
            margin: 10px;
            background-color: #007bff;
            color: white;
            text-decoration: none;
            border-radius: 5px;
        }
    </style>
</head>
<body>
<div class="success-icon">✓</div>
<h1>Confirmation</h1>
<p class="message">${message}</p>
<a href="ecommerce?action=vitrine" class="btn">Continuer mes achats</a>
</body>
</html>
