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
                <a href="administratif" data-page="dashboard" class="nav-link">
                    <i class="fas fa-chart-line"></i>
                    <img src="${pageContext.request.contextPath}/images/logo/logo.jpg" alt="Café">
                </a>
            </div>
        </div>
        <div class="header-right">
            <button class="mobile-toggle" id="mobileToggle">
                <i class="fas fa-bars"></i>
            </button>
            <div class="product-info">
                <a href="deconnexion-administratif" data-page="dashboard" class="nav-link">
                    <i class="fas fa-chart-line"></i>
                    <span class="nav-text">Déconnexion</span>
                </a>
            </div>
        </div>
    </header>
    <main class="content">
        <div class="element-section">
            <h1>STOCK</h1>
            <a href="administratif/stock" data-page="dashboard" class="nav-link">
                <i class="fas fa-chart-line"></i>
            </a>
            </div>
        </div>
        <div class="element-section">
            <h1>FINANCE</h1>
            <a href="administratif" data-page="dashboard" class="nav-link">
                <i class="fas fa-chart-line"></i>
            </a>
            </div>
        </div>
        <div class="element-section">
            <h1>RESSSOURCES HUMAINES</h1>
            <a href="administratif/rh" data-page="dashboard" class="nav-link">
                <i class="fas fa-chart-line"></i>
            </a>
            </div>
        </div>
            <div class="element-section">
            <h1>PRODUCTION</h1>
            <a href="administratif/production" data-page="dashboard" class="nav-link">
                <i class="fas fa-chart-line"></i>
            </a>
            </div>
        </div>
            <div class="element-section">
            <h1>MARKETING</h1>
            <a href="administratif" data-page="dashboard" class="nav-link">
                <i class="fas fa-chart-line"></i>
            </a>
            </div>
        </div> 
    </main>
    <script src="${pageContext.request.contextPath}/templates/script.js"></script>
</body>
</html>

