<%@ taglib uri="jakarta.servlet.jsp.jstl.core" prefix="c" %>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>CafeManager Pro - Gestion de Café</title>
    <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css" rel="stylesheet">
    <link href="https://fonts.googleapis.com/css2?family=Poppins:wght@300;400;500;600;700;800&display=swap" rel="stylesheet">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/templates/styles.css">
</head>
<body>
    <!-- Header -->
    <header class="header">
        <div class="header-left">
            <button class="mobile-toggle" id="mobileToggle">
                <i class="fas fa-bars"></i>
            </button>
            <div class="product-info">
                <img src="${pageContext.request.contextPath}/images/logo/logo.jpg" alt="Café">
            </div>
        </div>
        <div class="header-right">
            <button class="mobile-toggle" id="mobileToggle">
                <i class="fas fa-bars"></i>
            </button>
            <h1 class="page-title" id="pageTitle">Page de Login</h1>
        </div>
    </header>
    <main class="content">
        
    <!-- Forms Section -->
    <div class="element-section">
        <h3>Se connecter pour la gestion administrative</h3>
        <div class="form-showcase">
            <form action="login-administratif" method="post" class="form-example">
                <div class="form-group">
                    <label class="form-label">Nom :</label>
                    <input type="text" class="form-input" placeholder="Saisir votre nom d' administrateur..." name="nom" required>
                </div>
                <div class="form-group">
                    <label class="form-label">Mot de passe :</label>
                    <input type="password" class="form-input" name="motDePasse">
                </div>
                <button type="submit" class="btn primary">Se connecter</button>
            </form>
        </div>
    </div>
    <div class="element-section">
        <h3>Se connecter pour la gestion quotidienne</h3>
        <div class="form-showcase">
            <form action="login-quotidien" method="post" class="form-example">
                <div class="form-group">
                    <label class="form-label">Nom :</label>
                    <input type="text" class="form-input" placeholder="Saisir votre nom ..." name="nom" required>
                </div>
                <div class="form-group">
                    <label class="form-label">Mot de passe :</label>
                    <input type="password" class="form-input" name="motDePasse">
                </div>
                <button type="submit" class="btn primary">Se connecter</button>
            </form>
        </div>
    </div>
    <c:if test="${not empty erreurAdministratif}">
        <div class="alert danger">
            <i class="fas fa-times-circle"></i>
            <div>
                <strong>Erreur!</strong> ${erreurAdministratif}.
            </div>
        </div>
    </c:if>
    </main>
    <script src="${pageContext.request.contextPath}/templates/script.js"></script>
</body>
</html>
