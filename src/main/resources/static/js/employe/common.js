// Script pour la gestion de la sidebar et des interactions UI
document.addEventListener('DOMContentLoaded', function() {
    // Gestion de la sidebar mobile
    const mobileToggle = document.getElementById('mobileToggle');
    const sidebarToggle = document.getElementById('sidebarToggle');
    const sidebar = document.getElementById('sidebar');
    const mainContent = document.querySelector('.main-content');

    // Toggle sidebar sur mobile
    if (mobileToggle) {
        mobileToggle.addEventListener('click', function() {
            sidebar.classList.toggle('active');
        });
    }

    // Toggle sidebar depuis l'intérieur
    if (sidebarToggle) {
        sidebarToggle.addEventListener('click', function() {
            sidebar.classList.toggle('collapsed');
            mainContent.classList.toggle('expanded');
        });
    }

    // Fermer la sidebar en cliquant en dehors (mobile uniquement)
    document.addEventListener('click', function(event) {
        if (window.innerWidth <= 768) {
            if (!sidebar.contains(event.target) && !mobileToggle.contains(event.target)) {
                sidebar.classList.remove('active');
            }
        }
    });

    // Animations d'entrée pour les cartes
    const cards = document.querySelectorAll('.card');
    cards.forEach((card, index) => {
        card.style.animationDelay = `${index * 0.1}s`;
    });

    // Animation des boutons au hover
    const buttons = document.querySelectorAll('.btn');
    buttons.forEach(button => {
        button.addEventListener('mouseenter', function() {
            this.style.transform = 'translateY(-2px)';
        });
        
        button.addEventListener('mouseleave', function() {
            this.style.transform = 'translateY(0)';
        });
    });

    // Confirmation pour les actions critiques
    const dangerButtons = document.querySelectorAll('.btn-danger, [data-confirm]');
    dangerButtons.forEach(button => {
        button.addEventListener('click', function(e) {
            const message = this.getAttribute('data-confirm') || 'Êtes-vous sûr de vouloir effectuer cette action ?';
            if (!confirm(message)) {
                e.preventDefault();
            }
        });
    });

    // Auto-hide des alertes après 5 secondes
    const alerts = document.querySelectorAll('.alert');
    alerts.forEach(alert => {
        setTimeout(() => {
            alert.style.opacity = '0';
            setTimeout(() => {
                alert.style.display = 'none';
            }, 300);
        }, 5000);
    });

    // Smooth scroll pour les liens d'ancre
    const anchorLinks = document.querySelectorAll('a[href^="#"]');
    anchorLinks.forEach(link => {
        link.addEventListener('click', function(e) {
            e.preventDefault();
            const target = document.querySelector(this.getAttribute('href'));
            if (target) {
                target.scrollIntoView({
                    behavior: 'smooth',
                    block: 'start'
                });
            }
        });
    });

    // Loading state pour les formulaires
    const forms = document.querySelectorAll('form');
    forms.forEach(form => {
        form.addEventListener('submit', function() {
            const submitBtn = this.querySelector('button[type="submit"]');
            if (submitBtn) {
                submitBtn.disabled = true;
                submitBtn.innerHTML = '<i class="fas fa-spinner fa-spin"></i> Chargement...';
            }
        });
    });

    // Validation en temps réel pour les champs requis
    const requiredInputs = document.querySelectorAll('input[required], select[required]');
    requiredInputs.forEach(input => {
        input.addEventListener('blur', function() {
            if (this.value.trim() === '') {
                this.classList.add('is-invalid');
            } else {
                this.classList.remove('is-invalid');
                this.classList.add('is-valid');
            }
        });
    });

    // Tooltips Bootstrap
    const tooltipTriggerList = [].slice.call(document.querySelectorAll('[data-bs-toggle="tooltip"]'));
    const tooltipList = tooltipTriggerList.map(function (tooltipTriggerEl) {
        return new bootstrap.Tooltip(tooltipTriggerEl);
    });

    // Gestion responsive du titre de page
    function updatePageTitle() {
        const pageTitle = document.querySelector('.page-title');
        if (pageTitle && window.innerWidth <= 576) {
            const fullTitle = pageTitle.textContent;
            const words = fullTitle.split(' ');
            if (words.length > 2) {
                pageTitle.textContent = words.slice(0, 2).join(' ') + '...';
            }
        }
    }

    // Appel initial et sur resize
    updatePageTitle();
    window.addEventListener('resize', updatePageTitle);
});

// Fonction utilitaire pour formater les nombres
function formatCurrency(amount) {
    return new Intl.NumberFormat('fr-FR', {
        style: 'currency',
        currency: 'MGA',
        minimumFractionDigits: 0
    }).format(amount);
}

// Fonction pour afficher des notifications toast
function showToast(message, type = 'info') {
    const toast = document.createElement('div');
    toast.className = `toast align-items-center text-white bg-${type} border-0`;
    toast.setAttribute('role', 'alert');
    toast.innerHTML = `
        <div class="d-flex">
            <div class="toast-body">
                ${message}
            </div>
            <button type="button" class="btn-close btn-close-white me-2 m-auto" data-bs-dismiss="toast"></button>
        </div>
    `;
    
    // Ajouter le toast au container (créer s'il n'existe pas)
    let toastContainer = document.querySelector('.toast-container');
    if (!toastContainer) {
        toastContainer = document.createElement('div');
        toastContainer.className = 'toast-container position-fixed top-0 end-0 p-3';
        document.body.appendChild(toastContainer);
    }
    
    toastContainer.appendChild(toast);
    
    // Initialiser et afficher le toast
    const bsToast = new bootstrap.Toast(toast);
    bsToast.show();
    
    // Supprimer le toast après qu'il soit caché
    toast.addEventListener('hidden.bs.toast', function() {
        toast.remove();
    });
}
